package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ToegangsmiddelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ToegangsmiddelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Toegangsmiddel.class);
        Toegangsmiddel toegangsmiddel1 = getToegangsmiddelSample1();
        Toegangsmiddel toegangsmiddel2 = new Toegangsmiddel();
        assertThat(toegangsmiddel1).isNotEqualTo(toegangsmiddel2);

        toegangsmiddel2.setId(toegangsmiddel1.getId());
        assertThat(toegangsmiddel1).isEqualTo(toegangsmiddel2);

        toegangsmiddel2 = getToegangsmiddelSample2();
        assertThat(toegangsmiddel1).isNotEqualTo(toegangsmiddel2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Toegangsmiddel toegangsmiddel = new Toegangsmiddel();
        assertThat(toegangsmiddel.hashCode()).isZero();

        Toegangsmiddel toegangsmiddel1 = getToegangsmiddelSample1();
        toegangsmiddel.setId(toegangsmiddel1.getId());
        assertThat(toegangsmiddel).hasSameHashCodeAs(toegangsmiddel1);
    }
}
