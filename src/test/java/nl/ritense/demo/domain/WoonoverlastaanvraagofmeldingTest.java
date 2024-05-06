package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.WoonoverlastaanvraagofmeldingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WoonoverlastaanvraagofmeldingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Woonoverlastaanvraagofmelding.class);
        Woonoverlastaanvraagofmelding woonoverlastaanvraagofmelding1 = getWoonoverlastaanvraagofmeldingSample1();
        Woonoverlastaanvraagofmelding woonoverlastaanvraagofmelding2 = new Woonoverlastaanvraagofmelding();
        assertThat(woonoverlastaanvraagofmelding1).isNotEqualTo(woonoverlastaanvraagofmelding2);

        woonoverlastaanvraagofmelding2.setId(woonoverlastaanvraagofmelding1.getId());
        assertThat(woonoverlastaanvraagofmelding1).isEqualTo(woonoverlastaanvraagofmelding2);

        woonoverlastaanvraagofmelding2 = getWoonoverlastaanvraagofmeldingSample2();
        assertThat(woonoverlastaanvraagofmelding1).isNotEqualTo(woonoverlastaanvraagofmelding2);
    }
}
