package com.igorblazhko.booking.service;

import com.igorblazhko.booking.dto.user.IgorBlazhkoUserResponse;
import com.igorblazhko.booking.dto.user.IgorBlazhkoUserUpdateRequest;
import com.igorblazhko.booking.exception.IgorBlazhkoConflictException;
import com.igorblazhko.booking.exception.IgorBlazhkoResourceNotFoundException;
import com.igorblazhko.booking.mapper.IgorBlazhkoUserMapper;
import com.igorblazhko.booking.repository.IgorBlazhkoRoleRepository;
import com.igorblazhko.booking.repository.IgorBlazhkoUserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class IgorBlazhkoUserService {

    private final IgorBlazhkoUserRepository userRepository;
    private final IgorBlazhkoRoleRepository roleRepository;
    private final IgorBlazhkoUserMapper userMapper;

    @Transactional(readOnly = true)
    public List<IgorBlazhkoUserResponse> getAllUsers() {
        return userRepository.findAll().stream().map(userMapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public IgorBlazhkoUserResponse getUserById(Long id) {
        return userMapper.toResponse(findUserEntity(id));
    }

    @Transactional
    public IgorBlazhkoUserResponse updateUser(Long id, IgorBlazhkoUserUpdateRequest request) {
        var user = findUserEntity(id);
        if (!user.getEmail().equals(request.email()) && userRepository.existsByEmail(request.email())) {
            throw new IgorBlazhkoConflictException("User with this email already exists");
        }
        user.setFullName(request.fullName());
        user.setEmail(request.email());
        user.setEnabled(request.enabled());
        user.setRole(roleRepository.findByName(request.role()).orElseThrow());
        return userMapper.toResponse(userRepository.save(user));
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.delete(findUserEntity(id));
    }

    @Transactional(readOnly = true)
    public com.igorblazhko.booking.entity.IgorBlazhkoUserEntity findUserEntity(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IgorBlazhkoResourceNotFoundException("User not found with id: " + id));
    }
}