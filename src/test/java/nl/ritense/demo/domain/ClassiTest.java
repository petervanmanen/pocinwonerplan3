package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClassiTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassiTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classi.class);
        Classi classi1 = getClassiSample1();
        Classi classi2 = new Classi();
        assertThat(classi1).isNotEqualTo(classi2);

        classi2.setId(classi1.getId());
        assertThat(classi1).isEqualTo(classi2);

        classi2 = getClassiSample2();
        assertThat(classi1).isNotEqualTo(classi2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Classi classi = new Classi();
        assertThat(classi.hashCode()).isZero();

        Classi classi1 = getClassiSample1();
        classi.setId(classi1.getId());
        assertThat(classi).hasSameHashCodeAs(classi1);
    }
}
