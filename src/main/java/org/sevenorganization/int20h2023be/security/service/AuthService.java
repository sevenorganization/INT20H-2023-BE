package org.sevenorganization.int20h2023be.security.service;

import org.sevenorganization.int20h2023be.model.dto.AuthResponseDto;
import org.sevenorganization.int20h2023be.model.dto.SignUpRequestDto;
import org.sevenorganization.int20h2023be.model.dto.SignInRequestDto;
import org.sevenorganization.int20h2023be.model.entity.User;
import jakarta.transaction.Transactional;

public interface AuthService {

    @Transactional
    User standardSignUp(SignUpRequestDto signUpRequestDto);

    @Transactional
    AuthResponseDto signIn(SignInRequestDto signInRequestDto);

}
