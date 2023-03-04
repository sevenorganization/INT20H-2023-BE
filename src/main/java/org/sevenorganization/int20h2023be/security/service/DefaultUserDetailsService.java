package org.sevenorganization.int20h2023be.security.service;

import org.sevenorganization.int20h2023be.model.entity.User;
import org.sevenorganization.int20h2023be.repository.UserRepository;
import org.sevenorganization.int20h2023be.security.userdetails.DefaultUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> {
            throw new UsernameNotFoundException("Cannot load user by username (email): username (email) not found");
        });
        return DefaultUserDetails.fromUser(user);
    }
}
