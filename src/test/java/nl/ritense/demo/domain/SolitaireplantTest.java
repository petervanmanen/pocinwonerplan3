package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SolitaireplantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SolitaireplantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Solitaireplant.class);
        Solitaireplant solitaireplant1 = getSolitaireplantSample1();
        Solitaireplant solitaireplant2 = new Solitaireplant();
        assertThat(solitaireplant1).isNotEqualTo(solitaireplant2);

        solitaireplant2.setId(solitaireplant1.getId());
        assertThat(solitaireplant1).isEqualTo(solitaireplant2);

        solitaireplant2 = getSolitaireplantSample2();
        assertThat(solitaireplant1).isNotEqualTo(solitaireplant2);
    }
}
