package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LocatieTestSamples.*;
import static nl.ritense.demo.domain.NormTestSamples.*;
import static nl.ritense.demo.domain.NormwaardeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NormwaardeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Normwaarde.class);
        Normwaarde normwaarde1 = getNormwaardeSample1();
        Normwaarde normwaarde2 = new Normwaarde();
        assertThat(normwaarde1).isNotEqualTo(normwaarde2);

        normwaarde2.setId(normwaarde1.getId());
        assertThat(normwaarde1).isEqualTo(normwaarde2);

        normwaarde2 = getNormwaardeSample2();
        assertThat(normwaarde1).isNotEqualTo(normwaarde2);
    }

    @Test
    void geldtvoorLocatieTest() throws Exception {
        Normwaarde normwaarde = getNormwaardeRandomSampleGenerator();
        Locatie locatieBack = getLocatieRandomSampleGenerator();

        normwaarde.addGeldtvoorLocatie(locatieBack);
        assertThat(normwaarde.getGeldtvoorLocaties()).containsOnly(locatieBack);

        normwaarde.removeGeldtvoorLocatie(locatieBack);
        assertThat(normwaarde.getGeldtvoorLocaties()).doesNotContain(locatieBack);

        normwaarde.geldtvoorLocaties(new HashSet<>(Set.of(locatieBack)));
        assertThat(normwaarde.getGeldtvoorLocaties()).containsOnly(locatieBack);

        normwaarde.setGeldtvoorLocaties(new HashSet<>());
        assertThat(normwaarde.getGeldtvoorLocaties()).doesNotContain(locatieBack);
    }

    @Test
    void bevatNormTest() throws Exception {
        Normwaarde normwaarde = getNormwaardeRandomSampleGenerator();
        Norm normBack = getNormRandomSampleGenerator();

        normwaarde.setBevatNorm(normBack);
        assertThat(normwaarde.getBevatNorm()).isEqualTo(normBack);

        normwaarde.bevatNorm(null);
        assertThat(normwaarde.getBevatNorm()).isNull();
    }
}
