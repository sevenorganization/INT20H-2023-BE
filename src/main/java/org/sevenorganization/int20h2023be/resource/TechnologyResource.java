package org.sevenorganization.int20h2023be.resource;

import lombok.RequiredArgsConstructor;
import org.sevenorganization.int20h2023be.model.enumeration.TechnologyName;
import org.sevenorganization.int20h2023be.service.TechnologyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/technologies")
public class TechnologyResource {

    private final TechnologyService technologyService;

    @GetMapping
    public ResponseEntity<List<TechnologyName>> findAllLanguageNames() {
        return ResponseEntity.ok(technologyService.findAllTechnologyNames());
    }
}
