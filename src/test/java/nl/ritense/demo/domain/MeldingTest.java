package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeheerobjectTestSamples.*;
import static nl.ritense.demo.domain.CategorieTestSamples.*;
import static nl.ritense.demo.domain.ContainertypeTestSamples.*;
import static nl.ritense.demo.domain.FractieTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.LocatieTestSamples.*;
import static nl.ritense.demo.domain.MedewerkerTestSamples.*;
import static nl.ritense.demo.domain.MeldingTestSamples.*;
import static nl.ritense.demo.domain.SchouwrondeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MeldingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Melding.class);
        Melding melding1 = getMeldingSample1();
        Melding melding2 = new Melding();
        assertThat(melding1).isNotEqualTo(melding2);

        melding2.setId(melding1.getId());
        assertThat(melding1).isEqualTo(melding2);

        melding2 = getMeldingSample2();
        assertThat(melding1).isNotEqualTo(melding2);
    }

    @Test
    void hoofdcategorieCategorieTest() throws Exception {
        Melding melding = getMeldingRandomSampleGenerator();
        Categorie categorieBack = getCategorieRandomSampleGenerator();

        melding.setHoofdcategorieCategorie(categorieBack);
        assertThat(melding.getHoofdcategorieCategorie()).isEqualTo(categorieBack);

        melding.hoofdcategorieCategorie(null);
        assertThat(melding.getHoofdcategorieCategorie()).isNull();
    }

    @Test
    void subcategorieCategorieTest() throws Exception {
        Melding melding = getMeldingRandomSampleGenerator();
        Categorie categorieBack = getCategorieRandomSampleGenerator();

        melding.setSubcategorieCategorie(categorieBack);
        assertThat(melding.getSubcategorieCategorie()).isEqualTo(categorieBack);

        melding.subcategorieCategorie(null);
        assertThat(melding.getSubcategorieCategorie()).isNull();
    }

    @Test
    void betreftContainertypeTest() throws Exception {
        Melding melding = getMeldingRandomSampleGenerator();
        Containertype containertypeBack = getContainertypeRandomSampleGenerator();

        melding.setBetreftContainertype(containertypeBack);
        assertThat(melding.getBetreftContainertype()).isEqualTo(containertypeBack);

        melding.betreftContainertype(null);
        assertThat(melding.getBetreftContainertype()).isNull();
    }

    @Test
    void betreftFractieTest() throws Exception {
        Melding melding = getMeldingRandomSampleGenerator();
        Fractie fractieBack = getFractieRandomSampleGenerator();

        melding.setBetreftFractie(fractieBack);
        assertThat(melding.getBetreftFractie()).isEqualTo(fractieBack);

        melding.betreftFractie(null);
        assertThat(melding.getBetreftFractie()).isNull();
    }

    @Test
    void betreftLocatieTest() throws Exception {
        Melding melding = getMeldingRandomSampleGenerator();
        Locatie locatieBack = getLocatieRandomSampleGenerator();

        melding.setBetreftLocatie(locatieBack);
        assertThat(melding.getBetreftLocatie()).isEqualTo(locatieBack);

        melding.betreftLocatie(null);
        assertThat(melding.getBetreftLocatie()).isNull();
    }

    @Test
    void melderMedewerkerTest() throws Exception {
        Melding melding = getMeldingRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        melding.setMelderMedewerker(medewerkerBack);
        assertThat(melding.getMelderMedewerker()).isEqualTo(medewerkerBack);

        melding.melderMedewerker(null);
        assertThat(melding.getMelderMedewerker()).isNull();
    }

    @Test
    void uitvoerderLeverancierTest() throws Exception {
        Melding melding = getMeldingRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        melding.setUitvoerderLeverancier(leverancierBack);
        assertThat(melding.getUitvoerderLeverancier()).isEqualTo(leverancierBack);

        melding.uitvoerderLeverancier(null);
        assertThat(melding.getUitvoerderLeverancier()).isNull();
    }

    @Test
    void uitvoerderMedewerkerTest() throws Exception {
        Melding melding = getMeldingRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        melding.setUitvoerderMedewerker(medewerkerBack);
        assertThat(melding.getUitvoerderMedewerker()).isEqualTo(medewerkerBack);

        melding.uitvoerderMedewerker(null);
        assertThat(melding.getUitvoerderMedewerker()).isNull();
    }

    @Test
    void betreftBeheerobjectTest() throws Exception {
        Melding melding = getMeldingRandomSampleGenerator();
        Beheerobject beheerobjectBack = getBeheerobjectRandomSampleGenerator();

        melding.addBetreftBeheerobject(beheerobjectBack);
        assertThat(melding.getBetreftBeheerobjects()).containsOnly(beheerobjectBack);

        melding.removeBetreftBeheerobject(beheerobjectBack);
        assertThat(melding.getBetreftBeheerobjects()).doesNotContain(beheerobjectBack);

        melding.betreftBeheerobjects(new HashSet<>(Set.of(beheerobjectBack)));
        assertThat(melding.getBetreftBeheerobjects()).containsOnly(beheerobjectBack);

        melding.setBetreftBeheerobjects(new HashSet<>());
        assertThat(melding.getBetreftBeheerobjects()).doesNotContain(beheerobjectBack);
    }

    @Test
    void heeftSchouwrondeTest() throws Exception {
        Melding melding = getMeldingRandomSampleGenerator();
        Schouwronde schouwrondeBack = getSchouwrondeRandomSampleGenerator();

        melding.setHeeftSchouwronde(schouwrondeBack);
        assertThat(melding.getHeeftSchouwronde()).isEqualTo(schouwrondeBack);

        melding.heeftSchouwronde(null);
        assertThat(melding.getHeeftSchouwronde()).isNull();
    }
}
