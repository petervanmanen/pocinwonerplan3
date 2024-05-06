package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClientTestSamples.*;
import static nl.ritense.demo.domain.FraudegegevensTestSamples.*;
import static nl.ritense.demo.domain.FraudesoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FraudegegevensTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fraudegegevens.class);
        Fraudegegevens fraudegegevens1 = getFraudegegevensSample1();
        Fraudegegevens fraudegegevens2 = new Fraudegegevens();
        assertThat(fraudegegevens1).isNotEqualTo(fraudegegevens2);

        fraudegegevens2.setId(fraudegegevens1.getId());
        assertThat(fraudegegevens1).isEqualTo(fraudegegevens2);

        fraudegegevens2 = getFraudegegevensSample2();
        assertThat(fraudegegevens1).isNotEqualTo(fraudegegevens2);
    }

    @Test
    void isvansoortFraudesoortTest() throws Exception {
        Fraudegegevens fraudegegevens = getFraudegegevensRandomSampleGenerator();
        Fraudesoort fraudesoortBack = getFraudesoortRandomSampleGenerator();

        fraudegegevens.setIsvansoortFraudesoort(fraudesoortBack);
        assertThat(fraudegegevens.getIsvansoortFraudesoort()).isEqualTo(fraudesoortBack);

        fraudegegevens.isvansoortFraudesoort(null);
        assertThat(fraudegegevens.getIsvansoortFraudesoort()).isNull();
    }

    @Test
    void heeftfraudegegevensClientTest() throws Exception {
        Fraudegegevens fraudegegevens = getFraudegegevensRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        fraudegegevens.setHeeftfraudegegevensClient(clientBack);
        assertThat(fraudegegevens.getHeeftfraudegegevensClient()).isEqualTo(clientBack);

        fraudegegevens.heeftfraudegegevensClient(null);
        assertThat(fraudegegevens.getHeeftfraudegegevensClient()).isNull();
    }
}
