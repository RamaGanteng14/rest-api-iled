package com.enigma.Instructor_Led.dto.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateScheduleRequest {
    @NotBlank(message = "Id is required")
    private String id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Pattern(regexp = "^\\\\d{4}-\\d{2}-\\d{2}$", message = "format tanggal harus 'yyyy-MM-dd'")
    private Date date;

    @NotBlank(message = "Topic is required")
    private String topic;

    @NotBlank(message = "link is required")
    private String link;

    @NotBlank(message = "Trainer is required")
    private String trainerId;

}
