package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.request.LoginRequest;
import com.enigma.Instructor_Led.dto.request.RegitserTraineeRequest;
import com.enigma.Instructor_Led.dto.response.LoginResponse;
import com.enigma.Instructor_Led.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(RegitserTraineeRequest request);
    LoginResponse login(LoginRequest request);
}
