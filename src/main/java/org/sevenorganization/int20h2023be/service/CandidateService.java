package org.sevenorganization.int20h2023be.service;

import org.sevenorganization.int20h2023be.model.entity.ProjectMember;
import org.sevenorganization.int20h2023be.model.entity.User;
import org.sevenorganization.int20h2023be.model.enumeration.LanguageName;
import org.sevenorganization.int20h2023be.model.enumeration.TechnologyName;
import org.sevenorganization.int20h2023be.model.enumeration.Workload;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CandidateService {
    Page<User> findAllCandidates(Pageable pageable);

    Page<User> findCandidatesByTechnologies(Pageable pageable, List<TechnologyName> technologies);

    Page<User> findCandidatesByLanguages(Pageable pageable, List<LanguageName> languages);

    Page<User> findCandidatesByWorkload(Pageable pageable, Workload workload);

    boolean isCandidateFree(User user);

    User findCandidateByEmail(String email);
    ProjectMember findActiveProjectMember(User user);

    boolean hasNoActiveProject(User user);

}
