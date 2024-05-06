package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VervoerderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VervoerderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vervoerder.class);
        Vervoerder vervoerder1 = getVervoerderSample1();
        Vervoerder vervoerder2 = new Vervoerder();
        assertThat(vervoerder1).isNotEqualTo(vervoerder2);

        vervoerder2.setId(vervoerder1.getId());
        assertThat(vervoerder1).isEqualTo(vervoerder2);

        vervoerder2 = getVervoerderSample2();
        assertThat(vervoerder1).isNotEqualTo(vervoerder2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Vervoerder vervoerder = new Vervoerder();
        assertThat(vervoerder.hashCode()).isZero();

        Vervoerder vervoerder1 = getVervoerderSample1();
        vervoerder.setId(vervoerder1.getId());
        assertThat(vervoerder).hasSameHashCodeAs(vervoerder1);
    }
}
