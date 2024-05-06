package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PuntTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PuntTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Punt.class);
        Punt punt1 = getPuntSample1();
        Punt punt2 = new Punt();
        assertThat(punt1).isNotEqualTo(punt2);

        punt2.setId(punt1.getId());
        assertThat(punt1).isEqualTo(punt2);

        punt2 = getPuntSample2();
        assertThat(punt1).isNotEqualTo(punt2);
    }
}
