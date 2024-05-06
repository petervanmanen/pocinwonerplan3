package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanbestedinginhuurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AanbestedinginhuurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aanbestedinginhuur.class);
        Aanbestedinginhuur aanbestedinginhuur1 = getAanbestedinginhuurSample1();
        Aanbestedinginhuur aanbestedinginhuur2 = new Aanbestedinginhuur();
        assertThat(aanbestedinginhuur1).isNotEqualTo(aanbestedinginhuur2);

        aanbestedinginhuur2.setId(aanbestedinginhuur1.getId());
        assertThat(aanbestedinginhuur1).isEqualTo(aanbestedinginhuur2);

        aanbestedinginhuur2 = getAanbestedinginhuurSample2();
        assertThat(aanbestedinginhuur1).isNotEqualTo(aanbestedinginhuur2);
    }
}
