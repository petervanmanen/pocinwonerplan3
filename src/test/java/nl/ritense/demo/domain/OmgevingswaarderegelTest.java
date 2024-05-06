package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OmgevingsnormTestSamples.*;
import static nl.ritense.demo.domain.OmgevingswaardeTestSamples.*;
import static nl.ritense.demo.domain.OmgevingswaarderegelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OmgevingswaarderegelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Omgevingswaarderegel.class);
        Omgevingswaarderegel omgevingswaarderegel1 = getOmgevingswaarderegelSample1();
        Omgevingswaarderegel omgevingswaarderegel2 = new Omgevingswaarderegel();
        assertThat(omgevingswaarderegel1).isNotEqualTo(omgevingswaarderegel2);

        omgevingswaarderegel2.setId(omgevingswaarderegel1.getId());
        assertThat(omgevingswaarderegel1).isEqualTo(omgevingswaarderegel2);

        omgevingswaarderegel2 = getOmgevingswaarderegelSample2();
        assertThat(omgevingswaarderegel1).isNotEqualTo(omgevingswaarderegel2);
    }

    @Test
    void beschrijftOmgevingsnormTest() throws Exception {
        Omgevingswaarderegel omgevingswaarderegel = getOmgevingswaarderegelRandomSampleGenerator();
        Omgevingsnorm omgevingsnormBack = getOmgevingsnormRandomSampleGenerator();

        omgevingswaarderegel.addBeschrijftOmgevingsnorm(omgevingsnormBack);
        assertThat(omgevingswaarderegel.getBeschrijftOmgevingsnorms()).containsOnly(omgevingsnormBack);

        omgevingswaarderegel.removeBeschrijftOmgevingsnorm(omgevingsnormBack);
        assertThat(omgevingswaarderegel.getBeschrijftOmgevingsnorms()).doesNotContain(omgevingsnormBack);

        omgevingswaarderegel.beschrijftOmgevingsnorms(new HashSet<>(Set.of(omgevingsnormBack)));
        assertThat(omgevingswaarderegel.getBeschrijftOmgevingsnorms()).containsOnly(omgevingsnormBack);

        omgevingswaarderegel.setBeschrijftOmgevingsnorms(new HashSet<>());
        assertThat(omgevingswaarderegel.getBeschrijftOmgevingsnorms()).doesNotContain(omgevingsnormBack);
    }

    @Test
    void beschrijftOmgevingswaardeTest() throws Exception {
        Omgevingswaarderegel omgevingswaarderegel = getOmgevingswaarderegelRandomSampleGenerator();
        Omgevingswaarde omgevingswaardeBack = getOmgevingswaardeRandomSampleGenerator();

        omgevingswaarderegel.addBeschrijftOmgevingswaarde(omgevingswaardeBack);
        assertThat(omgevingswaarderegel.getBeschrijftOmgevingswaardes()).containsOnly(omgevingswaardeBack);

        omgevingswaarderegel.removeBeschrijftOmgevingswaarde(omgevingswaardeBack);
        assertThat(omgevingswaarderegel.getBeschrijftOmgevingswaardes()).doesNotContain(omgevingswaardeBack);

        omgevingswaarderegel.beschrijftOmgevingswaardes(new HashSet<>(Set.of(omgevingswaardeBack)));
        assertThat(omgevingswaarderegel.getBeschrijftOmgevingswaardes()).containsOnly(omgevingswaardeBack);

        omgevingswaarderegel.setBeschrijftOmgevingswaardes(new HashSet<>());
        assertThat(omgevingswaarderegel.getBeschrijftOmgevingswaardes()).doesNotContain(omgevingswaardeBack);
    }
}
