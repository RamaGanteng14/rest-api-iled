package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.constant.UserRole;
import com.enigma.Instructor_Led.entity.Role;
import com.enigma.Instructor_Led.repository.RoleRepository;
import com.enigma.Instructor_Led.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Transactional
    @Override
    public Role getOrSave(UserRole role) {
        return roleRepository.findByRole(role)
                .orElseGet(()-> roleRepository.saveAndFlush(
                                Role.builder()
                                        .role(role)
                                        .build()
                        )
                );
    }

}