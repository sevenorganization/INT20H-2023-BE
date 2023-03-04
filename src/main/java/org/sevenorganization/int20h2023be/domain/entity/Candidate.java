package org.sevenorganization.int20h2023be.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.sevenorganization.int20h2023be.domain.UserRole;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "candidate")
public class Candidate extends User {

    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER)
    private List<ProjectMember> projects;

    @OneToOne(mappedBy = "candidate")
    private Resume resume;

    private Boolean notifyByTechnologyFlag = false;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER)
    private Set<Invitation> invitations;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.EAGER)
    private Set<Application> applications;

    public Candidate(String firstName, String lastName, String email, String password) {
        super(firstName, lastName, email, password, UserRole.CANDIDATE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Candidate candidate = (Candidate) o;
        return getId() != null && Objects.equals(getId(), candidate.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
