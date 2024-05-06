package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanvraagvrijstellingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AanvraagvrijstellingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aanvraagvrijstelling.class);
        Aanvraagvrijstelling aanvraagvrijstelling1 = getAanvraagvrijstellingSample1();
        Aanvraagvrijstelling aanvraagvrijstelling2 = new Aanvraagvrijstelling();
        assertThat(aanvraagvrijstelling1).isNotEqualTo(aanvraagvrijstelling2);

        aanvraagvrijstelling2.setId(aanvraagvrijstelling1.getId());
        assertThat(aanvraagvrijstelling1).isEqualTo(aanvraagvrijstelling2);

        aanvraagvrijstelling2 = getAanvraagvrijstellingSample2();
        assertThat(aanvraagvrijstelling1).isNotEqualTo(aanvraagvrijstelling2);
    }
}
