package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DepotTestSamples.*;
import static nl.ritense.demo.domain.KastTestSamples.*;
import static nl.ritense.demo.domain.MagazijnlocatieTestSamples.*;
import static nl.ritense.demo.domain.StellingTestSamples.*;
import static nl.ritense.demo.domain.VindplaatsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StellingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stelling.class);
        Stelling stelling1 = getStellingSample1();
        Stelling stelling2 = new Stelling();
        assertThat(stelling1).isNotEqualTo(stelling2);

        stelling2.setId(stelling1.getId());
        assertThat(stelling1).isEqualTo(stelling2);

        stelling2 = getStellingSample2();
        assertThat(stelling1).isNotEqualTo(stelling2);
    }

    @Test
    void heeftMagazijnlocatieTest() throws Exception {
        Stelling stelling = getStellingRandomSampleGenerator();
        Magazijnlocatie magazijnlocatieBack = getMagazijnlocatieRandomSampleGenerator();

        stelling.addHeeftMagazijnlocatie(magazijnlocatieBack);
        assertThat(stelling.getHeeftMagazijnlocaties()).containsOnly(magazijnlocatieBack);
        assertThat(magazijnlocatieBack.getHeeftStelling()).isEqualTo(stelling);

        stelling.removeHeeftMagazijnlocatie(magazijnlocatieBack);
        assertThat(stelling.getHeeftMagazijnlocaties()).doesNotContain(magazijnlocatieBack);
        assertThat(magazijnlocatieBack.getHeeftStelling()).isNull();

        stelling.heeftMagazijnlocaties(new HashSet<>(Set.of(magazijnlocatieBack)));
        assertThat(stelling.getHeeftMagazijnlocaties()).containsOnly(magazijnlocatieBack);
        assertThat(magazijnlocatieBack.getHeeftStelling()).isEqualTo(stelling);

        stelling.setHeeftMagazijnlocaties(new HashSet<>());
        assertThat(stelling.getHeeftMagazijnlocaties()).doesNotContain(magazijnlocatieBack);
        assertThat(magazijnlocatieBack.getHeeftStelling()).isNull();
    }

    @Test
    void heeftKastTest() throws Exception {
        Stelling stelling = getStellingRandomSampleGenerator();
        Kast kastBack = getKastRandomSampleGenerator();

        stelling.addHeeftKast(kastBack);
        assertThat(stelling.getHeeftKasts()).containsOnly(kastBack);
        assertThat(kastBack.getHeeftStelling()).isEqualTo(stelling);

        stelling.removeHeeftKast(kastBack);
        assertThat(stelling.getHeeftKasts()).doesNotContain(kastBack);
        assertThat(kastBack.getHeeftStelling()).isNull();

        stelling.heeftKasts(new HashSet<>(Set.of(kastBack)));
        assertThat(stelling.getHeeftKasts()).containsOnly(kastBack);
        assertThat(kastBack.getHeeftStelling()).isEqualTo(stelling);

        stelling.setHeeftKasts(new HashSet<>());
        assertThat(stelling.getHeeftKasts()).doesNotContain(kastBack);
        assertThat(kastBack.getHeeftStelling()).isNull();
    }

    @Test
    void heeftDepotTest() throws Exception {
        Stelling stelling = getStellingRandomSampleGenerator();
        Depot depotBack = getDepotRandomSampleGenerator();

        stelling.setHeeftDepot(depotBack);
        assertThat(stelling.getHeeftDepot()).isEqualTo(depotBack);

        stelling.heeftDepot(null);
        assertThat(stelling.getHeeftDepot()).isNull();
    }

    @Test
    void istevindeninVindplaatsTest() throws Exception {
        Stelling stelling = getStellingRandomSampleGenerator();
        Vindplaats vindplaatsBack = getVindplaatsRandomSampleGenerator();

        stelling.addIstevindeninVindplaats(vindplaatsBack);
        assertThat(stelling.getIstevindeninVindplaats()).containsOnly(vindplaatsBack);
        assertThat(vindplaatsBack.getIstevindeninStelling()).isEqualTo(stelling);

        stelling.removeIstevindeninVindplaats(vindplaatsBack);
        assertThat(stelling.getIstevindeninVindplaats()).doesNotContain(vindplaatsBack);
        assertThat(vindplaatsBack.getIstevindeninStelling()).isNull();

        stelling.istevindeninVindplaats(new HashSet<>(Set.of(vindplaatsBack)));
        assertThat(stelling.getIstevindeninVindplaats()).containsOnly(vindplaatsBack);
        assertThat(vindplaatsBack.getIstevindeninStelling()).isEqualTo(stelling);

        stelling.setIstevindeninVindplaats(new HashSet<>());
        assertThat(stelling.getIstevindeninVindplaats()).doesNotContain(vindplaatsBack);
        assertThat(vindplaatsBack.getIstevindeninStelling()).isNull();
    }
}
