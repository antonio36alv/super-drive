package com.udacity.jwdnd.course1.cloudstorage.exception;

import com.udacity.jwdnd.course1.cloudstorage.service.StorageService;

public class StorageFileNotFoundException extends StorageException {

    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}