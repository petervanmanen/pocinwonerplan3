package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MedewerkerTestSamples.*;
import static nl.ritense.demo.domain.NaheffingTestSamples.*;
import static nl.ritense.demo.domain.ParkeerscanTestSamples.*;
import static nl.ritense.demo.domain.ParkeervlakTestSamples.*;
import static nl.ritense.demo.domain.VoertuigTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParkeerscanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parkeerscan.class);
        Parkeerscan parkeerscan1 = getParkeerscanSample1();
        Parkeerscan parkeerscan2 = new Parkeerscan();
        assertThat(parkeerscan1).isNotEqualTo(parkeerscan2);

        parkeerscan2.setId(parkeerscan1.getId());
        assertThat(parkeerscan1).isEqualTo(parkeerscan2);

        parkeerscan2 = getParkeerscanSample2();
        assertThat(parkeerscan1).isNotEqualTo(parkeerscan2);
    }

    @Test
    void komtvoortuitNaheffingTest() throws Exception {
        Parkeerscan parkeerscan = getParkeerscanRandomSampleGenerator();
        Naheffing naheffingBack = getNaheffingRandomSampleGenerator();

        parkeerscan.setKomtvoortuitNaheffing(naheffingBack);
        assertThat(parkeerscan.getKomtvoortuitNaheffing()).isEqualTo(naheffingBack);

        parkeerscan.komtvoortuitNaheffing(null);
        assertThat(parkeerscan.getKomtvoortuitNaheffing()).isNull();
    }

    @Test
    void uitgevoerddoorMedewerkerTest() throws Exception {
        Parkeerscan parkeerscan = getParkeerscanRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        parkeerscan.setUitgevoerddoorMedewerker(medewerkerBack);
        assertThat(parkeerscan.getUitgevoerddoorMedewerker()).isEqualTo(medewerkerBack);

        parkeerscan.uitgevoerddoorMedewerker(null);
        assertThat(parkeerscan.getUitgevoerddoorMedewerker()).isNull();
    }

    @Test
    void betreftVoertuigTest() throws Exception {
        Parkeerscan parkeerscan = getParkeerscanRandomSampleGenerator();
        Voertuig voertuigBack = getVoertuigRandomSampleGenerator();

        parkeerscan.setBetreftVoertuig(voertuigBack);
        assertThat(parkeerscan.getBetreftVoertuig()).isEqualTo(voertuigBack);

        parkeerscan.betreftVoertuig(null);
        assertThat(parkeerscan.getBetreftVoertuig()).isNull();
    }

    @Test
    void betreftParkeervlakTest() throws Exception {
        Parkeerscan parkeerscan = getParkeerscanRandomSampleGenerator();
        Parkeervlak parkeervlakBack = getParkeervlakRandomSampleGenerator();

        parkeerscan.setBetreftParkeervlak(parkeervlakBack);
        assertThat(parkeerscan.getBetreftParkeervlak()).isEqualTo(parkeervlakBack);

        parkeerscan.betreftParkeervlak(null);
        assertThat(parkeerscan.getBetreftParkeervlak()).isNull();
    }
}
