package com.enigma.Instructor_Led.controller;

import com.enigma.Instructor_Led.dto.response.DocumentationImageResponse;
import com.enigma.Instructor_Led.service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/images")
public class ImageController {

    @Autowired
    private ImageUploadService imageUploadService;

//    @PostMapping()
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<DocumentationImageResponse> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("scheduleId") String scheduleId

            ) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(null); // Handle case when the file is empty
            }
            DocumentationImageResponse imageResponse = imageUploadService.uploadImage(file, scheduleId);
            return ResponseEntity.ok(imageResponse);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null); // Customize this error handling
        }
    }

}
