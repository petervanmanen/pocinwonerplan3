package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EobjecttypecTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EobjecttypecTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eobjecttypec.class);
        Eobjecttypec eobjecttypec1 = getEobjecttypecSample1();
        Eobjecttypec eobjecttypec2 = new Eobjecttypec();
        assertThat(eobjecttypec1).isNotEqualTo(eobjecttypec2);

        eobjecttypec2.setId(eobjecttypec1.getId());
        assertThat(eobjecttypec1).isEqualTo(eobjecttypec2);

        eobjecttypec2 = getEobjecttypecSample2();
        assertThat(eobjecttypec1).isNotEqualTo(eobjecttypec2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Eobjecttypec eobjecttypec = new Eobjecttypec();
        assertThat(eobjecttypec.hashCode()).isZero();

        Eobjecttypec eobjecttypec1 = getEobjecttypecSample1();
        eobjecttypec.setId(eobjecttypec1.getId());
        assertThat(eobjecttypec).hasSameHashCodeAs(eobjecttypec1);
    }
}
