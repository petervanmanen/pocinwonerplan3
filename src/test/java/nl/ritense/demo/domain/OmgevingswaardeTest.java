package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OmgevingswaardeTestSamples.*;
import static nl.ritense.demo.domain.OmgevingswaarderegelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OmgevingswaardeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Omgevingswaarde.class);
        Omgevingswaarde omgevingswaarde1 = getOmgevingswaardeSample1();
        Omgevingswaarde omgevingswaarde2 = new Omgevingswaarde();
        assertThat(omgevingswaarde1).isNotEqualTo(omgevingswaarde2);

        omgevingswaarde2.setId(omgevingswaarde1.getId());
        assertThat(omgevingswaarde1).isEqualTo(omgevingswaarde2);

        omgevingswaarde2 = getOmgevingswaardeSample2();
        assertThat(omgevingswaarde1).isNotEqualTo(omgevingswaarde2);
    }

    @Test
    void beschrijftOmgevingswaarderegelTest() throws Exception {
        Omgevingswaarde omgevingswaarde = getOmgevingswaardeRandomSampleGenerator();
        Omgevingswaarderegel omgevingswaarderegelBack = getOmgevingswaarderegelRandomSampleGenerator();

        omgevingswaarde.addBeschrijftOmgevingswaarderegel(omgevingswaarderegelBack);
        assertThat(omgevingswaarde.getBeschrijftOmgevingswaarderegels()).containsOnly(omgevingswaarderegelBack);
        assertThat(omgevingswaarderegelBack.getBeschrijftOmgevingswaardes()).containsOnly(omgevingswaarde);

        omgevingswaarde.removeBeschrijftOmgevingswaarderegel(omgevingswaarderegelBack);
        assertThat(omgevingswaarde.getBeschrijftOmgevingswaarderegels()).doesNotContain(omgevingswaarderegelBack);
        assertThat(omgevingswaarderegelBack.getBeschrijftOmgevingswaardes()).doesNotContain(omgevingswaarde);

        omgevingswaarde.beschrijftOmgevingswaarderegels(new HashSet<>(Set.of(omgevingswaarderegelBack)));
        assertThat(omgevingswaarde.getBeschrijftOmgevingswaarderegels()).containsOnly(omgevingswaarderegelBack);
        assertThat(omgevingswaarderegelBack.getBeschrijftOmgevingswaardes()).containsOnly(omgevingswaarde);

        omgevingswaarde.setBeschrijftOmgevingswaarderegels(new HashSet<>());
        assertThat(omgevingswaarde.getBeschrijftOmgevingswaarderegels()).doesNotContain(omgevingswaarderegelBack);
        assertThat(omgevingswaarderegelBack.getBeschrijftOmgevingswaardes()).doesNotContain(omgevingswaarde);
    }
}
