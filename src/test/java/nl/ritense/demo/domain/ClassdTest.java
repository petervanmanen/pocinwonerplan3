package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClassdTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassdTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classd.class);
        Classd classd1 = getClassdSample1();
        Classd classd2 = new Classd();
        assertThat(classd1).isNotEqualTo(classd2);

        classd2.setId(classd1.getId());
        assertThat(classd1).isEqualTo(classd2);

        classd2 = getClassdSample2();
        assertThat(classd1).isNotEqualTo(classd2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Classd classd = new Classd();
        assertThat(classd.hashCode()).isZero();

        Classd classd1 = getClassdSample1();
        classd.setId(classd1.getId());
        assertThat(classd).hasSameHashCodeAs(classd1);
    }
}
