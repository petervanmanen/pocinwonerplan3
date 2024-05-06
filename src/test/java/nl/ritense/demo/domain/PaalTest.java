package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PaalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Paal.class);
        Paal paal1 = getPaalSample1();
        Paal paal2 = new Paal();
        assertThat(paal1).isNotEqualTo(paal2);

        paal2.setId(paal1.getId());
        assertThat(paal1).isEqualTo(paal2);

        paal2 = getPaalSample2();
        assertThat(paal1).isNotEqualTo(paal2);
    }
}
