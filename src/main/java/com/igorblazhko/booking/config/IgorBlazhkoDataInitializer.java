package com.igorblazhko.booking.config;

import com.igorblazhko.booking.entity.IgorBlazhkoRoleEntity;
import com.igorblazhko.booking.entity.IgorBlazhkoRoleName;
import com.igorblazhko.booking.entity.IgorBlazhkoUserEntity;
import com.igorblazhko.booking.repository.IgorBlazhkoRoleRepository;
import com.igorblazhko.booking.repository.IgorBlazhkoUserRepository;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class IgorBlazhkoDataInitializer implements CommandLineRunner {

    private final IgorBlazhkoRoleRepository roleRepository;
    private final IgorBlazhkoUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        Arrays.stream(IgorBlazhkoRoleName.values()).forEach(roleName -> roleRepository.findByName(roleName)
                .orElseGet(() -> {
                    IgorBlazhkoRoleEntity role = new IgorBlazhkoRoleEntity();
                    role.setName(roleName);
                    return roleRepository.save(role);
                }));

        userRepository.findByEmail("admin@igorblazhko.com").orElseGet(() -> {
            IgorBlazhkoUserEntity admin = new IgorBlazhkoUserEntity();
            admin.setFullName("Igor Blazhko Admin");
            admin.setEmail("admin@igorblazhko.com");
            admin.setPassword(passwordEncoder.encode("Admin12345"));
            admin.setRole(roleRepository.findByName(IgorBlazhkoRoleName.ROLE_ADMIN).orElseThrow());
            log.info("Default admin created: admin@igorblazhko.com / Admin12345");
            return userRepository.save(admin);
        });
    }
}