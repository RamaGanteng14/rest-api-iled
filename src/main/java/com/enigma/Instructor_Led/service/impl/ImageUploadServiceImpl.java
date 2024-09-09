package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.dto.response.DocumentationImageResponse;
import com.enigma.Instructor_Led.entity.DocumentationImage;
import com.enigma.Instructor_Led.entity.Schedule;
import com.enigma.Instructor_Led.repository.DocumentationImageRepository;
import com.enigma.Instructor_Led.repository.ScheduleRepository;
import com.enigma.Instructor_Led.service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Service
public class ImageUploadServiceImpl implements ImageUploadService {
    @Autowired
    private DocumentationImageRepository documentationImageRepository;

    @Autowired
    private ScheduleRepository scheduleRepository;

    private final RestTemplate restTemplate;
    private final String privateKey;
    private final String urlEndpoint;

    public ImageUploadServiceImpl(RestTemplate restTemplate,
                                  @Value("${imagekit.private_key}") String privateKey,
                                  @Value("${imagekit.url_endpoint}") String urlEndpoint) {
        this.restTemplate = restTemplate;
        this.privateKey = privateKey;
        this.urlEndpoint = urlEndpoint;
    }

    @Transactional
    @Override
    public DocumentationImageResponse uploadImage(MultipartFile file, String scheduleId) throws IOException {
        // Convert file to Base64
        String encodedFile = Base64.getEncoder().encodeToString(file.getBytes());

        // Create request body
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("file", encodedFile);
        requestBody.put("fileName", file.getOriginalFilename());

        // Create headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, privateKey);
        headers.set(HttpHeaders.CONTENT_TYPE, "application/json");

        // Create HttpEntity with headers and body
        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(requestBody, headers);

        // Send request to ImageKit
        ResponseEntity<Map<String, String>> response;
        try {
            response = restTemplate.exchange(urlEndpoint + "/upload", HttpMethod.POST, requestEntity,
                    new ParameterizedTypeReference<Map<String, String>>() {});
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Failed to upload image", e);
        }

        // Extract result from response
        Map<String, String> responseBody = response.getBody();
        if (responseBody == null || responseBody.get("url") == null) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to get image URL");
        }

        // Find Schedule by ID
        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));

        // Save DocumentationImage to database
        DocumentationImage documentationImage = new DocumentationImage();
        documentationImage.setLink(responseBody.get("url")); // URL result from ImageKit
        documentationImage.setSchedule(schedule);
        documentationImage = documentationImageRepository.save(documentationImage);

        // Return response
        return DocumentationImageResponse.builder()
                .id(documentationImage.getId())
                .link(documentationImage.getLink())
                .build();
    }
}
