package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AcademischetitelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AcademischetitelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Academischetitel.class);
        Academischetitel academischetitel1 = getAcademischetitelSample1();
        Academischetitel academischetitel2 = new Academischetitel();
        assertThat(academischetitel1).isNotEqualTo(academischetitel2);

        academischetitel2.setId(academischetitel1.getId());
        assertThat(academischetitel1).isEqualTo(academischetitel2);

        academischetitel2 = getAcademischetitelSample2();
        assertThat(academischetitel1).isNotEqualTo(academischetitel2);
    }
}
