package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OuderofverzorgerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OuderofverzorgerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ouderofverzorger.class);
        Ouderofverzorger ouderofverzorger1 = getOuderofverzorgerSample1();
        Ouderofverzorger ouderofverzorger2 = new Ouderofverzorger();
        assertThat(ouderofverzorger1).isNotEqualTo(ouderofverzorger2);

        ouderofverzorger2.setId(ouderofverzorger1.getId());
        assertThat(ouderofverzorger1).isEqualTo(ouderofverzorger2);

        ouderofverzorger2 = getOuderofverzorgerSample2();
        assertThat(ouderofverzorger1).isNotEqualTo(ouderofverzorger2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Ouderofverzorger ouderofverzorger = new Ouderofverzorger();
        assertThat(ouderofverzorger.hashCode()).isZero();

        Ouderofverzorger ouderofverzorger1 = getOuderofverzorgerSample1();
        ouderofverzorger.setId(ouderofverzorger1.getId());
        assertThat(ouderofverzorger).hasSameHashCodeAs(ouderofverzorger1);
    }
}
