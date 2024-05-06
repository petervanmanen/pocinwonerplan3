package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.NormTestSamples.*;
import static nl.ritense.demo.domain.NormwaardeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NormTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Norm.class);
        Norm norm1 = getNormSample1();
        Norm norm2 = new Norm();
        assertThat(norm1).isNotEqualTo(norm2);

        norm2.setId(norm1.getId());
        assertThat(norm1).isEqualTo(norm2);

        norm2 = getNormSample2();
        assertThat(norm1).isNotEqualTo(norm2);
    }

    @Test
    void bevatNormwaardeTest() throws Exception {
        Norm norm = getNormRandomSampleGenerator();
        Normwaarde normwaardeBack = getNormwaardeRandomSampleGenerator();

        norm.addBevatNormwaarde(normwaardeBack);
        assertThat(norm.getBevatNormwaardes()).containsOnly(normwaardeBack);
        assertThat(normwaardeBack.getBevatNorm()).isEqualTo(norm);

        norm.removeBevatNormwaarde(normwaardeBack);
        assertThat(norm.getBevatNormwaardes()).doesNotContain(normwaardeBack);
        assertThat(normwaardeBack.getBevatNorm()).isNull();

        norm.bevatNormwaardes(new HashSet<>(Set.of(normwaardeBack)));
        assertThat(norm.getBevatNormwaardes()).containsOnly(normwaardeBack);
        assertThat(normwaardeBack.getBevatNorm()).isEqualTo(norm);

        norm.setBevatNormwaardes(new HashSet<>());
        assertThat(norm.getBevatNormwaardes()).doesNotContain(normwaardeBack);
        assertThat(normwaardeBack.getBevatNorm()).isNull();
    }
}
