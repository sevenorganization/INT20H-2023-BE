package org.sevenorganization.int20h2023be.model.dto;

import org.sevenorganization.int20h2023be.model.enumeration.TechnologyName;
import org.sevenorganization.int20h2023be.model.enumeration.TechnologyProficiency;

public record TechnologyDto (
        TechnologyName technologyName,
        TechnologyProficiency technologyProficiency
) {
}
