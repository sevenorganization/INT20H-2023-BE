package org.sevenorganization.int20h2023be.resource;

import lombok.RequiredArgsConstructor;
import org.sevenorganization.int20h2023be.model.entity.Application;
import org.sevenorganization.int20h2023be.model.entity.Invitation;
import org.sevenorganization.int20h2023be.security.service.UserService;
import org.sevenorganization.int20h2023be.security.userdetails.DefaultUserDetails;
import org.sevenorganization.int20h2023be.service.ApplicationService;
import org.sevenorganization.int20h2023be.service.CandidateService;
import org.sevenorganization.int20h2023be.service.InvitationsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
@RestController
public class MyActivitiesResource {

    private final CandidateService candidateService;
    private final UserService userService;
    private final InvitationsService invitationsService;
    private final ApplicationService applicationService;

    @GetMapping("/applications")
    public ResponseEntity<Set<Application>> getCandidateApplications(Principal principal) {
        if (((UsernamePasswordAuthenticationToken) principal).getPrincipal() instanceof DefaultUserDetails defaultUserDetails) {
            var user = userService.findByEmail(defaultUserDetails.getEmail());
            return new ResponseEntity<>(user.getApplications(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptySet(), HttpStatus.OK);
    }

    @GetMapping("/invitations")
    public ResponseEntity<Set<Invitation>> getCandidateInvitations(Principal principal) {
        if (((UsernamePasswordAuthenticationToken) principal).getPrincipal() instanceof DefaultUserDetails defaultUserDetails) {
            var user = userService.findByEmail(defaultUserDetails.getEmail());
            return new ResponseEntity<>(user.getInvitations(), HttpStatus.OK);
        }
        return new ResponseEntity<>(Collections.emptySet(), HttpStatus.OK);
    }

    @PostMapping("/applications/cancel")
    private ResponseEntity<Void> cancelApplication(@RequestParam Long applicationId) {
        applicationService.cancel(applicationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/invitations/accept")
    private ResponseEntity<Void> acceptInvitation(@RequestParam Long invitationId, Principal principal) {
        if (((UsernamePasswordAuthenticationToken) principal).getPrincipal() instanceof DefaultUserDetails defaultUserDetails) {
            var user = userService.findByEmail(defaultUserDetails.getEmail());
            if (candidateService.hasNoActiveProject(user)) {
                invitationsService.accept(invitationId, user);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/invitations/reject")
    private ResponseEntity<Void> rejectInvitation(@RequestParam Long invitationId) {
        invitationsService.reject(invitationId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
