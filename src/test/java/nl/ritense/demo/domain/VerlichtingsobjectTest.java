package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerlichtingsobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerlichtingsobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verlichtingsobject.class);
        Verlichtingsobject verlichtingsobject1 = getVerlichtingsobjectSample1();
        Verlichtingsobject verlichtingsobject2 = new Verlichtingsobject();
        assertThat(verlichtingsobject1).isNotEqualTo(verlichtingsobject2);

        verlichtingsobject2.setId(verlichtingsobject1.getId());
        assertThat(verlichtingsobject1).isEqualTo(verlichtingsobject2);

        verlichtingsobject2 = getVerlichtingsobjectSample2();
        assertThat(verlichtingsobject1).isNotEqualTo(verlichtingsobject2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Verlichtingsobject verlichtingsobject = new Verlichtingsobject();
        assertThat(verlichtingsobject.hashCode()).isZero();

        Verlichtingsobject verlichtingsobject1 = getVerlichtingsobjectSample1();
        verlichtingsobject.setId(verlichtingsobject1.getId());
        assertThat(verlichtingsobject).hasSameHashCodeAs(verlichtingsobject1);
    }
}
