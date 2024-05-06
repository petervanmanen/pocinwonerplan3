package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SociaalteamdossierTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SociaalteamdossierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sociaalteamdossier.class);
        Sociaalteamdossier sociaalteamdossier1 = getSociaalteamdossierSample1();
        Sociaalteamdossier sociaalteamdossier2 = new Sociaalteamdossier();
        assertThat(sociaalteamdossier1).isNotEqualTo(sociaalteamdossier2);

        sociaalteamdossier2.setId(sociaalteamdossier1.getId());
        assertThat(sociaalteamdossier1).isEqualTo(sociaalteamdossier2);

        sociaalteamdossier2 = getSociaalteamdossierSample2();
        assertThat(sociaalteamdossier1).isNotEqualTo(sociaalteamdossier2);
    }
}
