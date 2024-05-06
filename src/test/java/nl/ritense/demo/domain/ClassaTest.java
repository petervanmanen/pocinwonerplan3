package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClassaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classa.class);
        Classa classa1 = getClassaSample1();
        Classa classa2 = new Classa();
        assertThat(classa1).isNotEqualTo(classa2);

        classa2.setId(classa1.getId());
        assertThat(classa1).isEqualTo(classa2);

        classa2 = getClassaSample2();
        assertThat(classa1).isNotEqualTo(classa2);
    }
}
