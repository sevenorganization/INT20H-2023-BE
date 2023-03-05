package org.sevenorganization.int20h2023be.service.impl;

import lombok.RequiredArgsConstructor;
import org.sevenorganization.int20h2023be.model.entity.User;
import org.sevenorganization.int20h2023be.model.entity.Project;
import org.sevenorganization.int20h2023be.model.entity.ProjectMember;
import org.sevenorganization.int20h2023be.model.enumeration.ProjectRole;
import org.sevenorganization.int20h2023be.repository.ApplicationRepository;
import org.sevenorganization.int20h2023be.repository.InvitationsRepository;
import org.sevenorganization.int20h2023be.repository.ProjectMemberRepository;
import org.sevenorganization.int20h2023be.service.ProjectMemberService;
import org.sevenorganization.int20h2023be.service.ProjectService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultProjectMemberService implements ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final InvitationsRepository invitationsRepository;
    private final ApplicationRepository applicationRepository;

    @Override
    public ProjectMember createProjectMember(User user, Project project, ProjectRole projectRole) {
        ProjectMember projectMember = new ProjectMember(user, project, projectRole);

        invitationsRepository.deleteAll(user.getInvitations());
        applicationRepository.deleteAll(user.getApplications());

        return projectMemberRepository.save(projectMember);
    }

    @Override
    public ProjectMember updateProjectMember(ProjectMember projectMember) {
        return projectMemberRepository.save(projectMember);
    }

    @Override
    public void deleteProjectMember(ProjectMember projectMember) {
        projectMemberRepository.delete(projectMember);
    }
}
