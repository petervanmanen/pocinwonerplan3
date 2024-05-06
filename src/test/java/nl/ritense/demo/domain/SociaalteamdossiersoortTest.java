package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SociaalteamdossiersoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SociaalteamdossiersoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sociaalteamdossiersoort.class);
        Sociaalteamdossiersoort sociaalteamdossiersoort1 = getSociaalteamdossiersoortSample1();
        Sociaalteamdossiersoort sociaalteamdossiersoort2 = new Sociaalteamdossiersoort();
        assertThat(sociaalteamdossiersoort1).isNotEqualTo(sociaalteamdossiersoort2);

        sociaalteamdossiersoort2.setId(sociaalteamdossiersoort1.getId());
        assertThat(sociaalteamdossiersoort1).isEqualTo(sociaalteamdossiersoort2);

        sociaalteamdossiersoort2 = getSociaalteamdossiersoortSample2();
        assertThat(sociaalteamdossiersoort1).isNotEqualTo(sociaalteamdossiersoort2);
    }
}
