package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanvraagofmeldingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AanvraagofmeldingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aanvraagofmelding.class);
        Aanvraagofmelding aanvraagofmelding1 = getAanvraagofmeldingSample1();
        Aanvraagofmelding aanvraagofmelding2 = new Aanvraagofmelding();
        assertThat(aanvraagofmelding1).isNotEqualTo(aanvraagofmelding2);

        aanvraagofmelding2.setId(aanvraagofmelding1.getId());
        assertThat(aanvraagofmelding1).isEqualTo(aanvraagofmelding2);

        aanvraagofmelding2 = getAanvraagofmeldingSample2();
        assertThat(aanvraagofmelding1).isNotEqualTo(aanvraagofmelding2);
    }
}
