package org.sevenorganization.int20h2023be.security.service;

import org.sevenorganization.int20h2023be.model.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface UserService {

    @Transactional
    User save(User user);

    User findByEmail(String email);

    void enableUser(User user);

}
