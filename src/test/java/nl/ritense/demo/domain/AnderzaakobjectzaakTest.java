package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AnderzaakobjectzaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AnderzaakobjectzaakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Anderzaakobjectzaak.class);
        Anderzaakobjectzaak anderzaakobjectzaak1 = getAnderzaakobjectzaakSample1();
        Anderzaakobjectzaak anderzaakobjectzaak2 = new Anderzaakobjectzaak();
        assertThat(anderzaakobjectzaak1).isNotEqualTo(anderzaakobjectzaak2);

        anderzaakobjectzaak2.setId(anderzaakobjectzaak1.getId());
        assertThat(anderzaakobjectzaak1).isEqualTo(anderzaakobjectzaak2);

        anderzaakobjectzaak2 = getAnderzaakobjectzaakSample2();
        assertThat(anderzaakobjectzaak1).isNotEqualTo(anderzaakobjectzaak2);
    }
}
