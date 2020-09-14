package com.udacity.jwdnd.course1.cloudstorage.service;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import com.udacity.jwdnd.course1.cloudstorage.entity.User;
import com.udacity.jwdnd.course1.cloudstorage.exception.StorageException;
import com.udacity.jwdnd.course1.cloudstorage.exception.StorageFileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Stream;

@Service
public class StorageServiceImpl implements StorageService {

    private final Path rootLocation;
    private FileMapper fileMapper;
    private UserMapper userMapper;
    private final UserService userService;
    final static Logger logger = LoggerFactory.getLogger(StorageServiceImpl.class);

    @Autowired
    public StorageServiceImpl(StorageProperties properties, FileMapper fileMapper, UserService userService, UserMapper userMapper) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.fileMapper = fileMapper;
        this.userService = userService;
        this.userMapper= userMapper;
    }


    public List<File> getUserFiles(String userName) {

        return fileMapper.getFiles(userMapper.getUser(userName).getUserId());
    }

    @Override
    public String store(MultipartFile file, String userName) {

        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                return "Failed to store empty file " + filename;
            }
            if (filename.contains("..")) {
                // This is a security check
                return "Cannot store file with relative path outside current directory "
                                + filename;
            }

            User user = userMapper.getUser(userName);
            Integer userId = user.getUserId();
            fileMapper.insertFile(new File(null, file.getOriginalFilename(), file.getContentType(), String.valueOf(file.getSize()),
                                           userId, file.getBytes()));
            return "You successfully uploaded " + file.getOriginalFilename();
        }
        catch (IOException e) {
            return "Failed to store file " + filename;
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    @Override
    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    @Override
    public Integer deleteFile(Integer fileId) {
        return fileMapper.deleteFile(fileId);
    }

    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
