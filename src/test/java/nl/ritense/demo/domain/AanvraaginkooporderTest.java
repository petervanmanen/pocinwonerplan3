package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanvraaginkooporderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AanvraaginkooporderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aanvraaginkooporder.class);
        Aanvraaginkooporder aanvraaginkooporder1 = getAanvraaginkooporderSample1();
        Aanvraaginkooporder aanvraaginkooporder2 = new Aanvraaginkooporder();
        assertThat(aanvraaginkooporder1).isNotEqualTo(aanvraaginkooporder2);

        aanvraaginkooporder2.setId(aanvraaginkooporder1.getId());
        assertThat(aanvraaginkooporder1).isEqualTo(aanvraaginkooporder2);

        aanvraaginkooporder2 = getAanvraaginkooporderSample2();
        assertThat(aanvraaginkooporder1).isNotEqualTo(aanvraaginkooporder2);
    }
}
