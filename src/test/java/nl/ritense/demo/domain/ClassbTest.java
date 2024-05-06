package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClassbTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassbTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classb.class);
        Classb classb1 = getClassbSample1();
        Classb classb2 = new Classb();
        assertThat(classb1).isNotEqualTo(classb2);

        classb2.setId(classb1.getId());
        assertThat(classb1).isEqualTo(classb2);

        classb2 = getClassbSample2();
        assertThat(classb1).isNotEqualTo(classb2);
    }
}
