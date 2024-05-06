package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ActiviteitTestSamples.*;
import static nl.ritense.demo.domain.ContainerTestSamples.*;
import static nl.ritense.demo.domain.GebiedsaanwijzingTestSamples.*;
import static nl.ritense.demo.domain.LocatieTestSamples.*;
import static nl.ritense.demo.domain.MeldingTestSamples.*;
import static nl.ritense.demo.domain.NormwaardeTestSamples.*;
import static nl.ritense.demo.domain.OphaalmomentTestSamples.*;
import static nl.ritense.demo.domain.ProjectTestSamples.*;
import static nl.ritense.demo.domain.ProjectlocatieTestSamples.*;
import static nl.ritense.demo.domain.PutTestSamples.*;
import static nl.ritense.demo.domain.RegeltekstTestSamples.*;
import static nl.ritense.demo.domain.VerzoekTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LocatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Locatie.class);
        Locatie locatie1 = getLocatieSample1();
        Locatie locatie2 = new Locatie();
        assertThat(locatie1).isNotEqualTo(locatie2);

        locatie2.setId(locatie1.getId());
        assertThat(locatie1).isEqualTo(locatie2);

        locatie2 = getLocatieSample2();
        assertThat(locatie1).isNotEqualTo(locatie2);
    }

    @Test
    void heeftContainerTest() throws Exception {
        Locatie locatie = getLocatieRandomSampleGenerator();
        Container containerBack = getContainerRandomSampleGenerator();

        locatie.addHeeftContainer(containerBack);
        assertThat(locatie.getHeeftContainers()).containsOnly(containerBack);
        assertThat(containerBack.getHeeftLocatie()).isEqualTo(locatie);

        locatie.removeHeeftContainer(containerBack);
        assertThat(locatie.getHeeftContainers()).doesNotContain(containerBack);
        assertThat(containerBack.getHeeftLocatie()).isNull();

        locatie.heeftContainers(new HashSet<>(Set.of(containerBack)));
        assertThat(locatie.getHeeftContainers()).containsOnly(containerBack);
        assertThat(containerBack.getHeeftLocatie()).isEqualTo(locatie);

        locatie.setHeeftContainers(new HashSet<>());
        assertThat(locatie.getHeeftContainers()).doesNotContain(containerBack);
        assertThat(containerBack.getHeeftLocatie()).isNull();
    }

    @Test
    void betreftMeldingTest() throws Exception {
        Locatie locatie = getLocatieRandomSampleGenerator();
        Melding meldingBack = getMeldingRandomSampleGenerator();

        locatie.addBetreftMelding(meldingBack);
        assertThat(locatie.getBetreftMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getBetreftLocatie()).isEqualTo(locatie);

        locatie.removeBetreftMelding(meldingBack);
        assertThat(locatie.getBetreftMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getBetreftLocatie()).isNull();

        locatie.betreftMeldings(new HashSet<>(Set.of(meldingBack)));
        assertThat(locatie.getBetreftMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getBetreftLocatie()).isEqualTo(locatie);

        locatie.setBetreftMeldings(new HashSet<>());
        assertThat(locatie.getBetreftMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getBetreftLocatie()).isNull();
    }

    @Test
    void gestoptopOphaalmomentTest() throws Exception {
        Locatie locatie = getLocatieRandomSampleGenerator();
        Ophaalmoment ophaalmomentBack = getOphaalmomentRandomSampleGenerator();

        locatie.addGestoptopOphaalmoment(ophaalmomentBack);
        assertThat(locatie.getGestoptopOphaalmoments()).containsOnly(ophaalmomentBack);
        assertThat(ophaalmomentBack.getGestoptopLocatie()).isEqualTo(locatie);

        locatie.removeGestoptopOphaalmoment(ophaalmomentBack);
        assertThat(locatie.getGestoptopOphaalmoments()).doesNotContain(ophaalmomentBack);
        assertThat(ophaalmomentBack.getGestoptopLocatie()).isNull();

        locatie.gestoptopOphaalmoments(new HashSet<>(Set.of(ophaalmomentBack)));
        assertThat(locatie.getGestoptopOphaalmoments()).containsOnly(ophaalmomentBack);
        assertThat(ophaalmomentBack.getGestoptopLocatie()).isEqualTo(locatie);

        locatie.setGestoptopOphaalmoments(new HashSet<>());
        assertThat(locatie.getGestoptopOphaalmoments()).doesNotContain(ophaalmomentBack);
        assertThat(ophaalmomentBack.getGestoptopLocatie()).isNull();
    }

    @Test
    void betreftProjectlocatieTest() throws Exception {
        Locatie locatie = getLocatieRandomSampleGenerator();
        Projectlocatie projectlocatieBack = getProjectlocatieRandomSampleGenerator();

        locatie.addBetreftProjectlocatie(projectlocatieBack);
        assertThat(locatie.getBetreftProjectlocaties()).containsOnly(projectlocatieBack);
        assertThat(projectlocatieBack.getBetreftLocatie()).isEqualTo(locatie);

        locatie.removeBetreftProjectlocatie(projectlocatieBack);
        assertThat(locatie.getBetreftProjectlocaties()).doesNotContain(projectlocatieBack);
        assertThat(projectlocatieBack.getBetreftLocatie()).isNull();

        locatie.betreftProjectlocaties(new HashSet<>(Set.of(projectlocatieBack)));
        assertThat(locatie.getBetreftProjectlocaties()).containsOnly(projectlocatieBack);
        assertThat(projectlocatieBack.getBetreftLocatie()).isEqualTo(locatie);

        locatie.setBetreftProjectlocaties(new HashSet<>());
        assertThat(locatie.getBetreftProjectlocaties()).doesNotContain(projectlocatieBack);
        assertThat(projectlocatieBack.getBetreftLocatie()).isNull();
    }

    @Test
    void heeftlocatiePutTest() throws Exception {
        Locatie locatie = getLocatieRandomSampleGenerator();
        Put putBack = getPutRandomSampleGenerator();

        locatie.addHeeftlocatiePut(putBack);
        assertThat(locatie.getHeeftlocatiePuts()).containsOnly(putBack);
        assertThat(putBack.getHeeftlocatieLocaties()).containsOnly(locatie);

        locatie.removeHeeftlocatiePut(putBack);
        assertThat(locatie.getHeeftlocatiePuts()).doesNotContain(putBack);
        assertThat(putBack.getHeeftlocatieLocaties()).doesNotContain(locatie);

        locatie.heeftlocatiePuts(new HashSet<>(Set.of(putBack)));
        assertThat(locatie.getHeeftlocatiePuts()).containsOnly(putBack);
        assertThat(putBack.getHeeftlocatieLocaties()).containsOnly(locatie);

        locatie.setHeeftlocatiePuts(new HashSet<>());
        assertThat(locatie.getHeeftlocatiePuts()).doesNotContain(putBack);
        assertThat(putBack.getHeeftlocatieLocaties()).doesNotContain(locatie);
    }

    @Test
    void wordtbegrensddoorProjectTest() throws Exception {
        Locatie locatie = getLocatieRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        locatie.addWordtbegrensddoorProject(projectBack);
        assertThat(locatie.getWordtbegrensddoorProjects()).containsOnly(projectBack);
        assertThat(projectBack.getWordtbegrensddoorLocaties()).containsOnly(locatie);

        locatie.removeWordtbegrensddoorProject(projectBack);
        assertThat(locatie.getWordtbegrensddoorProjects()).doesNotContain(projectBack);
        assertThat(projectBack.getWordtbegrensddoorLocaties()).doesNotContain(locatie);

        locatie.wordtbegrensddoorProjects(new HashSet<>(Set.of(projectBack)));
        assertThat(locatie.getWordtbegrensddoorProjects()).containsOnly(projectBack);
        assertThat(projectBack.getWordtbegrensddoorLocaties()).containsOnly(locatie);

        locatie.setWordtbegrensddoorProjects(new HashSet<>());
        assertThat(locatie.getWordtbegrensddoorProjects()).doesNotContain(projectBack);
        assertThat(projectBack.getWordtbegrensddoorLocaties()).doesNotContain(locatie);
    }

    @Test
    void betreftVerzoekTest() throws Exception {
        Locatie locatie = getLocatieRandomSampleGenerator();
        Verzoek verzoekBack = getVerzoekRandomSampleGenerator();

        locatie.addBetreftVerzoek(verzoekBack);
        assertThat(locatie.getBetreftVerzoeks()).containsOnly(verzoekBack);
        assertThat(verzoekBack.getBetreftLocaties()).containsOnly(locatie);

        locatie.removeBetreftVerzoek(verzoekBack);
        assertThat(locatie.getBetreftVerzoeks()).doesNotContain(verzoekBack);
        assertThat(verzoekBack.getBetreftLocaties()).doesNotContain(locatie);

        locatie.betreftVerzoeks(new HashSet<>(Set.of(verzoekBack)));
        assertThat(locatie.getBetreftVerzoeks()).containsOnly(verzoekBack);
        assertThat(verzoekBack.getBetreftLocaties()).containsOnly(locatie);

        locatie.setBetreftVerzoeks(new HashSet<>());
        assertThat(locatie.getBetreftVerzoeks()).doesNotContain(verzoekBack);
        assertThat(verzoekBack.getBetreftLocaties()).doesNotContain(locatie);
    }

    @Test
    void werkingsgebiedRegeltekstTest() throws Exception {
        Locatie locatie = getLocatieRandomSampleGenerator();
        Regeltekst regeltekstBack = getRegeltekstRandomSampleGenerator();

        locatie.addWerkingsgebiedRegeltekst(regeltekstBack);
        assertThat(locatie.getWerkingsgebiedRegelteksts()).containsOnly(regeltekstBack);
        assertThat(regeltekstBack.getWerkingsgebiedLocaties()).containsOnly(locatie);

        locatie.removeWerkingsgebiedRegeltekst(regeltekstBack);
        assertThat(locatie.getWerkingsgebiedRegelteksts()).doesNotContain(regeltekstBack);
        assertThat(regeltekstBack.getWerkingsgebiedLocaties()).doesNotContain(locatie);

        locatie.werkingsgebiedRegelteksts(new HashSet<>(Set.of(regeltekstBack)));
        assertThat(locatie.getWerkingsgebiedRegelteksts()).containsOnly(regeltekstBack);
        assertThat(regeltekstBack.getWerkingsgebiedLocaties()).containsOnly(locatie);

        locatie.setWerkingsgebiedRegelteksts(new HashSet<>());
        assertThat(locatie.getWerkingsgebiedRegelteksts()).doesNotContain(regeltekstBack);
        assertThat(regeltekstBack.getWerkingsgebiedLocaties()).doesNotContain(locatie);
    }

    @Test
    void isverbondenmetActiviteitTest() throws Exception {
        Locatie locatie = getLocatieRandomSampleGenerator();
        Activiteit activiteitBack = getActiviteitRandomSampleGenerator();

        locatie.addIsverbondenmetActiviteit(activiteitBack);
        assertThat(locatie.getIsverbondenmetActiviteits()).containsOnly(activiteitBack);
        assertThat(activiteitBack.getIsverbondenmetLocaties()).containsOnly(locatie);

        locatie.removeIsverbondenmetActiviteit(activiteitBack);
        assertThat(locatie.getIsverbondenmetActiviteits()).doesNotContain(activiteitBack);
        assertThat(activiteitBack.getIsverbondenmetLocaties()).doesNotContain(locatie);

        locatie.isverbondenmetActiviteits(new HashSet<>(Set.of(activiteitBack)));
        assertThat(locatie.getIsverbondenmetActiviteits()).containsOnly(activiteitBack);
        assertThat(activiteitBack.getIsverbondenmetLocaties()).containsOnly(locatie);

        locatie.setIsverbondenmetActiviteits(new HashSet<>());
        assertThat(locatie.getIsverbondenmetActiviteits()).doesNotContain(activiteitBack);
        assertThat(activiteitBack.getIsverbondenmetLocaties()).doesNotContain(locatie);
    }

    @Test
    void verwijstnaarGebiedsaanwijzingTest() throws Exception {
        Locatie locatie = getLocatieRandomSampleGenerator();
        Gebiedsaanwijzing gebiedsaanwijzingBack = getGebiedsaanwijzingRandomSampleGenerator();

        locatie.addVerwijstnaarGebiedsaanwijzing(gebiedsaanwijzingBack);
        assertThat(locatie.getVerwijstnaarGebiedsaanwijzings()).containsOnly(gebiedsaanwijzingBack);
        assertThat(gebiedsaanwijzingBack.getVerwijstnaarLocaties()).containsOnly(locatie);

        locatie.removeVerwijstnaarGebiedsaanwijzing(gebiedsaanwijzingBack);
        assertThat(locatie.getVerwijstnaarGebiedsaanwijzings()).doesNotContain(gebiedsaanwijzingBack);
        assertThat(gebiedsaanwijzingBack.getVerwijstnaarLocaties()).doesNotContain(locatie);

        locatie.verwijstnaarGebiedsaanwijzings(new HashSet<>(Set.of(gebiedsaanwijzingBack)));
        assertThat(locatie.getVerwijstnaarGebiedsaanwijzings()).containsOnly(gebiedsaanwijzingBack);
        assertThat(gebiedsaanwijzingBack.getVerwijstnaarLocaties()).containsOnly(locatie);

        locatie.setVerwijstnaarGebiedsaanwijzings(new HashSet<>());
        assertThat(locatie.getVerwijstnaarGebiedsaanwijzings()).doesNotContain(gebiedsaanwijzingBack);
        assertThat(gebiedsaanwijzingBack.getVerwijstnaarLocaties()).doesNotContain(locatie);
    }

    @Test
    void geldtvoorNormwaardeTest() throws Exception {
        Locatie locatie = getLocatieRandomSampleGenerator();
        Normwaarde normwaardeBack = getNormwaardeRandomSampleGenerator();

        locatie.addGeldtvoorNormwaarde(normwaardeBack);
        assertThat(locatie.getGeldtvoorNormwaardes()).containsOnly(normwaardeBack);
        assertThat(normwaardeBack.getGeldtvoorLocaties()).containsOnly(locatie);

        locatie.removeGeldtvoorNormwaarde(normwaardeBack);
        assertThat(locatie.getGeldtvoorNormwaardes()).doesNotContain(normwaardeBack);
        assertThat(normwaardeBack.getGeldtvoorLocaties()).doesNotContain(locatie);

        locatie.geldtvoorNormwaardes(new HashSet<>(Set.of(normwaardeBack)));
        assertThat(locatie.getGeldtvoorNormwaardes()).containsOnly(normwaardeBack);
        assertThat(normwaardeBack.getGeldtvoorLocaties()).containsOnly(locatie);

        locatie.setGeldtvoorNormwaardes(new HashSet<>());
        assertThat(locatie.getGeldtvoorNormwaardes()).doesNotContain(normwaardeBack);
        assertThat(normwaardeBack.getGeldtvoorLocaties()).doesNotContain(locatie);
    }
}
