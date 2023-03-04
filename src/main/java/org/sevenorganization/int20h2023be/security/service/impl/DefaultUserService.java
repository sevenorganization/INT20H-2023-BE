package org.sevenorganization.int20h2023be.security.service.impl;

import org.sevenorganization.int20h2023be.exception.BadCredentialsException;
import org.sevenorganization.int20h2023be.exception.EmailTakenException;
import org.sevenorganization.int20h2023be.model.entity.User;
import org.sevenorganization.int20h2023be.repository.UserRepository;
import org.sevenorganization.int20h2023be.security.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User save(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new EmailTakenException();
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User update(User user) {
        return userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(BadCredentialsException::new);
    }

    @Override
    public void enableUser(User user) {
        userRepository.enableUser(user.getEmail());
    }
}
