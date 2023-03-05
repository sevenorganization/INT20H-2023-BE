package org.sevenorganization.int20h2023be.resource;

import lombok.RequiredArgsConstructor;
import org.sevenorganization.int20h2023be.model.dto.ResumeDto;
import org.sevenorganization.int20h2023be.model.entity.Resume;
import org.sevenorganization.int20h2023be.security.service.UserService;
import org.sevenorganization.int20h2023be.security.userdetails.DefaultUserDetails;
import org.sevenorganization.int20h2023be.service.CandidateService;
import org.sevenorganization.int20h2023be.service.ResumeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
public class MyResumeResource {

    private final UserService userService;
    private final ResumeService resumeService;

    @GetMapping("/resume")
    private ResponseEntity<Resume> getResume(Principal principal) {
        if (((UsernamePasswordAuthenticationToken) principal).getPrincipal() instanceof DefaultUserDetails defaultUserDetails) {
            var user = userService.findByEmail(defaultUserDetails.getEmail());
            return new ResponseEntity<>(user.getResume(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/updateResume")
    private ResponseEntity<Resume> updateResume(@RequestBody ResumeDto resumeDto, Principal principal) {
        if (((UsernamePasswordAuthenticationToken) principal).getPrincipal() instanceof DefaultUserDetails defaultUserDetails) {
            var user = userService.findByEmail(defaultUserDetails.getEmail());
            resumeService.updateResume(resumeDto, user);
            return new ResponseEntity<>(user.getResume(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
