package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VervoersmiddelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VervoersmiddelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vervoersmiddel.class);
        Vervoersmiddel vervoersmiddel1 = getVervoersmiddelSample1();
        Vervoersmiddel vervoersmiddel2 = new Vervoersmiddel();
        assertThat(vervoersmiddel1).isNotEqualTo(vervoersmiddel2);

        vervoersmiddel2.setId(vervoersmiddel1.getId());
        assertThat(vervoersmiddel1).isEqualTo(vervoersmiddel2);

        vervoersmiddel2 = getVervoersmiddelSample2();
        assertThat(vervoersmiddel1).isNotEqualTo(vervoersmiddel2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Vervoersmiddel vervoersmiddel = new Vervoersmiddel();
        assertThat(vervoersmiddel.hashCode()).isZero();

        Vervoersmiddel vervoersmiddel1 = getVervoersmiddelSample1();
        vervoersmiddel.setId(vervoersmiddel1.getId());
        assertThat(vervoersmiddel).hasSameHashCodeAs(vervoersmiddel1);
    }
}
