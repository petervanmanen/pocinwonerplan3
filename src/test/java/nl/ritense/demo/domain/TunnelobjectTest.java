package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.TunnelobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TunnelobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tunnelobject.class);
        Tunnelobject tunnelobject1 = getTunnelobjectSample1();
        Tunnelobject tunnelobject2 = new Tunnelobject();
        assertThat(tunnelobject1).isNotEqualTo(tunnelobject2);

        tunnelobject2.setId(tunnelobject1.getId());
        assertThat(tunnelobject1).isEqualTo(tunnelobject2);

        tunnelobject2 = getTunnelobjectSample2();
        assertThat(tunnelobject1).isNotEqualTo(tunnelobject2);
    }
}
