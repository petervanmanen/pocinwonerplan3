package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KastTestSamples.*;
import static nl.ritense.demo.domain.PlankTestSamples.*;
import static nl.ritense.demo.domain.StellingTestSamples.*;
import static nl.ritense.demo.domain.VindplaatsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kast.class);
        Kast kast1 = getKastSample1();
        Kast kast2 = new Kast();
        assertThat(kast1).isNotEqualTo(kast2);

        kast2.setId(kast1.getId());
        assertThat(kast1).isEqualTo(kast2);

        kast2 = getKastSample2();
        assertThat(kast1).isNotEqualTo(kast2);
    }

    @Test
    void heeftPlankTest() throws Exception {
        Kast kast = getKastRandomSampleGenerator();
        Plank plankBack = getPlankRandomSampleGenerator();

        kast.addHeeftPlank(plankBack);
        assertThat(kast.getHeeftPlanks()).containsOnly(plankBack);
        assertThat(plankBack.getHeeftKast()).isEqualTo(kast);

        kast.removeHeeftPlank(plankBack);
        assertThat(kast.getHeeftPlanks()).doesNotContain(plankBack);
        assertThat(plankBack.getHeeftKast()).isNull();

        kast.heeftPlanks(new HashSet<>(Set.of(plankBack)));
        assertThat(kast.getHeeftPlanks()).containsOnly(plankBack);
        assertThat(plankBack.getHeeftKast()).isEqualTo(kast);

        kast.setHeeftPlanks(new HashSet<>());
        assertThat(kast.getHeeftPlanks()).doesNotContain(plankBack);
        assertThat(plankBack.getHeeftKast()).isNull();
    }

    @Test
    void heeftStellingTest() throws Exception {
        Kast kast = getKastRandomSampleGenerator();
        Stelling stellingBack = getStellingRandomSampleGenerator();

        kast.setHeeftStelling(stellingBack);
        assertThat(kast.getHeeftStelling()).isEqualTo(stellingBack);

        kast.heeftStelling(null);
        assertThat(kast.getHeeftStelling()).isNull();
    }

    @Test
    void istevindeninVindplaatsTest() throws Exception {
        Kast kast = getKastRandomSampleGenerator();
        Vindplaats vindplaatsBack = getVindplaatsRandomSampleGenerator();

        kast.addIstevindeninVindplaats(vindplaatsBack);
        assertThat(kast.getIstevindeninVindplaats()).containsOnly(vindplaatsBack);
        assertThat(vindplaatsBack.getIstevindeninKast()).isEqualTo(kast);

        kast.removeIstevindeninVindplaats(vindplaatsBack);
        assertThat(kast.getIstevindeninVindplaats()).doesNotContain(vindplaatsBack);
        assertThat(vindplaatsBack.getIstevindeninKast()).isNull();

        kast.istevindeninVindplaats(new HashSet<>(Set.of(vindplaatsBack)));
        assertThat(kast.getIstevindeninVindplaats()).containsOnly(vindplaatsBack);
        assertThat(vindplaatsBack.getIstevindeninKast()).isEqualTo(kast);

        kast.setIstevindeninVindplaats(new HashSet<>());
        assertThat(kast.getIstevindeninVindplaats()).doesNotContain(vindplaatsBack);
        assertThat(vindplaatsBack.getIstevindeninKast()).isNull();
    }
}
