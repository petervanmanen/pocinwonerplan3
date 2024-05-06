package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GebruiksdoelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GebruiksdoelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gebruiksdoel.class);
        Gebruiksdoel gebruiksdoel1 = getGebruiksdoelSample1();
        Gebruiksdoel gebruiksdoel2 = new Gebruiksdoel();
        assertThat(gebruiksdoel1).isNotEqualTo(gebruiksdoel2);

        gebruiksdoel2.setId(gebruiksdoel1.getId());
        assertThat(gebruiksdoel1).isEqualTo(gebruiksdoel2);

        gebruiksdoel2 = getGebruiksdoelSample2();
        assertThat(gebruiksdoel1).isNotEqualTo(gebruiksdoel2);
    }
}
