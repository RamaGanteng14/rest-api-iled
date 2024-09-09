package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.dto.response.JwtClaims;
import com.enigma.Instructor_Led.entity.UserAccount;

public interface JwtService {
    String generateToken(UserAccount userAccount);
    boolean verifyJwtToken(String token);
    JwtClaims getClaimsByToken(String token);
}
