package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EobjecttypeeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EobjecttypeeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eobjecttypee.class);
        Eobjecttypee eobjecttypee1 = getEobjecttypeeSample1();
        Eobjecttypee eobjecttypee2 = new Eobjecttypee();
        assertThat(eobjecttypee1).isNotEqualTo(eobjecttypee2);

        eobjecttypee2.setId(eobjecttypee1.getId());
        assertThat(eobjecttypee1).isEqualTo(eobjecttypee2);

        eobjecttypee2 = getEobjecttypeeSample2();
        assertThat(eobjecttypee1).isNotEqualTo(eobjecttypee2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Eobjecttypee eobjecttypee = new Eobjecttypee();
        assertThat(eobjecttypee.hashCode()).isZero();

        Eobjecttypee eobjecttypee1 = getEobjecttypeeSample1();
        eobjecttypee.setId(eobjecttypee1.getId());
        assertThat(eobjecttypee).hasSameHashCodeAs(eobjecttypee1);
    }
}
