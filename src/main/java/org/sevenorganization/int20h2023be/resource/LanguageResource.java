package org.sevenorganization.int20h2023be.resource;

import lombok.RequiredArgsConstructor;
import org.sevenorganization.int20h2023be.model.enumeration.LanguageName;
import org.sevenorganization.int20h2023be.service.LanguageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/languages")
public class LanguageResource {

    private final LanguageService languageService;

    @GetMapping
    public ResponseEntity<List<LanguageName>> findAllLanguageNames() {
        return ResponseEntity.ok(languageService.findAllLanguageNames());
    }
}
