package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PrecarioTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrecarioTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Precario.class);
        Precario precario1 = getPrecarioSample1();
        Precario precario2 = new Precario();
        assertThat(precario1).isNotEqualTo(precario2);

        precario2.setId(precario1.getId());
        assertThat(precario1).isEqualTo(precario2);

        precario2 = getPrecarioSample2();
        assertThat(precario1).isNotEqualTo(precario2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Precario precario = new Precario();
        assertThat(precario.hashCode()).isZero();

        Precario precario1 = getPrecarioSample1();
        precario.setId(precario1.getId());
        assertThat(precario).hasSameHashCodeAs(precario1);
    }
}
