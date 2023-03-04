package org.sevenorganization.int20h2023be.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.sevenorganization.int20h2023be.model.enumeration.Workload;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resume")
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "candidate_id", referencedColumnName = "id")
    private Candidate candidate;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "resume_technologie",
            joinColumns = @JoinColumn(name = "resume_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "technology_id", referencedColumnName = "id"))
    private Set<Technology> technologyStack = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "resume_language",
            joinColumns = @JoinColumn(name = "resume_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "language_id", referencedColumnName = "id"))
    private Set<Language> languages = new HashSet<>();

    private String portfolioUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Workload workload;

    public Resume(Candidate candidate, Set<Technology> technologyStack, Set<Language> languages, String portfolioUrl, Workload workload) {
        this.candidate = candidate;
        this.technologyStack = technologyStack;
        this.languages = languages;
        this.portfolioUrl = portfolioUrl;
        this.workload = workload;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Resume resume = (Resume) o;
        return id != null && Objects.equals(id, resume.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
