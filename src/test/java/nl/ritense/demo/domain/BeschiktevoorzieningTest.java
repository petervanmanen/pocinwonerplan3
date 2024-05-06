package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeschiktevoorzieningTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BeschiktevoorzieningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beschiktevoorziening.class);
        Beschiktevoorziening beschiktevoorziening1 = getBeschiktevoorzieningSample1();
        Beschiktevoorziening beschiktevoorziening2 = new Beschiktevoorziening();
        assertThat(beschiktevoorziening1).isNotEqualTo(beschiktevoorziening2);

        beschiktevoorziening2.setId(beschiktevoorziening1.getId());
        assertThat(beschiktevoorziening1).isEqualTo(beschiktevoorziening2);

        beschiktevoorziening2 = getBeschiktevoorzieningSample2();
        assertThat(beschiktevoorziening1).isNotEqualTo(beschiktevoorziening2);
    }
}
