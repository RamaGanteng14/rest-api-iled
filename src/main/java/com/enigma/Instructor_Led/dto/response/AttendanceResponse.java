package com.enigma.Instructor_Led.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceResponse {
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    private ScheduleResponse schedule;

    private List<AttendanceDetailResponse> attendanceDetails;
}
