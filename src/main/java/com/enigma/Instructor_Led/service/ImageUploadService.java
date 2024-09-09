package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.response.DocumentationImageResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUploadService {
    DocumentationImageResponse uploadImage(MultipartFile file, String scheduleId) throws IOException;
}
