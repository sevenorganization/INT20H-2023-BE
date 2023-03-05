package org.sevenorganization.int20h2023be.model.dto;

import org.sevenorganization.int20h2023be.model.enumeration.Workload;

import java.util.List;

public record ResumeDto(
        Workload workload,
        List<LanguageDto> languages,
        List<TechnologyDto> technologies,
        String portfolioUrl
) {
}
