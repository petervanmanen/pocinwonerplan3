package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FraudegegevensTestSamples.*;
import static nl.ritense.demo.domain.FraudesoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FraudesoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fraudesoort.class);
        Fraudesoort fraudesoort1 = getFraudesoortSample1();
        Fraudesoort fraudesoort2 = new Fraudesoort();
        assertThat(fraudesoort1).isNotEqualTo(fraudesoort2);

        fraudesoort2.setId(fraudesoort1.getId());
        assertThat(fraudesoort1).isEqualTo(fraudesoort2);

        fraudesoort2 = getFraudesoortSample2();
        assertThat(fraudesoort1).isNotEqualTo(fraudesoort2);
    }

    @Test
    void isvansoortFraudegegevensTest() throws Exception {
        Fraudesoort fraudesoort = getFraudesoortRandomSampleGenerator();
        Fraudegegevens fraudegegevensBack = getFraudegegevensRandomSampleGenerator();

        fraudesoort.addIsvansoortFraudegegevens(fraudegegevensBack);
        assertThat(fraudesoort.getIsvansoortFraudegegevens()).containsOnly(fraudegegevensBack);
        assertThat(fraudegegevensBack.getIsvansoortFraudesoort()).isEqualTo(fraudesoort);

        fraudesoort.removeIsvansoortFraudegegevens(fraudegegevensBack);
        assertThat(fraudesoort.getIsvansoortFraudegegevens()).doesNotContain(fraudegegevensBack);
        assertThat(fraudegegevensBack.getIsvansoortFraudesoort()).isNull();

        fraudesoort.isvansoortFraudegegevens(new HashSet<>(Set.of(fraudegegevensBack)));
        assertThat(fraudesoort.getIsvansoortFraudegegevens()).containsOnly(fraudegegevensBack);
        assertThat(fraudegegevensBack.getIsvansoortFraudesoort()).isEqualTo(fraudesoort);

        fraudesoort.setIsvansoortFraudegegevens(new HashSet<>());
        assertThat(fraudesoort.getIsvansoortFraudegegevens()).doesNotContain(fraudegegevensBack);
        assertThat(fraudegegevensBack.getIsvansoortFraudesoort()).isNull();
    }
}
