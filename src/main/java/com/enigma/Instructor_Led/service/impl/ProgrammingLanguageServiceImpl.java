package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.dto.request.CreateProgrammingLanguageRequest;
import com.enigma.Instructor_Led.dto.request.UpdateProgrammingLanguageRequest;
import com.enigma.Instructor_Led.dto.response.ProgrammingLanguageResponse;
import com.enigma.Instructor_Led.entity.ProgrammingLanguage;
import com.enigma.Instructor_Led.repository.ProgrammingLanguageRepository;
import com.enigma.Instructor_Led.service.ProgrammingLanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgrammingLanguageServiceImpl  implements ProgrammingLanguageService {

    private final ProgrammingLanguageRepository programmingLanguageRepository;

    @Override
    public ProgrammingLanguageResponse create(CreateProgrammingLanguageRequest request) {
        ProgrammingLanguage programmingLanguage = ProgrammingLanguage.builder()
                .programmingLanguage(request.getProgrammingLanguage())
                .build();

        ProgrammingLanguage savedProgrammingLanguage = programmingLanguageRepository.save(programmingLanguage);

        return mapToResponse(savedProgrammingLanguage);
    }

    @Override
    public ProgrammingLanguageResponse update(UpdateProgrammingLanguageRequest updateProgrammingLanguageRequest) {
        return null;
    }

    @Override
    public ProgrammingLanguageResponse getById(String id) {
        Optional<ProgrammingLanguage> optionalProgrammingLanguage = programmingLanguageRepository.findById(id);

        if (optionalProgrammingLanguage.isPresent()) {
            return mapToResponse(optionalProgrammingLanguage.get());
        } else {
            return null;
        }
    }

    @Override
    public ProgrammingLanguage getOneById(String id) {
        return programmingLanguageRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Programming Language not found"));
    }

    @Override
    public Page<ProgrammingLanguageResponse> getAll(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProgrammingLanguage> programmingLanguagePage = programmingLanguageRepository.findAll(pageable);

        return programmingLanguagePage.map(this::mapToResponse);
    }

    @Override
    public void delete(String id) {
        programmingLanguageRepository.deleteById(id);
    }

    private ProgrammingLanguageResponse mapToResponse(ProgrammingLanguage programmingLanguage) {
        return ProgrammingLanguageResponse.builder()
                .id(programmingLanguage.getId())
                .programmingLanguage(programmingLanguage.getProgrammingLanguage())
                .build();
    }
}
