package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AommeldingwmojeugdTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AommeldingwmojeugdTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aommeldingwmojeugd.class);
        Aommeldingwmojeugd aommeldingwmojeugd1 = getAommeldingwmojeugdSample1();
        Aommeldingwmojeugd aommeldingwmojeugd2 = new Aommeldingwmojeugd();
        assertThat(aommeldingwmojeugd1).isNotEqualTo(aommeldingwmojeugd2);

        aommeldingwmojeugd2.setId(aommeldingwmojeugd1.getId());
        assertThat(aommeldingwmojeugd1).isEqualTo(aommeldingwmojeugd2);

        aommeldingwmojeugd2 = getAommeldingwmojeugdSample2();
        assertThat(aommeldingwmojeugd1).isNotEqualTo(aommeldingwmojeugd2);
    }
}
