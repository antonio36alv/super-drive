package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class StorageServiceImpl implements StorageService {

    private FileMapper fileMapper;
    private UserMapper userMapper;
    private final UserService userService;
    final static Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);

    @Autowired
    public StorageServiceImpl(FileMapper fileMapper, UserService userService, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userService = userService;
        this.userMapper= userMapper;
    }

    public File viewFile(Integer fileId) {
        return fileMapper.viewFile(fileId);
    }


    public List<File> getUserFiles(String userName) {
        return fileMapper.getFiles(userMapper.getUser(userName).getUserId());
    }

    @Override
    public String store(MultipartFile file, Integer userId) {

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                if(file.getOriginalFilename().equals("")) {
                    return "Failed to store empty file.";
                }
                return String.format("Failed to store empty file %s.", filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                return "Cannot store file with relative path outside current directory "
                                + filename;
            }
            if(checkFileExists(file.getOriginalFilename(), userId)) {
                return "File name exists. Please rename file and try again.";
            }

            fileMapper.insertFile(new File(null, file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()),
                                           userId, file.getBytes()));
            return "You successfully uploaded " + file.getOriginalFilename();
        }
        catch (IOException e) {
            return "Failed to store file " + filename;
        }
    }

    @Override
    public Integer deleteFile(Integer fileId) {
        return fileMapper.deleteFile(fileId);
    }

    @Override
    public boolean checkFileExists(String fileName, Integer userId) {
        return fileMapper.checkFileExists(fileName, userId);
    }

}