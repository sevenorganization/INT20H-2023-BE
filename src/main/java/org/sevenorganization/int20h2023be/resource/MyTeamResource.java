package org.sevenorganization.int20h2023be.resource;

import lombok.RequiredArgsConstructor;
import org.sevenorganization.int20h2023be.model.entity.Project;
import org.sevenorganization.int20h2023be.model.entity.ProjectMember;
import org.sevenorganization.int20h2023be.model.enumeration.ProjectRole;
import org.sevenorganization.int20h2023be.security.service.UserService;
import org.sevenorganization.int20h2023be.security.userdetails.DefaultUserDetails;
import org.sevenorganization.int20h2023be.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class MyTeamResource {

    private final CandidateService candidateService;
    private final UserService userService;
    private final ProjectMemberService projectMemberService;
    private final ApplicationService applicationService;
    private final InvitationsService invitationsService;

    @GetMapping("/project/members")
    public ResponseEntity<List<ProjectMember>> getProjectMembers(Principal principal) {
        if (((UsernamePasswordAuthenticationToken) principal).getPrincipal() instanceof DefaultUserDetails defaultUserDetails) {
            var user = userService.findByEmail(defaultUserDetails.getEmail());
            ProjectMember projectMember = candidateService.findActiveProjectMember(user);
            // possibly will be an error while mapping list of project members to json
            return new ResponseEntity<>(projectMember.getProject().getProjectMembers(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptyList(), HttpStatus.OK);
    }

    @PostMapping("project/changeRole")
    private ResponseEntity<Void> changeRole(@RequestParam String projectRole, Principal principal) {
        if (((UsernamePasswordAuthenticationToken) principal).getPrincipal() instanceof DefaultUserDetails defaultUserDetails) {
            var user = userService.findByEmail(defaultUserDetails.getEmail());
            ProjectMember projectMember = candidateService.findActiveProjectMember(user);
            if (ProjectRole.PROJECT_MANAGER.toString().equals(projectMember) || ProjectRole.MEMBER.toString().equals(projectRole)) {
                projectMember.setProjectRole(ProjectRole.valueOf(projectRole));
                projectMemberService.updateProjectMember(projectMember);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("project/removeMember")
    private ResponseEntity<Void> removeMember(@RequestParam String memberMail) {
        var user = userService.findByEmail(memberMail);
        ProjectMember projectMember = candidateService.findActiveProjectMember(user);
        projectMemberService.deleteProjectMember(projectMember);

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/invitations/cancel")
    private ResponseEntity<Void> cancelInvitation(@RequestParam Long invitationId) {
        invitationsService.cancel(invitationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/applications/accept")
    private ResponseEntity<Void> acceptApplication(@RequestParam Long applicationId, Principal principal) {
        if (((UsernamePasswordAuthenticationToken) principal).getPrincipal() instanceof DefaultUserDetails defaultUserDetails) {
            var user = userService.findByEmail(defaultUserDetails.getEmail());
            if (candidateService.hasNoActiveProject(user)) {
                applicationService.accept(applicationId, user);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/applications/reject")
    private ResponseEntity<Void> rejectApplication(@RequestParam Long applicationId) {
        applicationService.reject(applicationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
