package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanvraagstadspasTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AanvraagstadspasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aanvraagstadspas.class);
        Aanvraagstadspas aanvraagstadspas1 = getAanvraagstadspasSample1();
        Aanvraagstadspas aanvraagstadspas2 = new Aanvraagstadspas();
        assertThat(aanvraagstadspas1).isNotEqualTo(aanvraagstadspas2);

        aanvraagstadspas2.setId(aanvraagstadspas1.getId());
        assertThat(aanvraagstadspas1).isEqualTo(aanvraagstadspas2);

        aanvraagstadspas2 = getAanvraagstadspasSample2();
        assertThat(aanvraagstadspas1).isNotEqualTo(aanvraagstadspas2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Aanvraagstadspas aanvraagstadspas = new Aanvraagstadspas();
        assertThat(aanvraagstadspas.hashCode()).isZero();

        Aanvraagstadspas aanvraagstadspas1 = getAanvraagstadspasSample1();
        aanvraagstadspas.setId(aanvraagstadspas1.getId());
        assertThat(aanvraagstadspas).hasSameHashCodeAs(aanvraagstadspas1);
    }
}
