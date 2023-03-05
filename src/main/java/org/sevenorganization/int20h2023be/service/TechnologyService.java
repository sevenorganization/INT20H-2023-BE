package org.sevenorganization.int20h2023be.service;

import org.sevenorganization.int20h2023be.model.enumeration.TechnologyName;

import java.util.List;

public interface TechnologyService {
    List<TechnologyName> findAllTechnologyNames();
}
