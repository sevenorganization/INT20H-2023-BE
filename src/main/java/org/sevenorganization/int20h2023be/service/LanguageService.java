package org.sevenorganization.int20h2023be.service;

import org.sevenorganization.int20h2023be.model.enumeration.LanguageName;

import java.util.List;

public interface LanguageService {
    List<LanguageName> findAllLanguageNames();
}
