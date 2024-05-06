package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ParkeerrechtTestSamples.*;
import static nl.ritense.demo.domain.ParkeerscanTestSamples.*;
import static nl.ritense.demo.domain.VoertuigTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VoertuigTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Voertuig.class);
        Voertuig voertuig1 = getVoertuigSample1();
        Voertuig voertuig2 = new Voertuig();
        assertThat(voertuig1).isNotEqualTo(voertuig2);

        voertuig2.setId(voertuig1.getId());
        assertThat(voertuig1).isEqualTo(voertuig2);

        voertuig2 = getVoertuigSample2();
        assertThat(voertuig1).isNotEqualTo(voertuig2);
    }

    @Test
    void betreftParkeerrechtTest() throws Exception {
        Voertuig voertuig = getVoertuigRandomSampleGenerator();
        Parkeerrecht parkeerrechtBack = getParkeerrechtRandomSampleGenerator();

        voertuig.addBetreftParkeerrecht(parkeerrechtBack);
        assertThat(voertuig.getBetreftParkeerrechts()).containsOnly(parkeerrechtBack);
        assertThat(parkeerrechtBack.getBetreftVoertuig()).isEqualTo(voertuig);

        voertuig.removeBetreftParkeerrecht(parkeerrechtBack);
        assertThat(voertuig.getBetreftParkeerrechts()).doesNotContain(parkeerrechtBack);
        assertThat(parkeerrechtBack.getBetreftVoertuig()).isNull();

        voertuig.betreftParkeerrechts(new HashSet<>(Set.of(parkeerrechtBack)));
        assertThat(voertuig.getBetreftParkeerrechts()).containsOnly(parkeerrechtBack);
        assertThat(parkeerrechtBack.getBetreftVoertuig()).isEqualTo(voertuig);

        voertuig.setBetreftParkeerrechts(new HashSet<>());
        assertThat(voertuig.getBetreftParkeerrechts()).doesNotContain(parkeerrechtBack);
        assertThat(parkeerrechtBack.getBetreftVoertuig()).isNull();
    }

    @Test
    void betreftParkeerscanTest() throws Exception {
        Voertuig voertuig = getVoertuigRandomSampleGenerator();
        Parkeerscan parkeerscanBack = getParkeerscanRandomSampleGenerator();

        voertuig.addBetreftParkeerscan(parkeerscanBack);
        assertThat(voertuig.getBetreftParkeerscans()).containsOnly(parkeerscanBack);
        assertThat(parkeerscanBack.getBetreftVoertuig()).isEqualTo(voertuig);

        voertuig.removeBetreftParkeerscan(parkeerscanBack);
        assertThat(voertuig.getBetreftParkeerscans()).doesNotContain(parkeerscanBack);
        assertThat(parkeerscanBack.getBetreftVoertuig()).isNull();

        voertuig.betreftParkeerscans(new HashSet<>(Set.of(parkeerscanBack)));
        assertThat(voertuig.getBetreftParkeerscans()).containsOnly(parkeerscanBack);
        assertThat(parkeerscanBack.getBetreftVoertuig()).isEqualTo(voertuig);

        voertuig.setBetreftParkeerscans(new HashSet<>());
        assertThat(voertuig.getBetreftParkeerscans()).doesNotContain(parkeerscanBack);
        assertThat(parkeerscanBack.getBetreftVoertuig()).isNull();
    }
}
