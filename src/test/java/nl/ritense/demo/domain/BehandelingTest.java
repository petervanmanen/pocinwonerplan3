package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BehandelingTestSamples.*;
import static nl.ritense.demo.domain.BehandelsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BehandelingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Behandeling.class);
        Behandeling behandeling1 = getBehandelingSample1();
        Behandeling behandeling2 = new Behandeling();
        assertThat(behandeling1).isNotEqualTo(behandeling2);

        behandeling2.setId(behandeling1.getId());
        assertThat(behandeling1).isEqualTo(behandeling2);

        behandeling2 = getBehandelingSample2();
        assertThat(behandeling1).isNotEqualTo(behandeling2);
    }

    @Test
    void isvansoortBehandelsoortTest() throws Exception {
        Behandeling behandeling = getBehandelingRandomSampleGenerator();
        Behandelsoort behandelsoortBack = getBehandelsoortRandomSampleGenerator();

        behandeling.setIsvansoortBehandelsoort(behandelsoortBack);
        assertThat(behandeling.getIsvansoortBehandelsoort()).isEqualTo(behandelsoortBack);

        behandeling.isvansoortBehandelsoort(null);
        assertThat(behandeling.getIsvansoortBehandelsoort()).isNull();
    }
}
