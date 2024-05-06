package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BehandelingTestSamples.*;
import static nl.ritense.demo.domain.BehandelsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BehandelsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Behandelsoort.class);
        Behandelsoort behandelsoort1 = getBehandelsoortSample1();
        Behandelsoort behandelsoort2 = new Behandelsoort();
        assertThat(behandelsoort1).isNotEqualTo(behandelsoort2);

        behandelsoort2.setId(behandelsoort1.getId());
        assertThat(behandelsoort1).isEqualTo(behandelsoort2);

        behandelsoort2 = getBehandelsoortSample2();
        assertThat(behandelsoort1).isNotEqualTo(behandelsoort2);
    }

    @Test
    void isvansoortBehandelingTest() throws Exception {
        Behandelsoort behandelsoort = getBehandelsoortRandomSampleGenerator();
        Behandeling behandelingBack = getBehandelingRandomSampleGenerator();

        behandelsoort.addIsvansoortBehandeling(behandelingBack);
        assertThat(behandelsoort.getIsvansoortBehandelings()).containsOnly(behandelingBack);
        assertThat(behandelingBack.getIsvansoortBehandelsoort()).isEqualTo(behandelsoort);

        behandelsoort.removeIsvansoortBehandeling(behandelingBack);
        assertThat(behandelsoort.getIsvansoortBehandelings()).doesNotContain(behandelingBack);
        assertThat(behandelingBack.getIsvansoortBehandelsoort()).isNull();

        behandelsoort.isvansoortBehandelings(new HashSet<>(Set.of(behandelingBack)));
        assertThat(behandelsoort.getIsvansoortBehandelings()).containsOnly(behandelingBack);
        assertThat(behandelingBack.getIsvansoortBehandelsoort()).isEqualTo(behandelsoort);

        behandelsoort.setIsvansoortBehandelings(new HashSet<>());
        assertThat(behandelsoort.getIsvansoortBehandelings()).doesNotContain(behandelingBack);
        assertThat(behandelingBack.getIsvansoortBehandelsoort()).isNull();
    }
}
