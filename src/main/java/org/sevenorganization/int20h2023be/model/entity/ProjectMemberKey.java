package org.sevenorganization.int20h2023be.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Hibernate;

import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProjectMemberKey implements java.io.Serializable {

    @Column(name = "user_id")
    public Long user_id;

    @Column(name = "project_id")
    public Long project_id;

}
