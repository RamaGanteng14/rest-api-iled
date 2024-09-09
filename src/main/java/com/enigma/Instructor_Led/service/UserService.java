package com.enigma.Instructor_Led.service;

import com.enigma.Instructor_Led.entity.UserAccount;

public interface UserService  {
    UserAccount getByUserId(String id);
    UserAccount getByContext();
}
