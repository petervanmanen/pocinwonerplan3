package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OmgevingsnormTestSamples.*;
import static nl.ritense.demo.domain.OmgevingswaarderegelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OmgevingsnormTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Omgevingsnorm.class);
        Omgevingsnorm omgevingsnorm1 = getOmgevingsnormSample1();
        Omgevingsnorm omgevingsnorm2 = new Omgevingsnorm();
        assertThat(omgevingsnorm1).isNotEqualTo(omgevingsnorm2);

        omgevingsnorm2.setId(omgevingsnorm1.getId());
        assertThat(omgevingsnorm1).isEqualTo(omgevingsnorm2);

        omgevingsnorm2 = getOmgevingsnormSample2();
        assertThat(omgevingsnorm1).isNotEqualTo(omgevingsnorm2);
    }

    @Test
    void beschrijftOmgevingswaarderegelTest() throws Exception {
        Omgevingsnorm omgevingsnorm = getOmgevingsnormRandomSampleGenerator();
        Omgevingswaarderegel omgevingswaarderegelBack = getOmgevingswaarderegelRandomSampleGenerator();

        omgevingsnorm.addBeschrijftOmgevingswaarderegel(omgevingswaarderegelBack);
        assertThat(omgevingsnorm.getBeschrijftOmgevingswaarderegels()).containsOnly(omgevingswaarderegelBack);
        assertThat(omgevingswaarderegelBack.getBeschrijftOmgevingsnorms()).containsOnly(omgevingsnorm);

        omgevingsnorm.removeBeschrijftOmgevingswaarderegel(omgevingswaarderegelBack);
        assertThat(omgevingsnorm.getBeschrijftOmgevingswaarderegels()).doesNotContain(omgevingswaarderegelBack);
        assertThat(omgevingswaarderegelBack.getBeschrijftOmgevingsnorms()).doesNotContain(omgevingsnorm);

        omgevingsnorm.beschrijftOmgevingswaarderegels(new HashSet<>(Set.of(omgevingswaarderegelBack)));
        assertThat(omgevingsnorm.getBeschrijftOmgevingswaarderegels()).containsOnly(omgevingswaarderegelBack);
        assertThat(omgevingswaarderegelBack.getBeschrijftOmgevingsnorms()).containsOnly(omgevingsnorm);

        omgevingsnorm.setBeschrijftOmgevingswaarderegels(new HashSet<>());
        assertThat(omgevingsnorm.getBeschrijftOmgevingswaarderegels()).doesNotContain(omgevingswaarderegelBack);
        assertThat(omgevingswaarderegelBack.getBeschrijftOmgevingsnorms()).doesNotContain(omgevingsnorm);
    }
}
