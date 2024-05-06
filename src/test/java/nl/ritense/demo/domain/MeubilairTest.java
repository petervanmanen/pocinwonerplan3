package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MeubilairTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MeubilairTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Meubilair.class);
        Meubilair meubilair1 = getMeubilairSample1();
        Meubilair meubilair2 = new Meubilair();
        assertThat(meubilair1).isNotEqualTo(meubilair2);

        meubilair2.setId(meubilair1.getId());
        assertThat(meubilair1).isEqualTo(meubilair2);

        meubilair2 = getMeubilairSample2();
        assertThat(meubilair1).isNotEqualTo(meubilair2);
    }
}
