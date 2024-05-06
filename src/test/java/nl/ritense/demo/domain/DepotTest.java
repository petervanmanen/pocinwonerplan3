package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DepotTestSamples.*;
import static nl.ritense.demo.domain.StellingTestSamples.*;
import static nl.ritense.demo.domain.VindplaatsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DepotTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Depot.class);
        Depot depot1 = getDepotSample1();
        Depot depot2 = new Depot();
        assertThat(depot1).isNotEqualTo(depot2);

        depot2.setId(depot1.getId());
        assertThat(depot1).isEqualTo(depot2);

        depot2 = getDepotSample2();
        assertThat(depot1).isNotEqualTo(depot2);
    }

    @Test
    void heeftStellingTest() throws Exception {
        Depot depot = getDepotRandomSampleGenerator();
        Stelling stellingBack = getStellingRandomSampleGenerator();

        depot.addHeeftStelling(stellingBack);
        assertThat(depot.getHeeftStellings()).containsOnly(stellingBack);
        assertThat(stellingBack.getHeeftDepot()).isEqualTo(depot);

        depot.removeHeeftStelling(stellingBack);
        assertThat(depot.getHeeftStellings()).doesNotContain(stellingBack);
        assertThat(stellingBack.getHeeftDepot()).isNull();

        depot.heeftStellings(new HashSet<>(Set.of(stellingBack)));
        assertThat(depot.getHeeftStellings()).containsOnly(stellingBack);
        assertThat(stellingBack.getHeeftDepot()).isEqualTo(depot);

        depot.setHeeftStellings(new HashSet<>());
        assertThat(depot.getHeeftStellings()).doesNotContain(stellingBack);
        assertThat(stellingBack.getHeeftDepot()).isNull();
    }

    @Test
    void istevindeninVindplaatsTest() throws Exception {
        Depot depot = getDepotRandomSampleGenerator();
        Vindplaats vindplaatsBack = getVindplaatsRandomSampleGenerator();

        depot.addIstevindeninVindplaats(vindplaatsBack);
        assertThat(depot.getIstevindeninVindplaats()).containsOnly(vindplaatsBack);
        assertThat(vindplaatsBack.getIstevindeninDepot()).isEqualTo(depot);

        depot.removeIstevindeninVindplaats(vindplaatsBack);
        assertThat(depot.getIstevindeninVindplaats()).doesNotContain(vindplaatsBack);
        assertThat(vindplaatsBack.getIstevindeninDepot()).isNull();

        depot.istevindeninVindplaats(new HashSet<>(Set.of(vindplaatsBack)));
        assertThat(depot.getIstevindeninVindplaats()).containsOnly(vindplaatsBack);
        assertThat(vindplaatsBack.getIstevindeninDepot()).isEqualTo(depot);

        depot.setIstevindeninVindplaats(new HashSet<>());
        assertThat(depot.getIstevindeninVindplaats()).doesNotContain(vindplaatsBack);
        assertThat(vindplaatsBack.getIstevindeninDepot()).isNull();
    }
}
