package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClassfTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassfTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classf.class);
        Classf classf1 = getClassfSample1();
        Classf classf2 = new Classf();
        assertThat(classf1).isNotEqualTo(classf2);

        classf2.setId(classf1.getId());
        assertThat(classf1).isEqualTo(classf2);

        classf2 = getClassfSample2();
        assertThat(classf1).isNotEqualTo(classf2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Classf classf = new Classf();
        assertThat(classf.hashCode()).isZero();

        Classf classf1 = getClassfSample1();
        classf.setId(classf1.getId());
        assertThat(classf).hasSameHashCodeAs(classf1);
    }
}
