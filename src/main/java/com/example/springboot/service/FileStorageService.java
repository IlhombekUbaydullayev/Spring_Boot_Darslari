package com.example.springboot.service;

import com.example.springboot.enums.FileStorageStatus;
import com.example.springboot.model.FileStorage;
import com.example.springboot.repository.FileStorageRepository;
import org.hashids.Hashids;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Service
public class FileStorageService {
    private final FileStorageRepository repository;
    private final Hashids hashids;

    @Value("${upload.folder}")
    private String uploadFolder;

    public FileStorageService(FileStorageRepository repository) {
        this.repository = repository;
        this.hashids = new Hashids(getClass().getName(),6);
    }

    public void save(MultipartFile multipartFile) {
        FileStorage fileStore = new FileStorage();
        fileStore.setName(multipartFile.getOriginalFilename());
        fileStore.setExtension(getExt(multipartFile.getOriginalFilename()));
        fileStore.setFileSize(multipartFile.getSize());
        fileStore.setContentType(multipartFile.getContentType());
        fileStore.setFileStorageStatus(FileStorageStatus.DRAFT);
        repository.save(fileStore);

        Date now = new Date();
        File uploadFolder = new File(String.format("%s/upload_files/%d/%d/%d/",this.uploadFolder,
                1900 + now.getYear(), 1 + now.getMonth(), now.getDate()));

        if (!uploadFolder.exists() && uploadFolder.mkdirs()) {
            System.out.println("file created");
        }
        fileStore.setHashId(hashids.encode(fileStore.getId()));
        fileStore.setUploadPath(String.format("upload_files/%d/%d/%d/%s.%s",
                        1900 + now.getYear(),
                        1 + now.getMonth(),now.getDate(),fileStore.getHashId(),fileStore.getExtension()));
        repository.save(fileStore);
        uploadFolder = uploadFolder.getAbsoluteFile();
        File file = new File(uploadFolder,String.format("%s.%s",fileStore.getHashId(),fileStore.getExtension()));
        try {
            multipartFile.transferTo(file);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Transactional(readOnly = true)
    public FileStorage findByHashId(String hashId) {
        return repository.findByHashId(hashId);
    }

    public void delete(String hashId) {
        FileStorage fileStorage  = findByHashId(hashId);
        File file = new File(String.format("%s/%s",this.uploadFolder,fileStorage.getUploadPath()));
        if (file.delete()) {
            repository.delete(fileStorage);
        }
    }

    @Scheduled(cron = "0 42 20 * * *")
    public void deleteAllDraft() {
        List<FileStorage> fileStorages = repository.findAllByFileStorageStatus(FileStorageStatus.DRAFT);
        fileStorages.forEach(fileStorage -> {
            delete(fileStorage.getHashId());
        });
    }

    private String getExt(String fileName) {
        String ext = null;
        if (fileName != null && !fileName.isEmpty()) {
            int dot = fileName.lastIndexOf('.');
            if (dot > 0 && dot <= fileName.length() -2) {
                ext = fileName.substring(dot + 1);
            }
        }
        return ext;
    }
}
