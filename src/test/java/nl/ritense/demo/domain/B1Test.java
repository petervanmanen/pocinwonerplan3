package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.B1TestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class B1Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(B1.class);
        B1 b11 = getB1Sample1();
        B1 b12 = new B1();
        assertThat(b11).isNotEqualTo(b12);

        b12.setId(b11.getId());
        assertThat(b11).isEqualTo(b12);

        b12 = getB1Sample2();
        assertThat(b11).isNotEqualTo(b12);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        B1 b1 = new B1();
        assertThat(b1.hashCode()).isZero();

        B1 b11 = getB1Sample1();
        b1.setId(b11.getId());
        assertThat(b1).hasSameHashCodeAs(b11);
    }
}
