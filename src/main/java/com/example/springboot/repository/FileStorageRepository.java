package com.example.springboot.repository;

import com.example.springboot.enums.FileStorageStatus;
import com.example.springboot.model.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileStorageRepository extends JpaRepository<FileStorage,Long> {
    FileStorage findByHashId(String hashId);
    List<FileStorage> findAllByFileStorageStatus(FileStorageStatus fileStorageStatus);
}
