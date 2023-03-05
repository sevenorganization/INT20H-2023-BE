package org.sevenorganization.int20h2023be.repository;

import org.sevenorganization.int20h2023be.model.entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationsRepository extends JpaRepository<Invitation, Long> {
}
