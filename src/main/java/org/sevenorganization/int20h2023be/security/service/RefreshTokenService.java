package org.sevenorganization.int20h2023be.security.service;

import org.sevenorganization.int20h2023be.model.entity.RefreshToken;
import org.springframework.transaction.annotation.Transactional;

public interface RefreshTokenService {

    RefreshToken save(RefreshToken refreshToken);

    RefreshToken findByToken(String token);

    @Transactional
    void deleteByToken(String token);

}
