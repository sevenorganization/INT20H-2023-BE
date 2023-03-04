package org.sevenorganization.int20h2023be.resource;

import org.sevenorganization.int20h2023be.model.dto.*;
import org.sevenorganization.int20h2023be.model.entity.AuthProvider;
import org.sevenorganization.int20h2023be.model.entity.User;
import org.sevenorganization.int20h2023be.security.service.JwtService;
import org.sevenorganization.int20h2023be.security.userdetails.DefaultUserDetails;
import org.sevenorganization.int20h2023be.security.service.impl.DefaultAuthService;
import org.sevenorganization.int20h2023be.security.service.impl.DefaultEmailVerificationTokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static org.sevenorganization.int20h2023be.security.oauth2.constant.DefaultOAuth2Constant.OAUTH2_JWT_TOKEN_PASS_NAME;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthResource {

    private final DefaultAuthService defaultAuthService;
    private final DefaultEmailVerificationTokenService defaultEmailVerificationTokenService;
    private final JwtService jwtService;

    @GetMapping("/verifyRegistration")
    public ResponseEntity<String> verifyRegistration(@RequestParam("token") String token) {
        if (defaultEmailVerificationTokenService.validateEmailVerificationToken(token)) {
            return ResponseEntity.ok("User is verified");
        }
        return new ResponseEntity<>("Cannot verify user, invalid verification link", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/resendEmailVerificationToken")
    public ResponseEntity<String> resendEmailVerificationToken(@RequestParam("token") String old) {
        defaultEmailVerificationTokenService.resendEmailVerificationToken(old);
        return ResponseEntity.ok("Verification link is resend");
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponseDto> signIn(@Valid @RequestBody SignInRequestDto signInRequestDto) {
        if (nonNull(signInRequestDto)) {
            return new ResponseEntity<>(defaultAuthService.signIn(signInRequestDto), HttpStatus.OK);
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new RuntimeException("blblablalb");
        }

        DefaultUserDetails defaultUserDetails = (DefaultUserDetails) authentication.getPrincipal();
        return new ResponseEntity<>(defaultAuthService.generateAuthResponseFromUser(defaultUserDetails), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<User> signUpUser(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        String authProvider = signUpRequestDto.authProvider();

        if (!authProvider.equalsIgnoreCase(AuthProvider.LOCAL.toString())) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            return new ResponseEntity<>(defaultAuthService.oauth2SignUp(signUpRequestDto, authentication), HttpStatus.OK);
        }

        if (isNull(signUpRequestDto.email()) || isNull(signUpRequestDto.password())) {
            throw new RuntimeException("agadgdfgda");
        }

        return new ResponseEntity<>(defaultAuthService.standardSignUp(signUpRequestDto), HttpStatus.OK);
    }

    @GetMapping("/sign-up")
    public ResponseEntity<AuthResponseDto> getSignUp(HttpServletRequest request) {
        Object object = request.getSession().getAttribute(OAUTH2_JWT_TOKEN_PASS_NAME);

        if (nonNull(object) && object instanceof Authentication authentication) {
            // oauth2 registration
            DefaultUserDetails defaultUserDetails = (DefaultUserDetails) authentication.getPrincipal();
            if (defaultUserDetails.getEmail() == null) {
                // means standard login (email/password)
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            return new ResponseEntity<>(defaultAuthService.generateAuthResponseFromUserMail(defaultUserDetails.getEmail()), HttpStatus.OK);
        }

        // means standard login (email/password)
        return new ResponseEntity<>(null, HttpStatus.OK);
    }


    @GetMapping("/my-profile")
    public ResponseEntity<AuthResponseDto> getMyProfile(HttpServletRequest request) {
        Object object = request.getSession().getAttribute(OAUTH2_JWT_TOKEN_PASS_NAME);

        if (nonNull(object) && object instanceof Authentication authentication) {
            // oauth2 registration
            DefaultUserDetails defaultUserDetails = (DefaultUserDetails) authentication.getPrincipal();
            return new ResponseEntity<>(defaultAuthService.generateAuthResponseFromUser(defaultUserDetails), HttpStatus.OK);
        }

        // means standard login (email/password)
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

//    @GetMapping("/sign-in")
//    public ResponseEntity<AuthResponseDto> getSignIn() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new RuntimeException("blblablalb");
//        }
//
//        DefaultUserDetails defaultUserDetails = (DefaultUserDetails) authentication.getPrincipal();
//        return new ResponseEntity<>(defaultAuthService.generateAuthResponseFromUser(defaultUserDetails), HttpStatus.OK);
//
//        // means standard login (email/password)
//        return new ResponseEntity<>(null, HttpStatus.OK);
//    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthResponseDto> refreshToken(@Valid @RequestBody RefreshTokenRequestDto refreshTokenRequestDto) {
        return new ResponseEntity<>(jwtService.refreshToken(refreshTokenRequestDto), HttpStatus.OK);
    }
}
