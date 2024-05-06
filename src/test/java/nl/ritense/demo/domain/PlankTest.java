package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KastTestSamples.*;
import static nl.ritense.demo.domain.PlankTestSamples.*;
import static nl.ritense.demo.domain.VindplaatsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlankTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plank.class);
        Plank plank1 = getPlankSample1();
        Plank plank2 = new Plank();
        assertThat(plank1).isNotEqualTo(plank2);

        plank2.setId(plank1.getId());
        assertThat(plank1).isEqualTo(plank2);

        plank2 = getPlankSample2();
        assertThat(plank1).isNotEqualTo(plank2);
    }

    @Test
    void heeftKastTest() throws Exception {
        Plank plank = getPlankRandomSampleGenerator();
        Kast kastBack = getKastRandomSampleGenerator();

        plank.setHeeftKast(kastBack);
        assertThat(plank.getHeeftKast()).isEqualTo(kastBack);

        plank.heeftKast(null);
        assertThat(plank.getHeeftKast()).isNull();
    }

    @Test
    void istevindeninVindplaatsTest() throws Exception {
        Plank plank = getPlankRandomSampleGenerator();
        Vindplaats vindplaatsBack = getVindplaatsRandomSampleGenerator();

        plank.addIstevindeninVindplaats(vindplaatsBack);
        assertThat(plank.getIstevindeninVindplaats()).containsOnly(vindplaatsBack);
        assertThat(vindplaatsBack.getIstevindeninPlank()).isEqualTo(plank);

        plank.removeIstevindeninVindplaats(vindplaatsBack);
        assertThat(plank.getIstevindeninVindplaats()).doesNotContain(vindplaatsBack);
        assertThat(vindplaatsBack.getIstevindeninPlank()).isNull();

        plank.istevindeninVindplaats(new HashSet<>(Set.of(vindplaatsBack)));
        assertThat(plank.getIstevindeninVindplaats()).containsOnly(vindplaatsBack);
        assertThat(vindplaatsBack.getIstevindeninPlank()).isEqualTo(plank);

        plank.setIstevindeninVindplaats(new HashSet<>());
        assertThat(plank.getIstevindeninVindplaats()).doesNotContain(vindplaatsBack);
        assertThat(vindplaatsBack.getIstevindeninPlank()).isNull();
    }
}
