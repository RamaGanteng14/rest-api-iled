package com.enigma.Instructor_Led.dto.request;

import com.enigma.Instructor_Led.dto.response.ProgrammingLanguageResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegitserTraineeRequest {
    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password cannot be blank")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "NIK is required")
    private String nik;

    @NotBlank(message = "Birth date is required")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "format tanggal harus 'yyyy-MM-dd'")
    private Date birthDate;

    @NotBlank(message = "Address is required")
    private String address;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@gmail\\.com$", message = "email harus menggunakan domain @gmail.com")
    @Email
    private String email;

    @Pattern(regexp = "^08\\d{9,11}$", message = "nomor telepon harus valid dan diawali dengan '08' diikuti oleh 9 hingga 11 angka")
    @NotBlank(message = "Phone Number is required")
    private String phoneNumber;

    @NotBlank(message = "Programming Language is required")
    private String programmingLanguageId;
}
