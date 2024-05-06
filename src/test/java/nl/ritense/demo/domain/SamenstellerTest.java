package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SamenstellerTestSamples.*;
import static nl.ritense.demo.domain.TentoonstellingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SamenstellerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Samensteller.class);
        Samensteller samensteller1 = getSamenstellerSample1();
        Samensteller samensteller2 = new Samensteller();
        assertThat(samensteller1).isNotEqualTo(samensteller2);

        samensteller2.setId(samensteller1.getId());
        assertThat(samensteller1).isEqualTo(samensteller2);

        samensteller2 = getSamenstellerSample2();
        assertThat(samensteller1).isNotEqualTo(samensteller2);
    }

    @Test
    void steltsamenTentoonstellingTest() throws Exception {
        Samensteller samensteller = getSamenstellerRandomSampleGenerator();
        Tentoonstelling tentoonstellingBack = getTentoonstellingRandomSampleGenerator();

        samensteller.addSteltsamenTentoonstelling(tentoonstellingBack);
        assertThat(samensteller.getSteltsamenTentoonstellings()).containsOnly(tentoonstellingBack);

        samensteller.removeSteltsamenTentoonstelling(tentoonstellingBack);
        assertThat(samensteller.getSteltsamenTentoonstellings()).doesNotContain(tentoonstellingBack);

        samensteller.steltsamenTentoonstellings(new HashSet<>(Set.of(tentoonstellingBack)));
        assertThat(samensteller.getSteltsamenTentoonstellings()).containsOnly(tentoonstellingBack);

        samensteller.setSteltsamenTentoonstellings(new HashSet<>());
        assertThat(samensteller.getSteltsamenTentoonstellings()).doesNotContain(tentoonstellingBack);
    }
}
