package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.UitvoerendeinstantieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UitvoerendeinstantieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uitvoerendeinstantie.class);
        Uitvoerendeinstantie uitvoerendeinstantie1 = getUitvoerendeinstantieSample1();
        Uitvoerendeinstantie uitvoerendeinstantie2 = new Uitvoerendeinstantie();
        assertThat(uitvoerendeinstantie1).isNotEqualTo(uitvoerendeinstantie2);

        uitvoerendeinstantie2.setId(uitvoerendeinstantie1.getId());
        assertThat(uitvoerendeinstantie1).isEqualTo(uitvoerendeinstantie2);

        uitvoerendeinstantie2 = getUitvoerendeinstantieSample2();
        assertThat(uitvoerendeinstantie1).isNotEqualTo(uitvoerendeinstantie2);
    }
}
