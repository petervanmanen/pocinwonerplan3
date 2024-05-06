package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.TunneldeelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TunneldeelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tunneldeel.class);
        Tunneldeel tunneldeel1 = getTunneldeelSample1();
        Tunneldeel tunneldeel2 = new Tunneldeel();
        assertThat(tunneldeel1).isNotEqualTo(tunneldeel2);

        tunneldeel2.setId(tunneldeel1.getId());
        assertThat(tunneldeel1).isEqualTo(tunneldeel2);

        tunneldeel2 = getTunneldeelSample2();
        assertThat(tunneldeel1).isNotEqualTo(tunneldeel2);
    }
}
