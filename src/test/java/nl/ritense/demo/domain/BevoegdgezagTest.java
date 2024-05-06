package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BevoegdgezagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BevoegdgezagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bevoegdgezag.class);
        Bevoegdgezag bevoegdgezag1 = getBevoegdgezagSample1();
        Bevoegdgezag bevoegdgezag2 = new Bevoegdgezag();
        assertThat(bevoegdgezag1).isNotEqualTo(bevoegdgezag2);

        bevoegdgezag2.setId(bevoegdgezag1.getId());
        assertThat(bevoegdgezag1).isEqualTo(bevoegdgezag2);

        bevoegdgezag2 = getBevoegdgezagSample2();
        assertThat(bevoegdgezag1).isNotEqualTo(bevoegdgezag2);
    }
}
