package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.dto.request.CreateQuestionRequest;
import com.enigma.Instructor_Led.dto.response.QuestionResponse;
import com.enigma.Instructor_Led.dto.response.TraineeResponse;
import com.enigma.Instructor_Led.dto.response.TrainerResponse;
import com.enigma.Instructor_Led.entity.Question;
import com.enigma.Instructor_Led.entity.Schedule;
import com.enigma.Instructor_Led.entity.Trainee;
import com.enigma.Instructor_Led.repository.QuestionRepository;
import com.enigma.Instructor_Led.repository.ScheduleRepository;
import com.enigma.Instructor_Led.repository.TraineeRepository;
import com.enigma.Instructor_Led.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {

    private final  QuestionRepository questionRepository;
    private final TraineeRepository traineeRepository;
    private final ScheduleRepository scheduleRepository;

    @Override
    public QuestionResponse createQuestion(CreateQuestionRequest request, String traineeId, String scheduleId) {
        Trainee trainee = traineeRepository.findById(traineeId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Trainee not found"));

        Schedule schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found"));

        Question question = Question.builder()
                .question(request.getQuestion())
                .trainee(trainee)
                .schedule(schedule)
                .status("Pending")
                .build();

        questionRepository.save(question);
        return toQuestionResponse(question);
    }

    @Override
    public QuestionResponse answerQuestion(String questionId, String answer) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found"));

        question.setAnswer(answer);
        question.setStatus("Answered");
        questionRepository.save(question);

        return toQuestionResponse(question);
    }

    @Override
    public List<QuestionResponse> getQuestionsBySchedule(String scheduleId) {
        return questionRepository.findByScheduleId(scheduleId).stream()
                .map(this::toQuestionResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<QuestionResponse> getQuestionsByTrainee(String traineeId) {
        return questionRepository.findByTraineeId(traineeId).stream()
                .map(this::toQuestionResponse)
                .collect(Collectors.toList());
    }

    private QuestionResponse toQuestionResponse(Question question) {
        return QuestionResponse.builder()
                .id(question.getId())
                .question(question.getQuestion())
                .answer(question.getAnswer())
                .status(question.getStatus())
//                .trainee(new TraineeResponse(question.getTrainee().getId(), question.getTrainee().getName())) // Assuming TraineeResponse has id and name
//                .trainer(new TrainerResponse(question.getSchedule().getTrainer().getId(), question.getSchedule().getTrainer().getName())) // Assuming TrainerResponse has id and name
                .build();
    }
}
