package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EnumenumerationaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EnumenumerationaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Enumenumerationa.class);
        Enumenumerationa enumenumerationa1 = getEnumenumerationaSample1();
        Enumenumerationa enumenumerationa2 = new Enumenumerationa();
        assertThat(enumenumerationa1).isNotEqualTo(enumenumerationa2);

        enumenumerationa2.setId(enumenumerationa1.getId());
        assertThat(enumenumerationa1).isEqualTo(enumenumerationa2);

        enumenumerationa2 = getEnumenumerationaSample2();
        assertThat(enumenumerationa1).isNotEqualTo(enumenumerationa2);
    }
}
