package com.udacity.jwdnd.course1.cloudstorage.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

@Service
public interface StorageService {

        void init();

        void store(MultipartFile fileUpload);

        Stream<Path> loadAll();

        Path load(String filename);

        Resource loadAsResource(String filename);

        void deleteAll();

}
