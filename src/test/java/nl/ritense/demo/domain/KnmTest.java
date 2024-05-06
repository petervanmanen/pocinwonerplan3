package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KnmTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KnmTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Knm.class);
        Knm knm1 = getKnmSample1();
        Knm knm2 = new Knm();
        assertThat(knm1).isNotEqualTo(knm2);

        knm2.setId(knm1.getId());
        assertThat(knm1).isEqualTo(knm2);

        knm2 = getKnmSample2();
        assertThat(knm1).isNotEqualTo(knm2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Knm knm = new Knm();
        assertThat(knm.hashCode()).isZero();

        Knm knm1 = getKnmSample1();
        knm.setId(knm1.getId());
        assertThat(knm).hasSameHashCodeAs(knm1);
    }
}
