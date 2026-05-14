package com.igorblazhko.booking.service;

import com.igorblazhko.booking.dto.auth.IgorBlazhkoAuthResponse;
import com.igorblazhko.booking.dto.auth.IgorBlazhkoLoginRequest;
import com.igorblazhko.booking.dto.auth.IgorBlazhkoRegisterRequest;
import com.igorblazhko.booking.entity.IgorBlazhkoRoleName;
import com.igorblazhko.booking.entity.IgorBlazhkoUserEntity;
import com.igorblazhko.booking.exception.IgorBlazhkoConflictException;
import com.igorblazhko.booking.repository.IgorBlazhkoRoleRepository;
import com.igorblazhko.booking.repository.IgorBlazhkoUserRepository;
import com.igorblazhko.booking.security.IgorBlazhkoJwtUtil;
import com.igorblazhko.booking.security.IgorBlazhkoUserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IgorBlazhkoAuthService {

    private final IgorBlazhkoUserRepository userRepository;
    private final IgorBlazhkoRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final IgorBlazhkoJwtUtil jwtUtil;

    @Transactional
    public IgorBlazhkoAuthResponse register(IgorBlazhkoRegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new IgorBlazhkoConflictException("User with this email already exists");
        }

        IgorBlazhkoUserEntity user = new IgorBlazhkoUserEntity();
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(roleRepository.findByName(IgorBlazhkoRoleName.ROLE_USER).orElseThrow());
        IgorBlazhkoUserEntity savedUser = userRepository.save(user);
        return buildResponse(savedUser, null);
    }

    public IgorBlazhkoAuthResponse login(IgorBlazhkoLoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        IgorBlazhkoUserEntity user = userRepository.findByEmail(request.email()).orElseThrow();
        String token = jwtUtil.generateToken(new IgorBlazhkoUserPrincipal(user));
        return buildResponse(user, token);
    }

    private IgorBlazhkoAuthResponse buildResponse(IgorBlazhkoUserEntity user, String token) {
        return new IgorBlazhkoAuthResponse(
                token,
                token == null ? null : "Bearer",
                user.getId(),
                user.getFullName(),
            user.getEmail(),
            user.getRole().getName().name()
        );
    }
}