package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BrondocumentenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BrondocumentenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Brondocumenten.class);
        Brondocumenten brondocumenten1 = getBrondocumentenSample1();
        Brondocumenten brondocumenten2 = new Brondocumenten();
        assertThat(brondocumenten1).isNotEqualTo(brondocumenten2);

        brondocumenten2.setId(brondocumenten1.getId());
        assertThat(brondocumenten1).isEqualTo(brondocumenten2);

        brondocumenten2 = getBrondocumentenSample2();
        assertThat(brondocumenten1).isNotEqualTo(brondocumenten2);
    }
}
