package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MagazijnplaatsingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MagazijnplaatsingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Magazijnplaatsing.class);
        Magazijnplaatsing magazijnplaatsing1 = getMagazijnplaatsingSample1();
        Magazijnplaatsing magazijnplaatsing2 = new Magazijnplaatsing();
        assertThat(magazijnplaatsing1).isNotEqualTo(magazijnplaatsing2);

        magazijnplaatsing2.setId(magazijnplaatsing1.getId());
        assertThat(magazijnplaatsing1).isEqualTo(magazijnplaatsing2);

        magazijnplaatsing2 = getMagazijnplaatsingSample2();
        assertThat(magazijnplaatsing1).isNotEqualTo(magazijnplaatsing2);
    }
}
