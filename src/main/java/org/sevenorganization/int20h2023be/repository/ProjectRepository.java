package org.sevenorganization.int20h2023be.repository;

import org.sevenorganization.int20h2023be.model.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByTitleContaining(String title);

    List<Project> findByDescriptionContaining(String title);
}
