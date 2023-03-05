package org.sevenorganization.int20h2023be.service.impl;

import lombok.RequiredArgsConstructor;
import org.sevenorganization.int20h2023be.model.entity.Application;
import org.sevenorganization.int20h2023be.model.entity.User;
import org.sevenorganization.int20h2023be.model.enumeration.ProjectRole;
import org.sevenorganization.int20h2023be.repository.ApplicationRepository;
import org.sevenorganization.int20h2023be.service.ApplicationService;
import org.sevenorganization.int20h2023be.service.ProjectMemberService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultApplicationService implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ProjectMemberService projectMemberService;


    @Override
    public void accept(Long id, User user) {
        Application application = applicationRepository.findById(id).get();

        projectMemberService.createProjectMember(user, application.getProject(), ProjectRole.MEMBER);

        applicationRepository.deleteById(id);
    }

    @Override
    public void cancel(Long id) {
        applicationRepository.deleteById(id);
        // trigger email process
    }

    @Override
    public void reject(Long id) {
        applicationRepository.deleteById(id);
        // trigger email process
    }
}
