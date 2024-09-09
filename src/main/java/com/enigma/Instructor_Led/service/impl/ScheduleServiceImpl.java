package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.dto.request.CreateScheduleRequest;
import com.enigma.Instructor_Led.dto.request.UpdateScheduleRequest;
import com.enigma.Instructor_Led.dto.response.ScheduleResponse;
import com.enigma.Instructor_Led.dto.response.TrainerResponse;
import com.enigma.Instructor_Led.entity.Schedule;
import com.enigma.Instructor_Led.entity.Trainer;
import com.enigma.Instructor_Led.repository.ScheduleRepository;
import com.enigma.Instructor_Led.repository.TrainerRepository;
import com.enigma.Instructor_Led.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final TrainerRepository trainerRepository;

    @Override
    public ScheduleResponse createSchedule(CreateScheduleRequest request) {
        Trainer trainer = trainerRepository.findById(request.getTrainerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,  "Trainer not found"));

        Schedule schedule = Schedule.builder()
                .date(request.getDate())
                .topic(request.getTopic())
                .link(request.getLink())
                .trainer(trainer)
                .programmingLanguage(request.getProgrammingLanguage())
                .build();

        scheduleRepository.save(schedule);

        return toScheduleResponse(schedule);
    }

    @Override
    public ScheduleResponse updateSchedule(UpdateScheduleRequest request) {
        Schedule schedule = scheduleRepository.findById(request.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));

        Trainer trainer = trainerRepository.findById(request.getTrainerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,  "Trainer not found"));

        schedule.setDate(request.getDate());
        schedule.setTopic(request.getTopic());
        schedule.setLink(request.getLink());
        schedule.setTrainer(trainer);
        schedule.setProgrammingLanguage(trainer.getProgrammingLanguages().toString());

        scheduleRepository.save(schedule);

        return toScheduleResponse(schedule);
    }

    @Override
    public ScheduleResponse getScheduleById(String id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));

        return toScheduleResponse(schedule);
    }

    @Override
    public Page<ScheduleResponse> getAllSchedules(Pageable pageable) {
        return scheduleRepository.findAll(pageable).map(this::toScheduleResponse);
    }

    @Override
    public void deleteSchedule(String id) {
        Schedule schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));

        scheduleRepository.delete(schedule);
    }

    private ScheduleResponse toScheduleResponse(Schedule schedule) {
        return ScheduleResponse.builder()
                .id(schedule.getId())
                .date(schedule.getDate())
                .topic(schedule.getTopic())
                .programmingLanguage(schedule.getProgrammingLanguage())
                .link(schedule.getLink())
//                .trainer(new TrainerResponse(schedule.getTrainer().getId(), schedule.getTrainer().getName())) // Assuming TrainerResponse has id and name
//                .documentationImages(schedule.getDocumentationImages().stream().map(img -> new DocumentationImageResponse(img.getId(), img.getUrl())).collect(Collectors.toList())) // Assuming DocumentationImageResponse has id and url
                .build();
    }
}
