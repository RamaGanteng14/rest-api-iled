package com.enigma.Instructor_Led.service.impl;

import com.enigma.Instructor_Led.constant.UserRole;
import com.enigma.Instructor_Led.dto.request.LoginRequest;
import com.enigma.Instructor_Led.dto.request.RegitserTraineeRequest;
import com.enigma.Instructor_Led.dto.response.LoginResponse;
import com.enigma.Instructor_Led.dto.response.RegisterResponse;
import com.enigma.Instructor_Led.entity.ProgrammingLanguage;
import com.enigma.Instructor_Led.entity.Role;
import com.enigma.Instructor_Led.entity.Trainee;
import com.enigma.Instructor_Led.entity.UserAccount;
import com.enigma.Instructor_Led.repository.UserAccountRepository;
import com.enigma.Instructor_Led.service.*;
import com.enigma.Instructor_Led.util.Validation;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserAccountRepository userAccountRepository;
    private final RoleService roleService;
    private final ProgrammingLanguageService programmingLanguageService;
    private final PasswordEncoder passwordEncoder;
    private final TraineeService traineeService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final Validation validation;

    @Value("${iled.admin.username}")
    private String adminUsername;
    @Value("${iled.admin.password}")
    private String adminPassword;

    @Transactional
    @PostConstruct
    public void initSuperAdmin(){
        Optional<UserAccount> currentUserSuperAdmin = userAccountRepository.findByUsername(adminUsername);
        if(currentUserSuperAdmin.isPresent())return;

        Role trainee = roleService.getOrSave(UserRole.ROLE_TRAINEE);
        Role admin = roleService.getOrSave(UserRole.ROLE_ADMIN);
        Role trainer = roleService.getOrSave(UserRole.ROLE_TRAINER);

        UserAccount account = UserAccount.builder()
                .username(adminUsername)
                .password(passwordEncoder.encode(adminPassword))
                .role(List.of(admin, trainee, trainer))
                .isEnable(true)
                .build();
        userAccountRepository.save(account);

    }

    @Override
    public RegisterResponse register(RegitserTraineeRequest request) {
        validation.validate(request);
        Role role = roleService.getOrSave(UserRole.ROLE_TRAINEE);
        String hashPassword = passwordEncoder.encode(request.getPassword());

        UserAccount account = UserAccount.builder()
                .username(request.getUsername())
                .password(hashPassword)
                .role(List.of(role))
                .isEnable(true)
                .build();
        userAccountRepository.saveAndFlush(account);

        ProgrammingLanguage programmingLanguage = programmingLanguageService
                .getOneById(request.getProgrammingLanguageId());

        Trainee trainee = Trainee.builder()
                .name(request.getName())
                .nik(request.getNik())
                .email(request.getEmail())
                .address(request.getEmail())
                .birthDate(request.getBirthDate())
                .phoneNumber(request.getPhoneNumber())
                .programmingLanguage(programmingLanguage)
                .userAccount(account)
                .build();
        traineeService.create(trainee);

        return RegisterResponse.builder()
                .username(account.getUsername())
                .roles(account.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }

    @Transactional(readOnly = true)
    @Override
    public LoginResponse login (LoginRequest request) {

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        );
        Authentication authenticate = authenticationManager.authenticate(authentication);
        UserAccount userAccount = (UserAccount) authenticate.getPrincipal();

        String token = jwtService.generateToken (userAccount);

        return LoginResponse.builder()
                .token(token)
                .username(userAccount.getUsername())
                .roles (userAccount.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
