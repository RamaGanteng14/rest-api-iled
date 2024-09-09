package com.enigma.Instructor_Led.controller;

import com.enigma.Instructor_Led.constant.PathUrl;
import com.enigma.Instructor_Led.dto.request.CreateTraineeRequest;
import com.enigma.Instructor_Led.dto.request.UpdateTraineeRequest;
import com.enigma.Instructor_Led.dto.response.TraineeResponse;
import com.enigma.Instructor_Led.dto.response.CommonResponse;
import com.enigma.Instructor_Led.dto.response.PagingResponse;
import com.enigma.Instructor_Led.service.TraineeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathUrl.TRAINEE)
@RequiredArgsConstructor
public class TraineeController {

    private final TraineeService traineeService;

//    @PostMapping
//    public ResponseEntity<CommonResponse<TraineeResponse>> createTrainee(@RequestBody CreateTraineeRequest createTraineeRequest) {
//        TraineeResponse traineeResponse = traineeService.create(createTraineeRequest);
//        CommonResponse<TraineeResponse> response = CommonResponse.<TraineeResponse>builder()
//                .message("Trainee created successfully")
//                .statusCode(HttpStatus.CREATED.value())
//                .data(traineeResponse)
//                .build();
//        return new ResponseEntity<>(response, HttpStatus.CREATED);
//    }

    @PutMapping
    public ResponseEntity<CommonResponse<TraineeResponse>> updateTrainee(@RequestBody UpdateTraineeRequest updateTraineeRequest) {
        TraineeResponse traineeResponse = traineeService.update(updateTraineeRequest);
        CommonResponse<TraineeResponse> response = CommonResponse.<TraineeResponse>builder()
                .message("Trainee updated successfully")
                .statusCode(HttpStatus.OK.value())
                .data(traineeResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<TraineeResponse>> getTraineeById(@PathVariable String id) {
        TraineeResponse traineeResponse = traineeService.getById(id);
        CommonResponse<TraineeResponse> response = CommonResponse.<TraineeResponse>builder()
                .message("Trainee fetched successfully")
                .statusCode(HttpStatus.OK.value())
                .data(traineeResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TraineeResponse>>> getAllTrainees(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<TraineeResponse> traineePage = traineeService.getAll(pageable);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(traineePage.getTotalPages())
                .totalElement(traineePage.getTotalElements())
                .hasNext(traineePage.hasNext())
                .hasPrevious(traineePage.hasPrevious())
                .build();

        CommonResponse<List<TraineeResponse>> response = CommonResponse.<List<TraineeResponse>>builder()
                .message("Successfully retrieved all trainees")
                .statusCode(HttpStatus.OK.value())
                .data(traineePage.getContent())
                .paging(pagingResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteTrainee(@PathVariable String id) {
        traineeService.delete(id);
        CommonResponse<Void> response = CommonResponse.<Void>builder()
                .message("Trainee deleted successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
