package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.constant.TraineeStatus;
import com.enigma.Instructor_Led.dto.request.CreateTraineeRequest;
import com.enigma.Instructor_Led.dto.request.UpdateTraineeRequest;
import com.enigma.Instructor_Led.dto.response.TraineeResponse;
import com.enigma.Instructor_Led.entity.ProgrammingLanguage;
import com.enigma.Instructor_Led.entity.Trainee;
import com.enigma.Instructor_Led.repository.ProgrammingLanguageRepository;
import com.enigma.Instructor_Led.repository.TraineeRepository;
import com.enigma.Instructor_Led.service.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TraineeServiceImpl implements TraineeService {

    private final TraineeRepository traineeRepository;
    private final ProgrammingLanguageRepository programmingLanguageRepository;

    @Override
    public Trainee create(Trainee trainee) {
        return traineeRepository.saveAndFlush(trainee);
            }

    @Override
    public TraineeResponse update(UpdateTraineeRequest updateTraineeRequest) {
        Optional<Trainee> traineeOpt = traineeRepository.findById(updateTraineeRequest.getId());
        if (traineeOpt.isEmpty()) {
            return null; // Or throw an exception
        }

        Trainee trainee = traineeOpt.get();
        trainee.setName(updateTraineeRequest.getName());
        trainee.setNik(updateTraineeRequest.getNik());
        trainee.setBirthDate(updateTraineeRequest.getBirthDate());
        trainee.setAddress(updateTraineeRequest.getAddress());
        trainee.setEmail(updateTraineeRequest.getEmail());
        trainee.setPhoneNumber(updateTraineeRequest.getPhoneNumber());
        Trainee updatedTrainee = traineeRepository.save(trainee);

        return mapToResponse(updatedTrainee);
    }

    @Override
    public TraineeResponse getById(String id) {
        Optional<Trainee> traineeOpt = traineeRepository.findById(id);
        return traineeOpt.map(this::mapToResponse).orElse(null);
    }

    @Override
    public Page<TraineeResponse> getAll(Pageable pageable) {
        return traineeRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Override
    public void delete(String id) {
        traineeRepository.deleteById(id);
    }

    private TraineeResponse mapToResponse(Trainee trainee) {
        return TraineeResponse.builder()
                .id(trainee.getId())
                .name(trainee.getName())
                .nik(trainee.getNik())
                .birthDate(trainee.getBirthDate())
                .address(trainee.getAddress())
                .email(trainee.getEmail())
                .phoneNumber(trainee.getPhoneNumber())
                .programmingLanguage(trainee.getProgrammingLanguage().getProgrammingLanguage())
                .status(trainee.getStatus())
                .build();
    }
}
