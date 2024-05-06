package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanwezigedeelnemerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AanwezigedeelnemerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aanwezigedeelnemer.class);
        Aanwezigedeelnemer aanwezigedeelnemer1 = getAanwezigedeelnemerSample1();
        Aanwezigedeelnemer aanwezigedeelnemer2 = new Aanwezigedeelnemer();
        assertThat(aanwezigedeelnemer1).isNotEqualTo(aanwezigedeelnemer2);

        aanwezigedeelnemer2.setId(aanwezigedeelnemer1.getId());
        assertThat(aanwezigedeelnemer1).isEqualTo(aanwezigedeelnemer2);

        aanwezigedeelnemer2 = getAanwezigedeelnemerSample2();
        assertThat(aanwezigedeelnemer1).isNotEqualTo(aanwezigedeelnemer2);
    }
}
