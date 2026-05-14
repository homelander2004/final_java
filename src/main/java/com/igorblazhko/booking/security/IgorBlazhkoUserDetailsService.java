package com.igorblazhko.booking.security;

import com.igorblazhko.booking.exception.IgorBlazhkoResourceNotFoundException;
import com.igorblazhko.booking.repository.IgorBlazhkoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IgorBlazhkoUserDetailsService implements UserDetailsService {

    private final IgorBlazhkoUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .map(IgorBlazhkoUserPrincipal::new)
                .orElseThrow(() -> new IgorBlazhkoResourceNotFoundException("User not found with email: " + username));
    }
}