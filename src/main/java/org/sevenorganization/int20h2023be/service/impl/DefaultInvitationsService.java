package org.sevenorganization.int20h2023be.service.impl;

import lombok.RequiredArgsConstructor;
import org.sevenorganization.int20h2023be.model.entity.User;
import org.sevenorganization.int20h2023be.model.entity.Invitation;
import org.sevenorganization.int20h2023be.model.enumeration.ProjectRole;
import org.sevenorganization.int20h2023be.repository.InvitationsRepository;
import org.sevenorganization.int20h2023be.service.InvitationsService;
import org.sevenorganization.int20h2023be.service.ProjectMemberService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DefaultInvitationsService implements InvitationsService {

    private final InvitationsRepository invitationsRepository;
    private final ProjectMemberService projectMemberService;

    @Override
    public void accept(Long id, User user) {
        Invitation invitation = invitationsRepository.findById(id).get();

        projectMemberService.createProjectMember(user, invitation.getProject(), ProjectRole.MEMBER);

        invitationsRepository.deleteById(id);
    }

    @Override
    public void cancel(Long id) {
        invitationsRepository.deleteById(id);
        // trigger email process
    }

    @Override
    public void reject(Long id) {
        invitationsRepository.deleteById(id);
        // trigger email process
    }
}
