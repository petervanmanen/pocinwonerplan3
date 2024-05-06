package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AomaanvraagwmojeugdTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AomaanvraagwmojeugdTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aomaanvraagwmojeugd.class);
        Aomaanvraagwmojeugd aomaanvraagwmojeugd1 = getAomaanvraagwmojeugdSample1();
        Aomaanvraagwmojeugd aomaanvraagwmojeugd2 = new Aomaanvraagwmojeugd();
        assertThat(aomaanvraagwmojeugd1).isNotEqualTo(aomaanvraagwmojeugd2);

        aomaanvraagwmojeugd2.setId(aomaanvraagwmojeugd1.getId());
        assertThat(aomaanvraagwmojeugd1).isEqualTo(aomaanvraagwmojeugd2);

        aomaanvraagwmojeugd2 = getAomaanvraagwmojeugdSample2();
        assertThat(aomaanvraagwmojeugd1).isNotEqualTo(aomaanvraagwmojeugd2);
    }
}
