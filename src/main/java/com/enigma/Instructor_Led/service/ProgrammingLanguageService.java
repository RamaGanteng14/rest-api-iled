package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.CreateProgrammingLanguageRequest;
import com.enigma.Instructor_Led.dto.request.UpdateProgrammingLanguageRequest;
import com.enigma.Instructor_Led.dto.response.ProgrammingLanguageResponse;
import com.enigma.Instructor_Led.entity.ProgrammingLanguage;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProgrammingLanguageService {
    ProgrammingLanguageResponse create(CreateProgrammingLanguageRequest createProgrammingLanguageRequest);
    ProgrammingLanguageResponse update(UpdateProgrammingLanguageRequest updateProgrammingLanguageRequest);
    ProgrammingLanguageResponse getById(String id);
    ProgrammingLanguage getOneById(String id);
    Page<ProgrammingLanguageResponse> getAll(Integer page, Integer size);
    void delete (String id);
}
