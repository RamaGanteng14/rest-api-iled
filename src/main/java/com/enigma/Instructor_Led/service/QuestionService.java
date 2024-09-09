package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.CreateAnswerRequest;
import com.enigma.Instructor_Led.dto.request.CreateQuestionRequest;
import com.enigma.Instructor_Led.dto.response.QuestionResponse;

import java.util.List;

public interface QuestionService {
    QuestionResponse createQuestion(CreateQuestionRequest request, String traineeId, String scheduleId);
    QuestionResponse answerQuestion(String questionId, String answer);
    List<QuestionResponse> getQuestionsBySchedule(String scheduleId);
    List<QuestionResponse> getQuestionsByTrainee(String traineeId);
}
