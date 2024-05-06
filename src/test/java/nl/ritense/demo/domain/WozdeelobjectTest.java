package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.WozdeelobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WozdeelobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wozdeelobject.class);
        Wozdeelobject wozdeelobject1 = getWozdeelobjectSample1();
        Wozdeelobject wozdeelobject2 = new Wozdeelobject();
        assertThat(wozdeelobject1).isNotEqualTo(wozdeelobject2);

        wozdeelobject2.setId(wozdeelobject1.getId());
        assertThat(wozdeelobject1).isEqualTo(wozdeelobject2);

        wozdeelobject2 = getWozdeelobjectSample2();
        assertThat(wozdeelobject1).isNotEqualTo(wozdeelobject2);
    }
}
