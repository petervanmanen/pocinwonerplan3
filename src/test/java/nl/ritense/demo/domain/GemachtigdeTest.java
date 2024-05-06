package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GemachtigdeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GemachtigdeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gemachtigde.class);
        Gemachtigde gemachtigde1 = getGemachtigdeSample1();
        Gemachtigde gemachtigde2 = new Gemachtigde();
        assertThat(gemachtigde1).isNotEqualTo(gemachtigde2);

        gemachtigde2.setId(gemachtigde1.getId());
        assertThat(gemachtigde1).isEqualTo(gemachtigde2);

        gemachtigde2 = getGemachtigdeSample2();
        assertThat(gemachtigde1).isNotEqualTo(gemachtigde2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Gemachtigde gemachtigde = new Gemachtigde();
        assertThat(gemachtigde.hashCode()).isZero();

        Gemachtigde gemachtigde1 = getGemachtigdeSample1();
        gemachtigde.setId(gemachtigde1.getId());
        assertThat(gemachtigde).hasSameHashCodeAs(gemachtigde1);
    }
}
