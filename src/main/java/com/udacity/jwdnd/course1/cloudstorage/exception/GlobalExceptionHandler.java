package com.udacity.jwdnd.course1.cloudstorage.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.naming.SizeLimitExceededException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MultipartException.class)
    public String sizeUploadHandler(MultipartException e, RedirectAttributes redirectAttributes) {


        redirectAttributes.addFlashAttribute("alertMessage", "Error: File size is too large. Max size allowed is 128KB.");
        return "redirect:/home";
    }

}
