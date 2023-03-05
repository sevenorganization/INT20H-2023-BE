package org.sevenorganization.int20h2023be.model.dto;

import org.sevenorganization.int20h2023be.model.enumeration.LanguageLevel;
import org.sevenorganization.int20h2023be.model.enumeration.LanguageName;

public record LanguageDto (
        LanguageName languageName,
        LanguageLevel languageLevel
) {
}
