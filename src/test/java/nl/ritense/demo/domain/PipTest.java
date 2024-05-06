package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PipTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PipTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pip.class);
        Pip pip1 = getPipSample1();
        Pip pip2 = new Pip();
        assertThat(pip1).isNotEqualTo(pip2);

        pip2.setId(pip1.getId());
        assertThat(pip1).isEqualTo(pip2);

        pip2 = getPipSample2();
        assertThat(pip1).isNotEqualTo(pip2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Pip pip = new Pip();
        assertThat(pip.hashCode()).isZero();

        Pip pip1 = getPipSample1();
        pip.setId(pip1.getId());
        assertThat(pip).hasSameHashCodeAs(pip1);
    }
}
