package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AuteurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AuteurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Auteur.class);
        Auteur auteur1 = getAuteurSample1();
        Auteur auteur2 = new Auteur();
        assertThat(auteur1).isNotEqualTo(auteur2);

        auteur2.setId(auteur1.getId());
        assertThat(auteur1).isEqualTo(auteur2);

        auteur2 = getAuteurSample2();
        assertThat(auteur1).isNotEqualTo(auteur2);
    }
}
