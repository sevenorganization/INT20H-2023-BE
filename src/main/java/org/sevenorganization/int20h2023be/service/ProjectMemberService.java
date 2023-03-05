package org.sevenorganization.int20h2023be.service;

import org.sevenorganization.int20h2023be.model.entity.User;
import org.sevenorganization.int20h2023be.model.entity.Project;
import org.sevenorganization.int20h2023be.model.entity.ProjectMember;
import org.sevenorganization.int20h2023be.model.enumeration.ProjectRole;

public interface ProjectMemberService {
    ProjectMember createProjectMember(User user, Project project, ProjectRole projectRole);
    ProjectMember updateProjectMember(ProjectMember projectMember);
    void deleteProjectMember(ProjectMember projectMember);
}
