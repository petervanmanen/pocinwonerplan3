package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LigplaatsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LigplaatsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ligplaats.class);
        Ligplaats ligplaats1 = getLigplaatsSample1();
        Ligplaats ligplaats2 = new Ligplaats();
        assertThat(ligplaats1).isNotEqualTo(ligplaats2);

        ligplaats2.setId(ligplaats1.getId());
        assertThat(ligplaats1).isEqualTo(ligplaats2);

        ligplaats2 = getLigplaatsSample2();
        assertThat(ligplaats1).isNotEqualTo(ligplaats2);
    }
}
