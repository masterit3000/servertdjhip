package com.tindung.jhip.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class StorageService {
    
    Logger log = LoggerFactory.getLogger(this.getClass().getName());
    private final Path rootLocation = Paths.get("uploads/");
    
    public void store(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            throw new RuntimeException("FAIL!");
        }
    }
    
    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }
    
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }
    
    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }
    
    public void store(MultipartFile file, long id) {
        try {
//            String typeString = file.getContentType().substring(6);
//            StringBuffer fileName = new StringBuffer();
//            fileName.append()
            Path folder = this.rootLocation.resolve("khachhang/").resolve(String.valueOf(id));
            if (folder.toFile().isFile() || !folder.toFile().exists()) {
                log.debug("creat folder: " + folder.toFile().getAbsolutePath());
                folder.toFile().mkdirs();
            }
//             String contentType = file.getContentType();
//            file.transferTo(this.rootLocation.resolve(id + ".jpg").toFile());
            Files.copy(file.getInputStream(), folder.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("FAIL!");
        }
    }
}
