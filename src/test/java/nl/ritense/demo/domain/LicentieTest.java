package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LicentieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LicentieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Licentie.class);
        Licentie licentie1 = getLicentieSample1();
        Licentie licentie2 = new Licentie();
        assertThat(licentie1).isNotEqualTo(licentie2);

        licentie2.setId(licentie1.getId());
        assertThat(licentie1).isEqualTo(licentie2);

        licentie2 = getLicentieSample2();
        assertThat(licentie1).isNotEqualTo(licentie2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Licentie licentie = new Licentie();
        assertThat(licentie.hashCode()).isZero();

        Licentie licentie1 = getLicentieSample1();
        licentie.setId(licentie1.getId());
        assertThat(licentie).hasSameHashCodeAs(licentie1);
    }
}
