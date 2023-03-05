package org.sevenorganization.int20h2023be.service.impl;

import org.sevenorganization.int20h2023be.model.enumeration.LanguageName;
import org.sevenorganization.int20h2023be.service.LanguageService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DefaultLanguageService implements LanguageService {

    @Override
    public List<LanguageName> findAllLanguageNames() {
        return Arrays.stream(LanguageName.values()).toList();
    }
}
