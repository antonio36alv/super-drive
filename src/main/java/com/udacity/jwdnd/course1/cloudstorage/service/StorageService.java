package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Stream;

@Service
public interface StorageService {

        List<File> getUserFiles(String userName);

        String store(MultipartFile fileUpload, Integer userId);

        Integer deleteFile(Integer fileId);

        File viewFile(Integer fileId);

        boolean checkFileExists(String fileName, Integer userId);

}
