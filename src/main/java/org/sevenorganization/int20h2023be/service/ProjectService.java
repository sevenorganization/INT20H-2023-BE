package org.sevenorganization.int20h2023be.service;

import org.sevenorganization.int20h2023be.model.dto.ProjectDto;
import org.sevenorganization.int20h2023be.model.entity.Project;
import org.sevenorganization.int20h2023be.model.entity.User;

public interface ProjectService {
    Project createProject(ProjectDto projectDto, User user);

    Project updateProject(ProjectDto projectDto, User user);

    Project getCurrentProject(User user);
}
