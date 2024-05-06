package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AppartementsrechtsplitsingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AppartementsrechtsplitsingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Appartementsrechtsplitsing.class);
        Appartementsrechtsplitsing appartementsrechtsplitsing1 = getAppartementsrechtsplitsingSample1();
        Appartementsrechtsplitsing appartementsrechtsplitsing2 = new Appartementsrechtsplitsing();
        assertThat(appartementsrechtsplitsing1).isNotEqualTo(appartementsrechtsplitsing2);

        appartementsrechtsplitsing2.setId(appartementsrechtsplitsing1.getId());
        assertThat(appartementsrechtsplitsing1).isEqualTo(appartementsrechtsplitsing2);

        appartementsrechtsplitsing2 = getAppartementsrechtsplitsingSample2();
        assertThat(appartementsrechtsplitsing1).isNotEqualTo(appartementsrechtsplitsing2);
    }
}
