package com.enigma.Instructor_Led.controller;


import com.enigma.Instructor_Led.constant.PathUrl;
import com.enigma.Instructor_Led.dto.request.LoginRequest;
import com.enigma.Instructor_Led.dto.request.RegitserTraineeRequest;
import com.enigma.Instructor_Led.dto.response.CommonResponse;
import com.enigma.Instructor_Led.dto.response.LoginResponse;
import com.enigma.Instructor_Led.dto.response.RegisterResponse;
import com.enigma.Instructor_Led.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = PathUrl.AUTH_API)
public class AuthController {
    private final AuthService authService;

    @PostMapping(path = "/register")
    public ResponseEntity<CommonResponse<?>> registerUser(@RequestBody RegitserTraineeRequest request){
        RegisterResponse register = authService.register(request);
        CommonResponse<RegisterResponse> response = CommonResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Register successfully")
                .data(register)
                .build();
        return ResponseEntity.status(201).body(response);
    }


    @PostMapping(path = "/login")
    public ResponseEntity<CommonResponse<?>> login(@RequestBody LoginRequest request){
        LoginResponse loginResponse = authService.login(request);
        CommonResponse<LoginResponse> response = CommonResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message("Login successfully")
                .data(loginResponse)
                .build();
        return ResponseEntity.status(201).body(response);
    }
}
