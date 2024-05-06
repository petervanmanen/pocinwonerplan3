package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OpenbareruimteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpenbareruimteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Openbareruimte.class);
        Openbareruimte openbareruimte1 = getOpenbareruimteSample1();
        Openbareruimte openbareruimte2 = new Openbareruimte();
        assertThat(openbareruimte1).isNotEqualTo(openbareruimte2);

        openbareruimte2.setId(openbareruimte1.getId());
        assertThat(openbareruimte1).isEqualTo(openbareruimte2);

        openbareruimte2 = getOpenbareruimteSample2();
        assertThat(openbareruimte1).isNotEqualTo(openbareruimte2);
    }
}
