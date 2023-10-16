package com.example.springboot.controller;

import com.example.springboot.model.FileStorage;
import com.example.springboot.service.FileStorageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileUrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URLEncoder;

@RestController
@RequestMapping("/api")
public class FileStorageController {
    private final FileStorageService service;

    @Value("${upload.folder}")
    private String uploadFolder;

    public FileStorageController(FileStorageService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) {
        service.save(file);
        return ResponseEntity.ok(file.getOriginalFilename() + " file saved!");
    }

    @GetMapping("/preview/{hashId}")
    public ResponseEntity previewFile(@PathVariable String hashId) throws IOException {
        FileStorage fileStorage = service.findByHashId(hashId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "inline; fileName=\"" +
                                URLEncoder.encode(fileStorage.getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s", uploadFolder,fileStorage.getUploadPath())));
    }

    @GetMapping("/download/{hashId}")
    public ResponseEntity downloadFile(@PathVariable String hashId) throws IOException {
        FileStorage fileStorage = service.findByHashId(hashId);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; fileName=\"" +
                                URLEncoder.encode(fileStorage.getName()))
                .contentType(MediaType.parseMediaType(fileStorage.getContentType()))
                .contentLength(fileStorage.getFileSize())
                .body(new FileUrlResource(String.format("%s/%s", uploadFolder,fileStorage.getUploadPath())));
    }

    @DeleteMapping("/delete/{hashId}")
    public ResponseEntity delete(@PathVariable String hashId) {
        service.delete(hashId);
        return ResponseEntity.ok("file deleted successfully");
    }
}
