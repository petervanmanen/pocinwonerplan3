package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerplichtingwmojeugdTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerplichtingwmojeugdTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verplichtingwmojeugd.class);
        Verplichtingwmojeugd verplichtingwmojeugd1 = getVerplichtingwmojeugdSample1();
        Verplichtingwmojeugd verplichtingwmojeugd2 = new Verplichtingwmojeugd();
        assertThat(verplichtingwmojeugd1).isNotEqualTo(verplichtingwmojeugd2);

        verplichtingwmojeugd2.setId(verplichtingwmojeugd1.getId());
        assertThat(verplichtingwmojeugd1).isEqualTo(verplichtingwmojeugd2);

        verplichtingwmojeugd2 = getVerplichtingwmojeugdSample2();
        assertThat(verplichtingwmojeugd1).isNotEqualTo(verplichtingwmojeugd2);
    }
}
