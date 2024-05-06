package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ErouteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ErouteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eroute.class);
        Eroute eroute1 = getErouteSample1();
        Eroute eroute2 = new Eroute();
        assertThat(eroute1).isNotEqualTo(eroute2);

        eroute2.setId(eroute1.getId());
        assertThat(eroute1).isEqualTo(eroute2);

        eroute2 = getErouteSample2();
        assertThat(eroute1).isNotEqualTo(eroute2);
    }
}
