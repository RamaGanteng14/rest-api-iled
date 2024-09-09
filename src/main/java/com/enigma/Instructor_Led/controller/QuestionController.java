package com.enigma.Instructor_Led.controller;

import com.enigma.Instructor_Led.constant.PathUrl;
import com.enigma.Instructor_Led.dto.request.CreateQuestionRequest;
import com.enigma.Instructor_Led.dto.response.CommonResponse;
import com.enigma.Instructor_Led.dto.response.PagingResponse;
import com.enigma.Instructor_Led.dto.response.QuestionResponse;
import com.enigma.Instructor_Led.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathUrl.QUESTION)
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping("/schedule/{scheduleId}/trainee/{traineeId}")
    public ResponseEntity<CommonResponse<QuestionResponse>> createQuestion(
            @RequestBody CreateQuestionRequest createQuestionRequest,
            @PathVariable String scheduleId,
            @PathVariable String traineeId) {
        QuestionResponse questionResponse = questionService.createQuestion(createQuestionRequest, traineeId, scheduleId);
        CommonResponse<QuestionResponse> response = CommonResponse.<QuestionResponse>builder()
                .message("Question created successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(questionResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{questionId}/answer")
    public ResponseEntity<CommonResponse<QuestionResponse>> answerQuestion(
            @PathVariable String questionId,
            @RequestBody String answer) {
        QuestionResponse questionResponse = questionService.answerQuestion(questionId, answer);
        CommonResponse<QuestionResponse> response = CommonResponse.<QuestionResponse>builder()
                .message("Question answered successfully")
                .statusCode(HttpStatus.OK.value())
                .data(questionResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @GetMapping("/schedule/{scheduleId}")
//    public ResponseEntity<CommonResponse<List<QuestionResponse>>> getQuestionsBySchedule(
//            @PathVariable String scheduleId,
//            @RequestParam(name = "page", defaultValue = "0") Integer page,
//            @RequestParam(name = "size", defaultValue = "10") Integer size) {
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<QuestionResponse> questionPage = questionService.getQuestionsBySchedule(scheduleId, pageable);
//
//        PagingResponse pagingResponse = PagingResponse.builder()
//                .page(page)
//                .size(size)
//                .totalPages(questionPage.getTotalPages())
//                .totalElement(questionPage.getTotalElements())
//                .hasNext(questionPage.hasNext())
//                .hasPrevious(questionPage.hasPrevious())
//                .build();
//
//        CommonResponse<List<QuestionResponse>> response = CommonResponse.<List<QuestionResponse>>builder()
//                .message("Successfully retrieved questions by schedule")
//                .statusCode(HttpStatus.OK.value())
//                .data(questionPage.getContent())
//                .paging(pagingResponse)
//                .build();
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
//
//    @GetMapping("/trainee/{traineeId}")
//    public ResponseEntity<CommonResponse<List<QuestionResponse>>> getQuestionsByTrainee(
//            @PathVariable String traineeId,
//            @RequestParam(name = "page", defaultValue = "0") Integer page,
//            @RequestParam(name = "size", defaultValue = "10") Integer size) {
//
//        Pageable pageable = PageRequest.of(page, size);
//        Page<QuestionResponse> questionPage = questionService.getQuestionsByTrainee(traineeId, pageable);
//
//        PagingResponse pagingResponse = PagingResponse.builder()
//                .page(page)
//                .size(size)
//                .totalPages(questionPage.getTotalPages())
//                .totalElement(questionPage.getTotalElements())
//                .hasNext(questionPage.hasNext())
//                .hasPrevious(questionPage.hasPrevious())
//                .build();
//
//        CommonResponse<List<QuestionResponse>> response = CommonResponse.<List<QuestionResponse>>builder()
//                .message("Successfully retrieved questions by trainee")
//                .statusCode(HttpStatus.OK.value())
//                .data(questionPage.getContent())
//                .paging(pagingResponse)
//                .build();
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<CommonResponse<Void>> deleteQuestion(@PathVariable String id) {
//        questionService.d(id);
//        CommonResponse<Void> response = CommonResponse.<Void>builder()
//                .message("Question deleted successfully")
//                .statusCode(HttpStatus.OK.value())
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }
}
