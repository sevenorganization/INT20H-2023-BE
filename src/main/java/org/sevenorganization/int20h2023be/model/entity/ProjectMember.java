package org.sevenorganization.int20h2023be.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.sevenorganization.int20h2023be.model.enumeration.ProjectRole;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "project_member")
public class ProjectMember {

    @EmbeddedId
    private ProjectMemberKey projectMemberKey;

    @ManyToOne
    @MapsId("user_id")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("project_id")
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectRole projectRole;

    public ProjectMember(User user, Project project, ProjectRole projectRole) {
        this.user = user;
        this.project = project;
        this.projectRole = projectRole;
    }
}
