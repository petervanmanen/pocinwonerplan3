package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SpeeltoestelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpeeltoestelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Speeltoestel.class);
        Speeltoestel speeltoestel1 = getSpeeltoestelSample1();
        Speeltoestel speeltoestel2 = new Speeltoestel();
        assertThat(speeltoestel1).isNotEqualTo(speeltoestel2);

        speeltoestel2.setId(speeltoestel1.getId());
        assertThat(speeltoestel1).isEqualTo(speeltoestel2);

        speeltoestel2 = getSpeeltoestelSample2();
        assertThat(speeltoestel1).isNotEqualTo(speeltoestel2);
    }
}
