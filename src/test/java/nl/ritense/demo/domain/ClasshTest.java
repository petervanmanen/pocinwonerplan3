package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClasshTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClasshTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classh.class);
        Classh classh1 = getClasshSample1();
        Classh classh2 = new Classh();
        assertThat(classh1).isNotEqualTo(classh2);

        classh2.setId(classh1.getId());
        assertThat(classh1).isEqualTo(classh2);

        classh2 = getClasshSample2();
        assertThat(classh1).isNotEqualTo(classh2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Classh classh = new Classh();
        assertThat(classh.hashCode()).isZero();

        Classh classh1 = getClasshSample1();
        classh.setId(classh1.getId());
        assertThat(classh).hasSameHashCodeAs(classh1);
    }
}
