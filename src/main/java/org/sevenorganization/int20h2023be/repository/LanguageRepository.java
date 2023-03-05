package org.sevenorganization.int20h2023be.repository;

import org.sevenorganization.int20h2023be.model.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends JpaRepository<Language, Long> {
}
