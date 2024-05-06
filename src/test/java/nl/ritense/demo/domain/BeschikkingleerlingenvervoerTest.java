package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeschikkingleerlingenvervoerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BeschikkingleerlingenvervoerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beschikkingleerlingenvervoer.class);
        Beschikkingleerlingenvervoer beschikkingleerlingenvervoer1 = getBeschikkingleerlingenvervoerSample1();
        Beschikkingleerlingenvervoer beschikkingleerlingenvervoer2 = new Beschikkingleerlingenvervoer();
        assertThat(beschikkingleerlingenvervoer1).isNotEqualTo(beschikkingleerlingenvervoer2);

        beschikkingleerlingenvervoer2.setId(beschikkingleerlingenvervoer1.getId());
        assertThat(beschikkingleerlingenvervoer1).isEqualTo(beschikkingleerlingenvervoer2);

        beschikkingleerlingenvervoer2 = getBeschikkingleerlingenvervoerSample2();
        assertThat(beschikkingleerlingenvervoer1).isNotEqualTo(beschikkingleerlingenvervoer2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Beschikkingleerlingenvervoer beschikkingleerlingenvervoer = new Beschikkingleerlingenvervoer();
        assertThat(beschikkingleerlingenvervoer.hashCode()).isZero();

        Beschikkingleerlingenvervoer beschikkingleerlingenvervoer1 = getBeschikkingleerlingenvervoerSample1();
        beschikkingleerlingenvervoer.setId(beschikkingleerlingenvervoer1.getId());
        assertThat(beschikkingleerlingenvervoer).hasSameHashCodeAs(beschikkingleerlingenvervoer1);
    }
}
