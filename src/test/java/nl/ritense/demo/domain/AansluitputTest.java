package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AansluitputTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AansluitputTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aansluitput.class);
        Aansluitput aansluitput1 = getAansluitputSample1();
        Aansluitput aansluitput2 = new Aansluitput();
        assertThat(aansluitput1).isNotEqualTo(aansluitput2);

        aansluitput2.setId(aansluitput1.getId());
        assertThat(aansluitput1).isEqualTo(aansluitput2);

        aansluitput2 = getAansluitputSample2();
        assertThat(aansluitput1).isNotEqualTo(aansluitput2);
    }
}
