package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GebruikerrolTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GebruikerrolTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gebruikerrol.class);
        Gebruikerrol gebruikerrol1 = getGebruikerrolSample1();
        Gebruikerrol gebruikerrol2 = new Gebruikerrol();
        assertThat(gebruikerrol1).isNotEqualTo(gebruikerrol2);

        gebruikerrol2.setId(gebruikerrol1.getId());
        assertThat(gebruikerrol1).isEqualTo(gebruikerrol2);

        gebruikerrol2 = getGebruikerrolSample2();
        assertThat(gebruikerrol1).isNotEqualTo(gebruikerrol2);
    }
}
