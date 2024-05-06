package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeschermdestatusTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BeschermdestatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beschermdestatus.class);
        Beschermdestatus beschermdestatus1 = getBeschermdestatusSample1();
        Beschermdestatus beschermdestatus2 = new Beschermdestatus();
        assertThat(beschermdestatus1).isNotEqualTo(beschermdestatus2);

        beschermdestatus2.setId(beschermdestatus1.getId());
        assertThat(beschermdestatus1).isEqualTo(beschermdestatus2);

        beschermdestatus2 = getBeschermdestatusSample2();
        assertThat(beschermdestatus1).isNotEqualTo(beschermdestatus2);
    }
}
