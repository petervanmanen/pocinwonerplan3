package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ZTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ZTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Z.class);
        Z z1 = getZSample1();
        Z z2 = new Z();
        assertThat(z1).isNotEqualTo(z2);

        z2.setId(z1.getId());
        assertThat(z1).isEqualTo(z2);

        z2 = getZSample2();
        assertThat(z1).isNotEqualTo(z2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Z z = new Z();
        assertThat(z.hashCode()).isZero();

        Z z1 = getZSample1();
        z.setId(z1.getId());
        assertThat(z).hasSameHashCodeAs(z1);
    }
}
