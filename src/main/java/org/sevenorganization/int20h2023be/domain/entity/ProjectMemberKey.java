package org.sevenorganization.int20h2023be.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProjectMemberKey implements java.io.Serializable {

    @Column(name = "candidate_id")
    private Long candidateId;

    @Column(name = "project_id")
    private Long projectId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProjectMemberKey that = (ProjectMemberKey) o;
        return candidateId != null && Objects.equals(candidateId, that.candidateId)
                && projectId != null && Objects.equals(projectId, that.projectId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(candidateId, projectId);
    }
}
