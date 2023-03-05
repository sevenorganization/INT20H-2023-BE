package org.sevenorganization.int20h2023be.service.impl;

import lombok.RequiredArgsConstructor;
import org.sevenorganization.int20h2023be.model.dto.ResumeDto;
import org.sevenorganization.int20h2023be.model.entity.Language;
import org.sevenorganization.int20h2023be.model.entity.Resume;
import org.sevenorganization.int20h2023be.model.entity.Technology;
import org.sevenorganization.int20h2023be.model.entity.User;
import org.sevenorganization.int20h2023be.repository.ResumeRepository;
import org.sevenorganization.int20h2023be.service.ResumeService;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class DefaultResumeService implements ResumeService {

    private final ResumeRepository resumeRepository;

    @Override
    public Resume updateResume(ResumeDto resumeDto, User user) {
        var technologies = resumeDto.technologies().stream()
                .map(technologyDto -> new Technology(technologyDto.technologyName(), technologyDto.technologyProficiency()))
                .collect(Collectors.toSet());
        var languages = resumeDto.languages().stream()
                .map(languageDto -> new Language(languageDto.languageName(), languageDto.languageLevel()))
                .collect(Collectors.toSet());
        Resume resume = new Resume(user, technologies, languages, resumeDto.portfolioUrl(), resumeDto.workload());
        return resumeRepository.save(resume);
    }
}
