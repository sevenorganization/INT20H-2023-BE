package org.sevenorganization.int20h2023be.model.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.sevenorganization.int20h2023be.model.enumeration.ProjectStatus;

import java.util.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name = "project")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false)
    private String title;

    @Column(length = 1000)
    private String description;

    private String repositoryUrl;

    @Enumerated(EnumType.STRING)
    private ProjectStatus projectStatus = ProjectStatus.WAITING;

    @JsonIgnore
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private List<ProjectMember> projectMembers = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private Set<Invitation> invitations = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER)
    private Set<Application> applications = new HashSet<>();

    public Project(String title) {
        this.title = title;
    }

    public Project(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public Project(String title, String description, String repositoryUrl) {
        this.title = title;
        this.description = description;
        this.repositoryUrl = repositoryUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Project project = (Project) o;
        return id != null && Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
