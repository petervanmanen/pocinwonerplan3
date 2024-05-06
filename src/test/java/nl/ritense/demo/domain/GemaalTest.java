package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GemaalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GemaalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gemaal.class);
        Gemaal gemaal1 = getGemaalSample1();
        Gemaal gemaal2 = new Gemaal();
        assertThat(gemaal1).isNotEqualTo(gemaal2);

        gemaal2.setId(gemaal1.getId());
        assertThat(gemaal1).isEqualTo(gemaal2);

        gemaal2 = getGemaalSample2();
        assertThat(gemaal1).isNotEqualTo(gemaal2);
    }
}
