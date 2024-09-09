package com.enigma.Instructor_Led.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateScheduleRequest {

    @NotBlank(message = "Date is required")
    private Date date;

    @NotBlank(message = "Topic is required")
    private String topic;

    @NotBlank
    private String link;

    private String programmingLanguage;

    @NotBlank(message = "Trainer is required")
    private String trainerId;
}
