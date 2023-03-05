package org.sevenorganization.int20h2023be.resource;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.sevenorganization.int20h2023be.model.dto.ProjectDto;
import org.sevenorganization.int20h2023be.model.entity.Project;
import org.sevenorganization.int20h2023be.security.userdetails.DefaultUserDetails;
import org.sevenorganization.int20h2023be.service.CandidateService;
import org.sevenorganization.int20h2023be.service.ProjectService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectResource {

    private final ProjectService projectService;
    private final CandidateService candidateService;

    @PostMapping("/createProject")
    public ResponseEntity<Project> createProject(@Valid @RequestBody ProjectDto projectDto, Principal principal) {
        if (((UsernamePasswordAuthenticationToken) principal).getPrincipal() instanceof DefaultUserDetails defaultUserDetails) {
            var user = candidateService.findCandidateByEmail(defaultUserDetails.getEmail());
            return new ResponseEntity<>(projectService.createProject(projectDto, user), HttpStatus.CREATED);
        }
        throw new RuntimeException("UNAUTHORIZED");
    }

//    @PutMapping("/updateProject")
//    public ResponseEntity<Project> updateProject(@Valid @RequestBody ProjectDto projectDto, Principal principal) {
//        if (((UsernamePasswordAuthenticationToken) principal).getPrincipal() instanceof DefaultUserDetails defaultUserDetails) {
//            var user = candidateService.findCandidateByEmail(defaultUserDetails.getEmail());
//            return new ResponseEntity<>(projectService.updateProject(projectDto, user), HttpStatus.OK);
//        }
//        throw new RuntimeException("UNAUTHORIZED");
//    }

    @GetMapping("/projects")
    public ResponseEntity<List<Project>> getProjects(@RequestParam(name = "q", required = false, defaultValue = "") String q) {
        if (q.isBlank()) return ResponseEntity.ok(projectService.findAll());
        return ResponseEntity.ok(projectService.findByTitleAndDescriptionContaining(q));
    }
}
