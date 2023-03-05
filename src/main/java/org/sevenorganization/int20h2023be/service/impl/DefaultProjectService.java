package org.sevenorganization.int20h2023be.service.impl;

import lombok.RequiredArgsConstructor;
import org.sevenorganization.int20h2023be.model.dto.ProjectDto;
import org.sevenorganization.int20h2023be.model.entity.Project;
import org.sevenorganization.int20h2023be.model.entity.User;
import org.sevenorganization.int20h2023be.model.enumeration.ProjectRole;
import org.sevenorganization.int20h2023be.repository.ProjectRepository;
import org.sevenorganization.int20h2023be.service.ProjectMemberService;
import org.sevenorganization.int20h2023be.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DefaultProjectService implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMemberService projectMemberService;

    @Override
    public Project getCurrentProject(User user) {
        return user.getProjects().get(user.getProjects().size() - 1).getProject();
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public List<Project> findByTitleAndDescriptionContaining(String q) {
        List<Project> res = projectRepository.findByTitleContaining(q);
        res.addAll(projectRepository.findByDescriptionContaining(q));
        return res.stream().distinct().toList();
    }

    @Override
    public Project createProject(ProjectDto projectDto, User user) {
        Project project = projectRepository.save(new Project(
                projectDto.title(), projectDto.description(), projectDto.repositoryUrl())
        );

        project.getProjectMembers().add(projectMemberService.createProjectMember(
                user, project, ProjectRole.PROJECT_OWNER
        ));

        return project;
    }

    @Override
    public Project updateProject(ProjectDto projectDto, User user) {
        Project target = getCurrentProject(user);
        target.setTitle(projectDto.title());
        target.setDescription(projectDto.description());
        target.setRepositoryUrl(projectDto.repositoryUrl());
        return projectRepository.save(target);
    }
}
