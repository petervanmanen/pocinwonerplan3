package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OverbruggingsdeelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OverbruggingsdeelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Overbruggingsdeel.class);
        Overbruggingsdeel overbruggingsdeel1 = getOverbruggingsdeelSample1();
        Overbruggingsdeel overbruggingsdeel2 = new Overbruggingsdeel();
        assertThat(overbruggingsdeel1).isNotEqualTo(overbruggingsdeel2);

        overbruggingsdeel2.setId(overbruggingsdeel1.getId());
        assertThat(overbruggingsdeel1).isEqualTo(overbruggingsdeel2);

        overbruggingsdeel2 = getOverbruggingsdeelSample2();
        assertThat(overbruggingsdeel1).isNotEqualTo(overbruggingsdeel2);
    }
}
