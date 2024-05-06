package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ContainerTestSamples.*;
import static nl.ritense.demo.domain.FractieTestSamples.*;
import static nl.ritense.demo.domain.MeldingTestSamples.*;
import static nl.ritense.demo.domain.MilieustraatTestSamples.*;
import static nl.ritense.demo.domain.StortingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FractieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fractie.class);
        Fractie fractie1 = getFractieSample1();
        Fractie fractie2 = new Fractie();
        assertThat(fractie1).isNotEqualTo(fractie2);

        fractie2.setId(fractie1.getId());
        assertThat(fractie1).isEqualTo(fractie2);

        fractie2 = getFractieSample2();
        assertThat(fractie1).isNotEqualTo(fractie2);
    }

    @Test
    void geschiktvoorContainerTest() throws Exception {
        Fractie fractie = getFractieRandomSampleGenerator();
        Container containerBack = getContainerRandomSampleGenerator();

        fractie.addGeschiktvoorContainer(containerBack);
        assertThat(fractie.getGeschiktvoorContainers()).containsOnly(containerBack);
        assertThat(containerBack.getGeschiktvoorFractie()).isEqualTo(fractie);

        fractie.removeGeschiktvoorContainer(containerBack);
        assertThat(fractie.getGeschiktvoorContainers()).doesNotContain(containerBack);
        assertThat(containerBack.getGeschiktvoorFractie()).isNull();

        fractie.geschiktvoorContainers(new HashSet<>(Set.of(containerBack)));
        assertThat(fractie.getGeschiktvoorContainers()).containsOnly(containerBack);
        assertThat(containerBack.getGeschiktvoorFractie()).isEqualTo(fractie);

        fractie.setGeschiktvoorContainers(new HashSet<>());
        assertThat(fractie.getGeschiktvoorContainers()).doesNotContain(containerBack);
        assertThat(containerBack.getGeschiktvoorFractie()).isNull();
    }

    @Test
    void betreftMeldingTest() throws Exception {
        Fractie fractie = getFractieRandomSampleGenerator();
        Melding meldingBack = getMeldingRandomSampleGenerator();

        fractie.addBetreftMelding(meldingBack);
        assertThat(fractie.getBetreftMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getBetreftFractie()).isEqualTo(fractie);

        fractie.removeBetreftMelding(meldingBack);
        assertThat(fractie.getBetreftMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getBetreftFractie()).isNull();

        fractie.betreftMeldings(new HashSet<>(Set.of(meldingBack)));
        assertThat(fractie.getBetreftMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getBetreftFractie()).isEqualTo(fractie);

        fractie.setBetreftMeldings(new HashSet<>());
        assertThat(fractie.getBetreftMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getBetreftFractie()).isNull();
    }

    @Test
    void inzamelpuntvanMilieustraatTest() throws Exception {
        Fractie fractie = getFractieRandomSampleGenerator();
        Milieustraat milieustraatBack = getMilieustraatRandomSampleGenerator();

        fractie.addInzamelpuntvanMilieustraat(milieustraatBack);
        assertThat(fractie.getInzamelpuntvanMilieustraats()).containsOnly(milieustraatBack);
        assertThat(milieustraatBack.getInzamelpuntvanFracties()).containsOnly(fractie);

        fractie.removeInzamelpuntvanMilieustraat(milieustraatBack);
        assertThat(fractie.getInzamelpuntvanMilieustraats()).doesNotContain(milieustraatBack);
        assertThat(milieustraatBack.getInzamelpuntvanFracties()).doesNotContain(fractie);

        fractie.inzamelpuntvanMilieustraats(new HashSet<>(Set.of(milieustraatBack)));
        assertThat(fractie.getInzamelpuntvanMilieustraats()).containsOnly(milieustraatBack);
        assertThat(milieustraatBack.getInzamelpuntvanFracties()).containsOnly(fractie);

        fractie.setInzamelpuntvanMilieustraats(new HashSet<>());
        assertThat(fractie.getInzamelpuntvanMilieustraats()).doesNotContain(milieustraatBack);
        assertThat(milieustraatBack.getInzamelpuntvanFracties()).doesNotContain(fractie);
    }

    @Test
    void fractieStortingTest() throws Exception {
        Fractie fractie = getFractieRandomSampleGenerator();
        Storting stortingBack = getStortingRandomSampleGenerator();

        fractie.addFractieStorting(stortingBack);
        assertThat(fractie.getFractieStortings()).containsOnly(stortingBack);
        assertThat(stortingBack.getFractieFracties()).containsOnly(fractie);

        fractie.removeFractieStorting(stortingBack);
        assertThat(fractie.getFractieStortings()).doesNotContain(stortingBack);
        assertThat(stortingBack.getFractieFracties()).doesNotContain(fractie);

        fractie.fractieStortings(new HashSet<>(Set.of(stortingBack)));
        assertThat(fractie.getFractieStortings()).containsOnly(stortingBack);
        assertThat(stortingBack.getFractieFracties()).containsOnly(fractie);

        fractie.setFractieStortings(new HashSet<>());
        assertThat(fractie.getFractieStortings()).doesNotContain(stortingBack);
        assertThat(stortingBack.getFractieFracties()).doesNotContain(fractie);
    }
}
