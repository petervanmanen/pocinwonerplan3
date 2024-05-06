package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ArcheologiebesluitTestSamples.*;
import static nl.ritense.demo.domain.BoringTestSamples.*;
import static nl.ritense.demo.domain.KostenplaatsTestSamples.*;
import static nl.ritense.demo.domain.LocatieTestSamples.*;
import static nl.ritense.demo.domain.ProjectTestSamples.*;
import static nl.ritense.demo.domain.ProjectactiviteitTestSamples.*;
import static nl.ritense.demo.domain.ProjectlocatieTestSamples.*;
import static nl.ritense.demo.domain.ProjectsoortTestSamples.*;
import static nl.ritense.demo.domain.PutTestSamples.*;
import static nl.ritense.demo.domain.ResultaatTestSamples.*;
import static nl.ritense.demo.domain.TrajectTestSamples.*;
import static nl.ritense.demo.domain.UitstroomredenTestSamples.*;
import static nl.ritense.demo.domain.VindplaatsTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Project.class);
        Project project1 = getProjectSample1();
        Project project2 = new Project();
        assertThat(project1).isNotEqualTo(project2);

        project2.setId(project1.getId());
        assertThat(project1).isEqualTo(project2);

        project2 = getProjectSample2();
        assertThat(project1).isNotEqualTo(project2);
    }

    @Test
    void heeftuitstroomredenUitstroomredenTest() throws Exception {
        Project project = getProjectRandomSampleGenerator();
        Uitstroomreden uitstroomredenBack = getUitstroomredenRandomSampleGenerator();

        project.setHeeftuitstroomredenUitstroomreden(uitstroomredenBack);
        assertThat(project.getHeeftuitstroomredenUitstroomreden()).isEqualTo(uitstroomredenBack);

        project.heeftuitstroomredenUitstroomreden(null);
        assertThat(project.getHeeftuitstroomredenUitstroomreden()).isNull();
    }

    @Test
    void heeftresultaatResultaatTest() throws Exception {
        Project project = getProjectRandomSampleGenerator();
        Resultaat resultaatBack = getResultaatRandomSampleGenerator();

        project.setHeeftresultaatResultaat(resultaatBack);
        assertThat(project.getHeeftresultaatResultaat()).isEqualTo(resultaatBack);

        project.heeftresultaatResultaat(null);
        assertThat(project.getHeeftresultaatResultaat()).isNull();
    }

    @Test
    void heeftArcheologiebesluitTest() throws Exception {
        Project project = getProjectRandomSampleGenerator();
        Archeologiebesluit archeologiebesluitBack = getArcheologiebesluitRandomSampleGenerator();

        project.addHeeftArcheologiebesluit(archeologiebesluitBack);
        assertThat(project.getHeeftArcheologiebesluits()).containsOnly(archeologiebesluitBack);
        assertThat(archeologiebesluitBack.getHeeftProject()).isEqualTo(project);

        project.removeHeeftArcheologiebesluit(archeologiebesluitBack);
        assertThat(project.getHeeftArcheologiebesluits()).doesNotContain(archeologiebesluitBack);
        assertThat(archeologiebesluitBack.getHeeftProject()).isNull();

        project.heeftArcheologiebesluits(new HashSet<>(Set.of(archeologiebesluitBack)));
        assertThat(project.getHeeftArcheologiebesluits()).containsOnly(archeologiebesluitBack);
        assertThat(archeologiebesluitBack.getHeeftProject()).isEqualTo(project);

        project.setHeeftArcheologiebesluits(new HashSet<>());
        assertThat(project.getHeeftArcheologiebesluits()).doesNotContain(archeologiebesluitBack);
        assertThat(archeologiebesluitBack.getHeeftProject()).isNull();
    }

    @Test
    void heeftBoringTest() throws Exception {
        Project project = getProjectRandomSampleGenerator();
        Boring boringBack = getBoringRandomSampleGenerator();

        project.addHeeftBoring(boringBack);
        assertThat(project.getHeeftBorings()).containsOnly(boringBack);
        assertThat(boringBack.getHeeftProject()).isEqualTo(project);

        project.removeHeeftBoring(boringBack);
        assertThat(project.getHeeftBorings()).doesNotContain(boringBack);
        assertThat(boringBack.getHeeftProject()).isNull();

        project.heeftBorings(new HashSet<>(Set.of(boringBack)));
        assertThat(project.getHeeftBorings()).containsOnly(boringBack);
        assertThat(boringBack.getHeeftProject()).isEqualTo(project);

        project.setHeeftBorings(new HashSet<>());
        assertThat(project.getHeeftBorings()).doesNotContain(boringBack);
        assertThat(boringBack.getHeeftProject()).isNull();
    }

    @Test
    void heeftPutTest() throws Exception {
        Project project = getProjectRandomSampleGenerator();
        Put putBack = getPutRandomSampleGenerator();

        project.addHeeftPut(putBack);
        assertThat(project.getHeeftPuts()).containsOnly(putBack);
        assertThat(putBack.getHeeftProject()).isEqualTo(project);

        project.removeHeeftPut(putBack);
        assertThat(project.getHeeftPuts()).doesNotContain(putBack);
        assertThat(putBack.getHeeftProject()).isNull();

        project.heeftPuts(new HashSet<>(Set.of(putBack)));
        assertThat(project.getHeeftPuts()).containsOnly(putBack);
        assertThat(putBack.getHeeftProject()).isEqualTo(project);

        project.setHeeftPuts(new HashSet<>());
        assertThat(project.getHeeftPuts()).doesNotContain(putBack);
        assertThat(putBack.getHeeftProject()).isNull();
    }

    @Test
    void heeftProjectlocatieTest() throws Exception {
        Project project = getProjectRandomSampleGenerator();
        Projectlocatie projectlocatieBack = getProjectlocatieRandomSampleGenerator();

        project.addHeeftProjectlocatie(projectlocatieBack);
        assertThat(project.getHeeftProjectlocaties()).containsOnly(projectlocatieBack);
        assertThat(projectlocatieBack.getHeeftProject()).isEqualTo(project);

        project.removeHeeftProjectlocatie(projectlocatieBack);
        assertThat(project.getHeeftProjectlocaties()).doesNotContain(projectlocatieBack);
        assertThat(projectlocatieBack.getHeeftProject()).isNull();

        project.heeftProjectlocaties(new HashSet<>(Set.of(projectlocatieBack)));
        assertThat(project.getHeeftProjectlocaties()).containsOnly(projectlocatieBack);
        assertThat(projectlocatieBack.getHeeftProject()).isEqualTo(project);

        project.setHeeftProjectlocaties(new HashSet<>());
        assertThat(project.getHeeftProjectlocaties()).doesNotContain(projectlocatieBack);
        assertThat(projectlocatieBack.getHeeftProject()).isNull();
    }

    @Test
    void heeftProjectactiviteitTest() throws Exception {
        Project project = getProjectRandomSampleGenerator();
        Projectactiviteit projectactiviteitBack = getProjectactiviteitRandomSampleGenerator();

        project.addHeeftProjectactiviteit(projectactiviteitBack);
        assertThat(project.getHeeftProjectactiviteits()).containsOnly(projectactiviteitBack);
        assertThat(projectactiviteitBack.getHeeftProject()).isEqualTo(project);

        project.removeHeeftProjectactiviteit(projectactiviteitBack);
        assertThat(project.getHeeftProjectactiviteits()).doesNotContain(projectactiviteitBack);
        assertThat(projectactiviteitBack.getHeeftProject()).isNull();

        project.heeftProjectactiviteits(new HashSet<>(Set.of(projectactiviteitBack)));
        assertThat(project.getHeeftProjectactiviteits()).containsOnly(projectactiviteitBack);
        assertThat(projectactiviteitBack.getHeeftProject()).isEqualTo(project);

        project.setHeeftProjectactiviteits(new HashSet<>());
        assertThat(project.getHeeftProjectactiviteits()).doesNotContain(projectactiviteitBack);
        assertThat(projectactiviteitBack.getHeeftProject()).isNull();
    }

    @Test
    void soortprojectProjectsoortTest() throws Exception {
        Project project = getProjectRandomSampleGenerator();
        Projectsoort projectsoortBack = getProjectsoortRandomSampleGenerator();

        project.setSoortprojectProjectsoort(projectsoortBack);
        assertThat(project.getSoortprojectProjectsoort()).isEqualTo(projectsoortBack);

        project.soortprojectProjectsoort(null);
        assertThat(project.getSoortprojectProjectsoort()).isNull();
    }

    @Test
    void wordtbegrensddoorLocatieTest() throws Exception {
        Project project = getProjectRandomSampleGenerator();
        Locatie locatieBack = getLocatieRandomSampleGenerator();

        project.addWordtbegrensddoorLocatie(locatieBack);
        assertThat(project.getWordtbegrensddoorLocaties()).containsOnly(locatieBack);

        project.removeWordtbegrensddoorLocatie(locatieBack);
        assertThat(project.getWordtbegrensddoorLocaties()).doesNotContain(locatieBack);

        project.wordtbegrensddoorLocaties(new HashSet<>(Set.of(locatieBack)));
        assertThat(project.getWordtbegrensddoorLocaties()).containsOnly(locatieBack);

        project.setWordtbegrensddoorLocaties(new HashSet<>());
        assertThat(project.getWordtbegrensddoorLocaties()).doesNotContain(locatieBack);
    }

    @Test
    void heeftKostenplaatsTest() throws Exception {
        Project project = getProjectRandomSampleGenerator();
        Kostenplaats kostenplaatsBack = getKostenplaatsRandomSampleGenerator();

        project.addHeeftKostenplaats(kostenplaatsBack);
        assertThat(project.getHeeftKostenplaats()).containsOnly(kostenplaatsBack);

        project.removeHeeftKostenplaats(kostenplaatsBack);
        assertThat(project.getHeeftKostenplaats()).doesNotContain(kostenplaatsBack);

        project.heeftKostenplaats(new HashSet<>(Set.of(kostenplaatsBack)));
        assertThat(project.getHeeftKostenplaats()).containsOnly(kostenplaatsBack);

        project.setHeeftKostenplaats(new HashSet<>());
        assertThat(project.getHeeftKostenplaats()).doesNotContain(kostenplaatsBack);
    }

    @Test
    void hoortbijVindplaatsTest() throws Exception {
        Project project = getProjectRandomSampleGenerator();
        Vindplaats vindplaatsBack = getVindplaatsRandomSampleGenerator();

        project.setHoortbijVindplaats(vindplaatsBack);
        assertThat(project.getHoortbijVindplaats()).isEqualTo(vindplaatsBack);
        assertThat(vindplaatsBack.getHoortbijProject()).isEqualTo(project);

        project.hoortbijVindplaats(null);
        assertThat(project.getHoortbijVindplaats()).isNull();
        assertThat(vindplaatsBack.getHoortbijProject()).isNull();
    }

    @Test
    void heeftprojectTrajectTest() throws Exception {
        Project project = getProjectRandomSampleGenerator();
        Traject trajectBack = getTrajectRandomSampleGenerator();

        project.setHeeftprojectTraject(trajectBack);
        assertThat(project.getHeeftprojectTraject()).isEqualTo(trajectBack);

        project.heeftprojectTraject(null);
        assertThat(project.getHeeftprojectTraject()).isNull();
    }

    @Test
    void betreftZaakTest() throws Exception {
        Project project = getProjectRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        project.addBetreftZaak(zaakBack);
        assertThat(project.getBetreftZaaks()).containsOnly(zaakBack);
        assertThat(zaakBack.getBetreftProject()).isEqualTo(project);

        project.removeBetreftZaak(zaakBack);
        assertThat(project.getBetreftZaaks()).doesNotContain(zaakBack);
        assertThat(zaakBack.getBetreftProject()).isNull();

        project.betreftZaaks(new HashSet<>(Set.of(zaakBack)));
        assertThat(project.getBetreftZaaks()).containsOnly(zaakBack);
        assertThat(zaakBack.getBetreftProject()).isEqualTo(project);

        project.setBetreftZaaks(new HashSet<>());
        assertThat(project.getBetreftZaaks()).doesNotContain(zaakBack);
        assertThat(zaakBack.getBetreftProject()).isNull();
    }
}
