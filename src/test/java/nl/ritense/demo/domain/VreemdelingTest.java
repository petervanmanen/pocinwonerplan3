package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VreemdelingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VreemdelingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vreemdeling.class);
        Vreemdeling vreemdeling1 = getVreemdelingSample1();
        Vreemdeling vreemdeling2 = new Vreemdeling();
        assertThat(vreemdeling1).isNotEqualTo(vreemdeling2);

        vreemdeling2.setId(vreemdeling1.getId());
        assertThat(vreemdeling1).isEqualTo(vreemdeling2);

        vreemdeling2 = getVreemdelingSample2();
        assertThat(vreemdeling1).isNotEqualTo(vreemdeling2);
    }
}
