package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EobjecttypeaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EobjecttypeaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eobjecttypea.class);
        Eobjecttypea eobjecttypea1 = getEobjecttypeaSample1();
        Eobjecttypea eobjecttypea2 = new Eobjecttypea();
        assertThat(eobjecttypea1).isNotEqualTo(eobjecttypea2);

        eobjecttypea2.setId(eobjecttypea1.getId());
        assertThat(eobjecttypea1).isEqualTo(eobjecttypea2);

        eobjecttypea2 = getEobjecttypeaSample2();
        assertThat(eobjecttypea1).isNotEqualTo(eobjecttypea2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Eobjecttypea eobjecttypea = new Eobjecttypea();
        assertThat(eobjecttypea.hashCode()).isZero();

        Eobjecttypea eobjecttypea1 = getEobjecttypeaSample1();
        eobjecttypea.setId(eobjecttypea1.getId());
        assertThat(eobjecttypea).hasSameHashCodeAs(eobjecttypea1);
    }
}
