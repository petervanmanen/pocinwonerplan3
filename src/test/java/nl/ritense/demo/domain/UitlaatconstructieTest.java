package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.UitlaatconstructieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UitlaatconstructieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uitlaatconstructie.class);
        Uitlaatconstructie uitlaatconstructie1 = getUitlaatconstructieSample1();
        Uitlaatconstructie uitlaatconstructie2 = new Uitlaatconstructie();
        assertThat(uitlaatconstructie1).isNotEqualTo(uitlaatconstructie2);

        uitlaatconstructie2.setId(uitlaatconstructie1.getId());
        assertThat(uitlaatconstructie1).isEqualTo(uitlaatconstructie2);

        uitlaatconstructie2 = getUitlaatconstructieSample2();
        assertThat(uitlaatconstructie1).isNotEqualTo(uitlaatconstructie2);
    }
}
