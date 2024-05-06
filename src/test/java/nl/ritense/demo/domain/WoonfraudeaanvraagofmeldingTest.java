package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.WoonfraudeaanvraagofmeldingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WoonfraudeaanvraagofmeldingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Woonfraudeaanvraagofmelding.class);
        Woonfraudeaanvraagofmelding woonfraudeaanvraagofmelding1 = getWoonfraudeaanvraagofmeldingSample1();
        Woonfraudeaanvraagofmelding woonfraudeaanvraagofmelding2 = new Woonfraudeaanvraagofmelding();
        assertThat(woonfraudeaanvraagofmelding1).isNotEqualTo(woonfraudeaanvraagofmelding2);

        woonfraudeaanvraagofmelding2.setId(woonfraudeaanvraagofmelding1.getId());
        assertThat(woonfraudeaanvraagofmelding1).isEqualTo(woonfraudeaanvraagofmelding2);

        woonfraudeaanvraagofmelding2 = getWoonfraudeaanvraagofmeldingSample2();
        assertThat(woonfraudeaanvraagofmelding1).isNotEqualTo(woonfraudeaanvraagofmelding2);
    }
}
