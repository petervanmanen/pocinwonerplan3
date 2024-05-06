package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EobjecttypebTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EobjecttypebTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eobjecttypeb.class);
        Eobjecttypeb eobjecttypeb1 = getEobjecttypebSample1();
        Eobjecttypeb eobjecttypeb2 = new Eobjecttypeb();
        assertThat(eobjecttypeb1).isNotEqualTo(eobjecttypeb2);

        eobjecttypeb2.setId(eobjecttypeb1.getId());
        assertThat(eobjecttypeb1).isEqualTo(eobjecttypeb2);

        eobjecttypeb2 = getEobjecttypebSample2();
        assertThat(eobjecttypeb1).isNotEqualTo(eobjecttypeb2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Eobjecttypeb eobjecttypeb = new Eobjecttypeb();
        assertThat(eobjecttypeb.hashCode()).isZero();

        Eobjecttypeb eobjecttypeb1 = getEobjecttypebSample1();
        eobjecttypeb.setId(eobjecttypeb1.getId());
        assertThat(eobjecttypeb).hasSameHashCodeAs(eobjecttypeb1);
    }
}
