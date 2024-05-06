package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VthmeldingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VthmeldingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vthmelding.class);
        Vthmelding vthmelding1 = getVthmeldingSample1();
        Vthmelding vthmelding2 = new Vthmelding();
        assertThat(vthmelding1).isNotEqualTo(vthmelding2);

        vthmelding2.setId(vthmelding1.getId());
        assertThat(vthmelding1).isEqualTo(vthmelding2);

        vthmelding2 = getVthmeldingSample2();
        assertThat(vthmelding1).isNotEqualTo(vthmelding2);
    }
}
