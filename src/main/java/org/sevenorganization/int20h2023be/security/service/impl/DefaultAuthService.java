package org.sevenorganization.int20h2023be.security.service.impl;

import org.sevenorganization.int20h2023be.model.dto.AuthResponseDto;
import org.sevenorganization.int20h2023be.model.dto.SignUpRequestDto;
import org.sevenorganization.int20h2023be.model.dto.SignInRequestDto;
import org.sevenorganization.int20h2023be.model.entity.AuthProvider;
import org.sevenorganization.int20h2023be.model.entity.EmailVerificationToken;
import org.sevenorganization.int20h2023be.model.entity.User;
import org.sevenorganization.int20h2023be.security.service.JwtService;
import org.sevenorganization.int20h2023be.security.userdetails.DefaultUserDetails;
import org.sevenorganization.int20h2023be.security.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultAuthService implements AuthService {

    private final DefaultUserService defaultUserService;
    private final DefaultEmailVerificationTokenService defaultEmailVerificationTokenService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public User standardSignUp(SignUpRequestDto signUpRequestDto) {
        User user = new User(signUpRequestDto.email(), signUpRequestDto.password());

        user.setLastName(signUpRequestDto.lastName());
        user.setFirstName(signUpRequestDto.firstName());
        fulfillUserCommonData(user, signUpRequestDto);

        User registeredUser = defaultUserService.save(user);

        EmailVerificationToken evt = defaultEmailVerificationTokenService.generateEmailVerificationToken(registeredUser);
        defaultEmailVerificationTokenService.sendEmailRegistrationToken(registeredUser, evt);

        return registeredUser;
    }

    public User oauth2SignUp(SignUpRequestDto signUpRequestDto, Authentication authentication) {
        DefaultUserDetails defaultUserDetails = (DefaultUserDetails) authentication.getPrincipal();
        User user = defaultUserService.findByEmail(defaultUserDetails.getEmail());

        fulfillUserCommonData(user, signUpRequestDto);

        return defaultUserService.update(user);
    }

    private void fulfillUserCommonData(User user, SignUpRequestDto signUpRequestDto) {
        user.setProvider(AuthProvider.valueOf(signUpRequestDto.authProvider()));
    }

    @Override
    public AuthResponseDto signIn(SignInRequestDto signInRequestDto) {
        User user = defaultUserService.findByEmail(signInRequestDto.email());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                user.getEmail(), signInRequestDto.password()
        ));
        return generateAuthResponseFromUser(DefaultUserDetails.fromUser(user));
    }

    public AuthResponseDto generateAuthResponseFromUser(DefaultUserDetails defaultUserDetails) {
        String accessToken = jwtService.generateAccessToken(defaultUserDetails);
        String refreshToken = jwtService.generateRefreshToken(defaultUserDetails);
        return new AuthResponseDto(accessToken, refreshToken);
    }

    public AuthResponseDto generateAuthResponseFromUserMail(String mail) {
        User user = defaultUserService.findByEmail(mail);
        var dud = DefaultUserDetails.fromUser(user);
        String accessToken = jwtService.generateAccessToken(dud);
        String refreshToken = jwtService.generateRefreshToken(dud);
        return new AuthResponseDto(accessToken, refreshToken);
    }

}
