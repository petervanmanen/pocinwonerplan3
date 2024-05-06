package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KaartTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KaartTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kaart.class);
        Kaart kaart1 = getKaartSample1();
        Kaart kaart2 = new Kaart();
        assertThat(kaart1).isNotEqualTo(kaart2);

        kaart2.setId(kaart1.getId());
        assertThat(kaart1).isEqualTo(kaart2);

        kaart2 = getKaartSample2();
        assertThat(kaart1).isNotEqualTo(kaart2);
    }
}
