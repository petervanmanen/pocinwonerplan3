package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClasscTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClasscTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classc.class);
        Classc classc1 = getClasscSample1();
        Classc classc2 = new Classc();
        assertThat(classc1).isNotEqualTo(classc2);

        classc2.setId(classc1.getId());
        assertThat(classc1).isEqualTo(classc2);

        classc2 = getClasscSample2();
        assertThat(classc1).isNotEqualTo(classc2);
    }
}
