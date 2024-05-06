package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KlachtleerlingenvervoerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KlachtleerlingenvervoerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Klachtleerlingenvervoer.class);
        Klachtleerlingenvervoer klachtleerlingenvervoer1 = getKlachtleerlingenvervoerSample1();
        Klachtleerlingenvervoer klachtleerlingenvervoer2 = new Klachtleerlingenvervoer();
        assertThat(klachtleerlingenvervoer1).isNotEqualTo(klachtleerlingenvervoer2);

        klachtleerlingenvervoer2.setId(klachtleerlingenvervoer1.getId());
        assertThat(klachtleerlingenvervoer1).isEqualTo(klachtleerlingenvervoer2);

        klachtleerlingenvervoer2 = getKlachtleerlingenvervoerSample2();
        assertThat(klachtleerlingenvervoer1).isNotEqualTo(klachtleerlingenvervoer2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Klachtleerlingenvervoer klachtleerlingenvervoer = new Klachtleerlingenvervoer();
        assertThat(klachtleerlingenvervoer.hashCode()).isZero();

        Klachtleerlingenvervoer klachtleerlingenvervoer1 = getKlachtleerlingenvervoerSample1();
        klachtleerlingenvervoer.setId(klachtleerlingenvervoer1.getId());
        assertThat(klachtleerlingenvervoer).hasSameHashCodeAs(klachtleerlingenvervoer1);
    }
}
