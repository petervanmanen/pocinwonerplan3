package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PachterTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PachterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pachter.class);
        Pachter pachter1 = getPachterSample1();
        Pachter pachter2 = new Pachter();
        assertThat(pachter1).isNotEqualTo(pachter2);

        pachter2.setId(pachter1.getId());
        assertThat(pachter1).isEqualTo(pachter2);

        pachter2 = getPachterSample2();
        assertThat(pachter1).isNotEqualTo(pachter2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Pachter pachter = new Pachter();
        assertThat(pachter.hashCode()).isZero();

        Pachter pachter1 = getPachterSample1();
        pachter.setId(pachter1.getId());
        assertThat(pachter).hasSameHashCodeAs(pachter1);
    }
}
