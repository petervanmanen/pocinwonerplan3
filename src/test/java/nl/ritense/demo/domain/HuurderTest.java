package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.HuurderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HuurderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Huurder.class);
        Huurder huurder1 = getHuurderSample1();
        Huurder huurder2 = new Huurder();
        assertThat(huurder1).isNotEqualTo(huurder2);

        huurder2.setId(huurder1.getId());
        assertThat(huurder1).isEqualTo(huurder2);

        huurder2 = getHuurderSample2();
        assertThat(huurder1).isNotEqualTo(huurder2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Huurder huurder = new Huurder();
        assertThat(huurder.hashCode()).isZero();

        Huurder huurder1 = getHuurderSample1();
        huurder.setId(huurder1.getId());
        assertThat(huurder).hasSameHashCodeAs(huurder1);
    }
}
