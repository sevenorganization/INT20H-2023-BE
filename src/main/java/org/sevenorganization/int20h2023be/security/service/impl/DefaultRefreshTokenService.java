package org.sevenorganization.int20h2023be.security.service.impl;

import org.sevenorganization.int20h2023be.exception.EntityNotFoundException;
import org.sevenorganization.int20h2023be.model.entity.RefreshToken;
import org.sevenorganization.int20h2023be.repository.RefreshTokenRepository;
import org.sevenorganization.int20h2023be.security.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultRefreshTokenService implements RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token).orElseThrow(() -> {
            throw new EntityNotFoundException("JWT refresh token not found, re-authenticate please");
        });
    }

    @Override
    public void deleteByToken(String token) {
        refreshTokenRepository.deleteByToken(token);
    }

}
