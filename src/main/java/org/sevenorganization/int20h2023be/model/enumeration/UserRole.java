package org.sevenorganization.int20h2023be.model.enumeration;

import org.springframework.security.core.GrantedAuthority;

public enum UserRole implements GrantedAuthority {
    CANDIDATE, RECRUITER, COACH;
    @Override
    public String getAuthority() {
        return name();
    }
}
