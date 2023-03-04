package org.sevenorganization.int20h2023be.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import org.sevenorganization.int20h2023be.domain.LanguageLevel;
import org.sevenorganization.int20h2023be.domain.LanguageName;

import java.util.Objects;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "language")
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LanguageName languageName;

    @Enumerated(EnumType.STRING)
    private LanguageLevel languageLevel;

    public Language(LanguageName languageName, LanguageLevel languageLevel) {
        this.languageName = languageName;
        this.languageLevel = languageLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Language language = (Language) o;
        return id != null && Objects.equals(id, language.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
