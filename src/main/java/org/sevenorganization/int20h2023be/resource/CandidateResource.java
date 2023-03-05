package org.sevenorganization.int20h2023be.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.sevenorganization.int20h2023be.model.entity.Language;
import org.sevenorganization.int20h2023be.model.entity.Technology;
import org.sevenorganization.int20h2023be.model.entity.User;
import org.sevenorganization.int20h2023be.model.enumeration.LanguageName;
import org.sevenorganization.int20h2023be.model.enumeration.ProjectStatus;
import org.sevenorganization.int20h2023be.model.enumeration.TechnologyName;
import org.sevenorganization.int20h2023be.model.enumeration.Workload;
import org.sevenorganization.int20h2023be.service.CandidateService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/candidates")
public class CandidateResource {

    private final CandidateService candidateService;

    @GetMapping
    public ResponseEntity<Page<User>> findAllCandidates(
            @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
            @RequestParam(name = "tech", required = false, defaultValue = "") String[] tech,
            @RequestParam(name = "lang", required = false, defaultValue = "") String[] lang,
            @RequestParam(name = "workload", required = false, defaultValue = "") String workload,
            @RequestParam(name = "free", required = false, defaultValue = "false") boolean free,
            @RequestParam(name = "hasPortfolio", required = false, defaultValue = "false") boolean hasPortfolio
    ) {
        final PageRequest pageable = PageRequest.of(page, size);
        List<User> res = candidateService.findAllCandidates(pageable).toList();

        if (tech.length != 0) {
            res = res.stream()
                    .filter(user -> user.getResume() != null)
                    .filter(user -> extractTechnologyNames(user.getResume().getTechnologyStack()).containsAll(mapTech(tech)))
                    .toList();
        }

        if (lang.length != 0) {
            res = res.stream()
                    .filter(user -> user.getResume() != null)
                    .filter(user -> extractLanguageNames(user.getResume().getLanguages()).containsAll(mapLang(lang)))
                    .toList();
        }

        if (!workload.isBlank()) {
            res = res.stream()
                    .filter(user -> user.getResume() != null)
                    .filter(user -> user.getResume().getWorkload().equals(Workload.valueOf(workload.toUpperCase())))
                    .toList();
        }

        if (free) {
            res = res.stream()
                    .filter(user -> {
                        boolean hasNoLast = user.getProjects().isEmpty();
                        if (hasNoLast) return true;

                        boolean lastCompleted = user.getProjects().get(user.getProjects().size() - 1).getProject()
                                .getProjectStatus().equals(ProjectStatus.COMPLETED);
                        if (lastCompleted) return true;

                        return false;
                    })
                    .toList();
        }

        if (hasPortfolio) {
            res = res.stream()
                    .filter(user -> user.getResume() != null)
                    .filter(user -> !user.getResume().getPortfolioUrl().isBlank())
                    .toList();
        }

        return ResponseEntity.ok(new PageImpl<>(res));
    }

    private Collection<TechnologyName> extractTechnologyNames(Collection<Technology> technologies) {
        return technologies.stream()
                .map(Technology::getTechnologyName)
                .toList();
    }

    private List<TechnologyName> mapTech(String[] tech) {
        return Arrays.stream(tech)
                .map(s -> TechnologyName.valueOf(s.toUpperCase()))
                .toList();
    }

    private Collection<LanguageName> extractLanguageNames(Collection<Language> languages) {
        return languages.stream()
                .map(Language::getLanguageName)
                .toList();
    }

    private List<LanguageName> mapLang(String[] lang) {
        return Arrays.stream(lang)
                .map(s -> LanguageName.valueOf(s.toUpperCase()))
                .toList();
    }
}
