package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.IngeschrevenpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IngeschrevenpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ingeschrevenpersoon.class);
        Ingeschrevenpersoon ingeschrevenpersoon1 = getIngeschrevenpersoonSample1();
        Ingeschrevenpersoon ingeschrevenpersoon2 = new Ingeschrevenpersoon();
        assertThat(ingeschrevenpersoon1).isNotEqualTo(ingeschrevenpersoon2);

        ingeschrevenpersoon2.setId(ingeschrevenpersoon1.getId());
        assertThat(ingeschrevenpersoon1).isEqualTo(ingeschrevenpersoon2);

        ingeschrevenpersoon2 = getIngeschrevenpersoonSample2();
        assertThat(ingeschrevenpersoon1).isNotEqualTo(ingeschrevenpersoon2);
    }
}
