package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EobjecttypefTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EobjecttypefTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eobjecttypef.class);
        Eobjecttypef eobjecttypef1 = getEobjecttypefSample1();
        Eobjecttypef eobjecttypef2 = new Eobjecttypef();
        assertThat(eobjecttypef1).isNotEqualTo(eobjecttypef2);

        eobjecttypef2.setId(eobjecttypef1.getId());
        assertThat(eobjecttypef1).isEqualTo(eobjecttypef2);

        eobjecttypef2 = getEobjecttypefSample2();
        assertThat(eobjecttypef1).isNotEqualTo(eobjecttypef2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Eobjecttypef eobjecttypef = new Eobjecttypef();
        assertThat(eobjecttypef.hashCode()).isZero();

        Eobjecttypef eobjecttypef1 = getEobjecttypefSample1();
        eobjecttypef.setId(eobjecttypef1.getId());
        assertThat(eobjecttypef).hasSameHashCodeAs(eobjecttypef1);
    }
}
