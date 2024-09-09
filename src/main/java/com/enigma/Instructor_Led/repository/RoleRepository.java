package com.enigma.Instructor_Led.repository;

import com.enigma.Instructor_Led.constant.UserRole;
import com.enigma.Instructor_Led.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(UserRole role);
}
