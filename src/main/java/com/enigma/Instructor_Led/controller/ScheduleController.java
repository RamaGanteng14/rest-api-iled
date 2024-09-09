package com.enigma.Instructor_Led.controller;

import com.enigma.Instructor_Led.constant.PathUrl;
import com.enigma.Instructor_Led.dto.request.CreateScheduleRequest;
import com.enigma.Instructor_Led.dto.request.UpdateScheduleRequest;
import com.enigma.Instructor_Led.dto.response.CommonResponse;
import com.enigma.Instructor_Led.dto.response.PagingResponse;
import com.enigma.Instructor_Led.dto.response.ScheduleResponse;
import com.enigma.Instructor_Led.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(PathUrl.SCHEDULE)
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;

    @PostMapping
    public ResponseEntity<CommonResponse<ScheduleResponse>> createSchedule(@RequestBody CreateScheduleRequest createScheduleRequest) {
        ScheduleResponse scheduleResponse = scheduleService.createSchedule(createScheduleRequest);
        CommonResponse<ScheduleResponse> response = CommonResponse.<ScheduleResponse>builder()
                .message("Schedule created successfully")
                .statusCode(HttpStatus.CREATED.value())
                .data(scheduleResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<CommonResponse<ScheduleResponse>> updateSchedule(@RequestBody UpdateScheduleRequest updateScheduleRequest) {
        ScheduleResponse scheduleResponse = scheduleService.updateSchedule(updateScheduleRequest);
        CommonResponse<ScheduleResponse> response = CommonResponse.<ScheduleResponse>builder()
                .message("Schedule updated successfully")
                .statusCode(HttpStatus.OK.value())
                .data(scheduleResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CommonResponse<ScheduleResponse>> getScheduleById(@PathVariable String id) {
        ScheduleResponse scheduleResponse = scheduleService.getScheduleById(id);
        CommonResponse<ScheduleResponse> response = CommonResponse.<ScheduleResponse>builder()
                .message("Schedule fetched successfully")
                .statusCode(HttpStatus.OK.value())
                .data(scheduleResponse)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ScheduleResponse>>> getAllSchedules(
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<ScheduleResponse> schedulePage = scheduleService.getAllSchedules(pageable);

        PagingResponse pagingResponse = PagingResponse.builder()
                .page(page)
                .size(size)
                .totalPages(schedulePage.getTotalPages())
                .totalElement(schedulePage.getTotalElements())
                .hasNext(schedulePage.hasNext())
                .hasPrevious(schedulePage.hasPrevious())
                .build();

        CommonResponse<List<ScheduleResponse>> response = CommonResponse.<List<ScheduleResponse>>builder()
                .message("Successfully retrieved all schedules")
                .statusCode(HttpStatus.OK.value())
                .data(schedulePage.getContent())
                .paging(pagingResponse)
                .build();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<Void>> deleteSchedule(@PathVariable String id) {
        scheduleService.deleteSchedule(id);
        CommonResponse<Void> response = CommonResponse.<Void>builder()
                .message("Schedule deleted successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
