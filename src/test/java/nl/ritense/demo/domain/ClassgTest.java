package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClassgTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassgTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classg.class);
        Classg classg1 = getClassgSample1();
        Classg classg2 = new Classg();
        assertThat(classg1).isNotEqualTo(classg2);

        classg2.setId(classg1.getId());
        assertThat(classg1).isEqualTo(classg2);

        classg2 = getClassgSample2();
        assertThat(classg1).isNotEqualTo(classg2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Classg classg = new Classg();
        assertThat(classg.hashCode()).isZero();

        Classg classg1 = getClassgSample1();
        classg.setId(classg1.getId());
        assertThat(classg).hasSameHashCodeAs(classg1);
    }
}
