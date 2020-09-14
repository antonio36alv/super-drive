package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Service
public interface StorageService {

        void init();

        List<File> getUserFiles(String userName);

        String store(MultipartFile fileUpload, String userName);

        Stream<Path> loadAll();

        Path load(String filename);

        Resource loadAsResource(String filename);

        void deleteAll();

}
