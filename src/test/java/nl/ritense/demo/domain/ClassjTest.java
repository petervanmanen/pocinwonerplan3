package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClassjTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassjTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classj.class);
        Classj classj1 = getClassjSample1();
        Classj classj2 = new Classj();
        assertThat(classj1).isNotEqualTo(classj2);

        classj2.setId(classj1.getId());
        assertThat(classj1).isEqualTo(classj2);

        classj2 = getClassjSample2();
        assertThat(classj1).isNotEqualTo(classj2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Classj classj = new Classj();
        assertThat(classj.hashCode()).isZero();

        Classj classj1 = getClassjSample1();
        classj.setId(classj1.getId());
        assertThat(classj).hasSameHashCodeAs(classj1);
    }
}
