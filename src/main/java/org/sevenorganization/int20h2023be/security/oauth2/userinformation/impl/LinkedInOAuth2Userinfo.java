package org.sevenorganization.int20h2023be.security.oauth2.userinformation.impl;

import org.sevenorganization.int20h2023be.security.oauth2.userinformation.OAuth2UserInfo;

import java.util.Map;

public class LinkedInOAuth2Userinfo  extends OAuth2UserInfo {

    public LinkedInOAuth2Userinfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public String getImageUrl() {
        return null;
    }
}
