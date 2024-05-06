package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.InfiltratieputTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InfiltratieputTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Infiltratieput.class);
        Infiltratieput infiltratieput1 = getInfiltratieputSample1();
        Infiltratieput infiltratieput2 = new Infiltratieput();
        assertThat(infiltratieput1).isNotEqualTo(infiltratieput2);

        infiltratieput2.setId(infiltratieput1.getId());
        assertThat(infiltratieput1).isEqualTo(infiltratieput2);

        infiltratieput2 = getInfiltratieputSample2();
        assertThat(infiltratieput1).isNotEqualTo(infiltratieput2);
    }
}
