package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ParkeerscanTestSamples.*;
import static nl.ritense.demo.domain.ParkeervlakTestSamples.*;
import static nl.ritense.demo.domain.StraatsectieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParkeervlakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parkeervlak.class);
        Parkeervlak parkeervlak1 = getParkeervlakSample1();
        Parkeervlak parkeervlak2 = new Parkeervlak();
        assertThat(parkeervlak1).isNotEqualTo(parkeervlak2);

        parkeervlak2.setId(parkeervlak1.getId());
        assertThat(parkeervlak1).isEqualTo(parkeervlak2);

        parkeervlak2 = getParkeervlakSample2();
        assertThat(parkeervlak1).isNotEqualTo(parkeervlak2);
    }

    @Test
    void bevatStraatsectieTest() throws Exception {
        Parkeervlak parkeervlak = getParkeervlakRandomSampleGenerator();
        Straatsectie straatsectieBack = getStraatsectieRandomSampleGenerator();

        parkeervlak.setBevatStraatsectie(straatsectieBack);
        assertThat(parkeervlak.getBevatStraatsectie()).isEqualTo(straatsectieBack);

        parkeervlak.bevatStraatsectie(null);
        assertThat(parkeervlak.getBevatStraatsectie()).isNull();
    }

    @Test
    void betreftParkeerscanTest() throws Exception {
        Parkeervlak parkeervlak = getParkeervlakRandomSampleGenerator();
        Parkeerscan parkeerscanBack = getParkeerscanRandomSampleGenerator();

        parkeervlak.addBetreftParkeerscan(parkeerscanBack);
        assertThat(parkeervlak.getBetreftParkeerscans()).containsOnly(parkeerscanBack);
        assertThat(parkeerscanBack.getBetreftParkeervlak()).isEqualTo(parkeervlak);

        parkeervlak.removeBetreftParkeerscan(parkeerscanBack);
        assertThat(parkeervlak.getBetreftParkeerscans()).doesNotContain(parkeerscanBack);
        assertThat(parkeerscanBack.getBetreftParkeervlak()).isNull();

        parkeervlak.betreftParkeerscans(new HashSet<>(Set.of(parkeerscanBack)));
        assertThat(parkeervlak.getBetreftParkeerscans()).containsOnly(parkeerscanBack);
        assertThat(parkeerscanBack.getBetreftParkeervlak()).isEqualTo(parkeervlak);

        parkeervlak.setBetreftParkeerscans(new HashSet<>());
        assertThat(parkeervlak.getBetreftParkeerscans()).doesNotContain(parkeerscanBack);
        assertThat(parkeerscanBack.getBetreftParkeervlak()).isNull();
    }
}
