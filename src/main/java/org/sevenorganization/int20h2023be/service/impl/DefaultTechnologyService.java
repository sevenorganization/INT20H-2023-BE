package org.sevenorganization.int20h2023be.service.impl;

import org.sevenorganization.int20h2023be.model.enumeration.TechnologyName;
import org.sevenorganization.int20h2023be.service.TechnologyService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class DefaultTechnologyService implements TechnologyService {

    @Override
    public List<TechnologyName> findAllTechnologyNames() {
        return Arrays.stream(TechnologyName.values()).toList();
    }
}
