package org.sevenorganization.int20h2023be.service.impl;

import lombok.RequiredArgsConstructor;
import org.sevenorganization.int20h2023be.exception.EntityNotFoundException;
import org.sevenorganization.int20h2023be.model.entity.Language;
import org.sevenorganization.int20h2023be.model.entity.Technology;
import org.sevenorganization.int20h2023be.model.entity.User;
import org.sevenorganization.int20h2023be.model.enumeration.LanguageName;
import org.sevenorganization.int20h2023be.model.enumeration.ProjectStatus;
import org.sevenorganization.int20h2023be.model.enumeration.TechnologyName;
import org.sevenorganization.int20h2023be.model.enumeration.Workload;
import org.sevenorganization.int20h2023be.repository.UserRepository;
import org.sevenorganization.int20h2023be.service.CandidateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultCandidateService implements CandidateService {

    private final UserRepository userRepository;

    @Override
    public Page<User> findAllCandidates(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User findCandidateByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> {
            throw new EntityNotFoundException("Candidate not found");
        });
    }

    @Override
    public boolean isCandidateFree(User user) {
        try {
            return user.getProjects().isEmpty() || user.getProjects().get(user.getProjects().size() - 1).getProject()
                    .getProjectStatus().equals(ProjectStatus.COMPLETED);
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }

    @Override
    public Page<User> findCandidatesByTechnologies(Pageable pageable, List<TechnologyName> technologies) {
        List<User> matchingCandidates = userRepository.findAll(pageable).stream()
                .filter(user -> extractTechnologyNames(user.getResume().getTechnologyStack()).containsAll(technologies))
                .toList();
        return new PageImpl<>(matchingCandidates);
    }

    private Collection<TechnologyName> extractTechnologyNames(Collection<Technology> technologies) {
        return technologies.stream()
                .map(Technology::getTechnologyName)
                .toList();
    }

    @Override
    public Page<User> findCandidatesByLanguages(Pageable pageable, List<LanguageName> languages) {
        List<User> matchingCandidates = userRepository.findAll(pageable).stream()
                .filter(user -> extractLanguageNames(user.getResume().getLanguages()).containsAll(languages))
                .toList();
        return new PageImpl<>(matchingCandidates);
    }

    private Collection<LanguageName> extractLanguageNames(Collection<Language> languages) {
        return languages.stream()
                .map(Language::getLanguageName)
                .toList();
    }

    @Override
    public Page<User> findCandidatesByWorkload(Pageable pageable, Workload workload) {
        List<User> matchingCandidates = userRepository.findAll(pageable).stream()
                .filter(user -> user.getResume().getWorkload().equals(workload))
                .toList();
        return new PageImpl<>(matchingCandidates);
    }
}
