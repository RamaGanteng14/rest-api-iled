package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.CreateScheduleRequest;
import com.enigma.Instructor_Led.dto.request.UpdateScheduleRequest;
import com.enigma.Instructor_Led.dto.response.AdminResponse;
import com.enigma.Instructor_Led.dto.response.ScheduleResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ScheduleService {
    ScheduleResponse createSchedule(CreateScheduleRequest request);
    ScheduleResponse updateSchedule(UpdateScheduleRequest request);
    ScheduleResponse getScheduleById(String id);
//    List<ScheduleResponse> getAllSchedules();
    Page<ScheduleResponse> getAllSchedules(Pageable pageable);
    void deleteSchedule(String id);

}
