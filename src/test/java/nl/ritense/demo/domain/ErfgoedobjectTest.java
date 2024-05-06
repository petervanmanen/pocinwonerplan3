package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ErfgoedobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ErfgoedobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Erfgoedobject.class);
        Erfgoedobject erfgoedobject1 = getErfgoedobjectSample1();
        Erfgoedobject erfgoedobject2 = new Erfgoedobject();
        assertThat(erfgoedobject1).isNotEqualTo(erfgoedobject2);

        erfgoedobject2.setId(erfgoedobject1.getId());
        assertThat(erfgoedobject1).isEqualTo(erfgoedobject2);

        erfgoedobject2 = getErfgoedobjectSample2();
        assertThat(erfgoedobject1).isNotEqualTo(erfgoedobject2);
    }
}
