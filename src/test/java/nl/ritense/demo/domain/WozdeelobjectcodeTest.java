package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.WozdeelobjectcodeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WozdeelobjectcodeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wozdeelobjectcode.class);
        Wozdeelobjectcode wozdeelobjectcode1 = getWozdeelobjectcodeSample1();
        Wozdeelobjectcode wozdeelobjectcode2 = new Wozdeelobjectcode();
        assertThat(wozdeelobjectcode1).isNotEqualTo(wozdeelobjectcode2);

        wozdeelobjectcode2.setId(wozdeelobjectcode1.getId());
        assertThat(wozdeelobjectcode1).isEqualTo(wozdeelobjectcode2);

        wozdeelobjectcode2 = getWozdeelobjectcodeSample2();
        assertThat(wozdeelobjectcode1).isNotEqualTo(wozdeelobjectcode2);
    }
}
