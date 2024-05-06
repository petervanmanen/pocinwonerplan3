package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BezettingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BezettingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bezetting.class);
        Bezetting bezetting1 = getBezettingSample1();
        Bezetting bezetting2 = new Bezetting();
        assertThat(bezetting1).isNotEqualTo(bezetting2);

        bezetting2.setId(bezetting1.getId());
        assertThat(bezetting1).isEqualTo(bezetting2);

        bezetting2 = getBezettingSample2();
        assertThat(bezetting1).isNotEqualTo(bezetting2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Bezetting bezetting = new Bezetting();
        assertThat(bezetting.hashCode()).isZero();

        Bezetting bezetting1 = getBezettingSample1();
        bezetting.setId(bezetting1.getId());
        assertThat(bezetting).hasSameHashCodeAs(bezetting1);
    }
}
