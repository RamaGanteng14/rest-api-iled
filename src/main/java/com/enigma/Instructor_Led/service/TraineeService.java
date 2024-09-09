package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.CreateTraineeRequest;
import com.enigma.Instructor_Led.dto.request.UpdateTraineeRequest;
import com.enigma.Instructor_Led.dto.response.TraineeResponse;
import com.enigma.Instructor_Led.entity.Trainee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TraineeService {
    Trainee create(Trainee trainee);
    TraineeResponse update(UpdateTraineeRequest updateTraineeRequest);
    TraineeResponse getById(String id);
    Page<TraineeResponse> getAll(Pageable pageable);
    void delete(String id);
}
