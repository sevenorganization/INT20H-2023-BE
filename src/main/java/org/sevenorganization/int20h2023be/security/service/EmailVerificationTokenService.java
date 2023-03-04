package org.sevenorganization.int20h2023be.security.service;

import org.sevenorganization.int20h2023be.model.entity.EmailVerificationToken;
import org.sevenorganization.int20h2023be.model.entity.User;

public interface EmailVerificationTokenService {

    EmailVerificationToken findByToken(String token);

    EmailVerificationToken generateEmailVerificationToken(User user);

    EmailVerificationToken regenerateEmailVerificationToken(String old);

    boolean validateEmailVerificationToken(String token);

    void sendEmailRegistrationToken(User user, EmailVerificationToken evt);

    void resendEmailVerificationToken(String old);

}
