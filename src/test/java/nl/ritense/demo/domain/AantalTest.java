package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AantalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AantalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aantal.class);
        Aantal aantal1 = getAantalSample1();
        Aantal aantal2 = new Aantal();
        assertThat(aantal1).isNotEqualTo(aantal2);

        aantal2.setId(aantal1.getId());
        assertThat(aantal1).isEqualTo(aantal2);

        aantal2 = getAantalSample2();
        assertThat(aantal1).isNotEqualTo(aantal2);
    }
}
