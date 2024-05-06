package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.Class1TestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class Class1Test {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Class1.class);
        Class1 class11 = getClass1Sample1();
        Class1 class12 = new Class1();
        assertThat(class11).isNotEqualTo(class12);

        class12.setId(class11.getId());
        assertThat(class11).isEqualTo(class12);

        class12 = getClass1Sample2();
        assertThat(class11).isNotEqualTo(class12);
    }
}
