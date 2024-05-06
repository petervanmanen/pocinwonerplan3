package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.InkomensvoorzieningTestSamples.*;
import static nl.ritense.demo.domain.InkomensvoorzieningsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InkomensvoorzieningsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inkomensvoorzieningsoort.class);
        Inkomensvoorzieningsoort inkomensvoorzieningsoort1 = getInkomensvoorzieningsoortSample1();
        Inkomensvoorzieningsoort inkomensvoorzieningsoort2 = new Inkomensvoorzieningsoort();
        assertThat(inkomensvoorzieningsoort1).isNotEqualTo(inkomensvoorzieningsoort2);

        inkomensvoorzieningsoort2.setId(inkomensvoorzieningsoort1.getId());
        assertThat(inkomensvoorzieningsoort1).isEqualTo(inkomensvoorzieningsoort2);

        inkomensvoorzieningsoort2 = getInkomensvoorzieningsoortSample2();
        assertThat(inkomensvoorzieningsoort1).isNotEqualTo(inkomensvoorzieningsoort2);
    }

    @Test
    void issoortvoorzieningInkomensvoorzieningTest() throws Exception {
        Inkomensvoorzieningsoort inkomensvoorzieningsoort = getInkomensvoorzieningsoortRandomSampleGenerator();
        Inkomensvoorziening inkomensvoorzieningBack = getInkomensvoorzieningRandomSampleGenerator();

        inkomensvoorzieningsoort.addIssoortvoorzieningInkomensvoorziening(inkomensvoorzieningBack);
        assertThat(inkomensvoorzieningsoort.getIssoortvoorzieningInkomensvoorzienings()).containsOnly(inkomensvoorzieningBack);
        assertThat(inkomensvoorzieningBack.getIssoortvoorzieningInkomensvoorzieningsoort()).isEqualTo(inkomensvoorzieningsoort);

        inkomensvoorzieningsoort.removeIssoortvoorzieningInkomensvoorziening(inkomensvoorzieningBack);
        assertThat(inkomensvoorzieningsoort.getIssoortvoorzieningInkomensvoorzienings()).doesNotContain(inkomensvoorzieningBack);
        assertThat(inkomensvoorzieningBack.getIssoortvoorzieningInkomensvoorzieningsoort()).isNull();

        inkomensvoorzieningsoort.issoortvoorzieningInkomensvoorzienings(new HashSet<>(Set.of(inkomensvoorzieningBack)));
        assertThat(inkomensvoorzieningsoort.getIssoortvoorzieningInkomensvoorzienings()).containsOnly(inkomensvoorzieningBack);
        assertThat(inkomensvoorzieningBack.getIssoortvoorzieningInkomensvoorzieningsoort()).isEqualTo(inkomensvoorzieningsoort);

        inkomensvoorzieningsoort.setIssoortvoorzieningInkomensvoorzienings(new HashSet<>());
        assertThat(inkomensvoorzieningsoort.getIssoortvoorzieningInkomensvoorzienings()).doesNotContain(inkomensvoorzieningBack);
        assertThat(inkomensvoorzieningBack.getIssoortvoorzieningInkomensvoorzieningsoort()).isNull();
    }
}
