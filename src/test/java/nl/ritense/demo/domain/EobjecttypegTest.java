package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EobjecttypegTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EobjecttypegTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eobjecttypeg.class);
        Eobjecttypeg eobjecttypeg1 = getEobjecttypegSample1();
        Eobjecttypeg eobjecttypeg2 = new Eobjecttypeg();
        assertThat(eobjecttypeg1).isNotEqualTo(eobjecttypeg2);

        eobjecttypeg2.setId(eobjecttypeg1.getId());
        assertThat(eobjecttypeg1).isEqualTo(eobjecttypeg2);

        eobjecttypeg2 = getEobjecttypegSample2();
        assertThat(eobjecttypeg1).isNotEqualTo(eobjecttypeg2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Eobjecttypeg eobjecttypeg = new Eobjecttypeg();
        assertThat(eobjecttypeg.hashCode()).isZero();

        Eobjecttypeg eobjecttypeg1 = getEobjecttypegSample1();
        eobjecttypeg.setId(eobjecttypeg1.getId());
        assertThat(eobjecttypeg).hasSameHashCodeAs(eobjecttypeg1);
    }
}
