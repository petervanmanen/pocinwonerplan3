package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FietsparkeervoorzieningTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FietsparkeervoorzieningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fietsparkeervoorziening.class);
        Fietsparkeervoorziening fietsparkeervoorziening1 = getFietsparkeervoorzieningSample1();
        Fietsparkeervoorziening fietsparkeervoorziening2 = new Fietsparkeervoorziening();
        assertThat(fietsparkeervoorziening1).isNotEqualTo(fietsparkeervoorziening2);

        fietsparkeervoorziening2.setId(fietsparkeervoorziening1.getId());
        assertThat(fietsparkeervoorziening1).isEqualTo(fietsparkeervoorziening2);

        fietsparkeervoorziening2 = getFietsparkeervoorzieningSample2();
        assertThat(fietsparkeervoorziening1).isNotEqualTo(fietsparkeervoorziening2);
    }
}
