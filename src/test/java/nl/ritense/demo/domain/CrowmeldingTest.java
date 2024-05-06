package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.CrowmeldingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CrowmeldingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Crowmelding.class);
        Crowmelding crowmelding1 = getCrowmeldingSample1();
        Crowmelding crowmelding2 = new Crowmelding();
        assertThat(crowmelding1).isNotEqualTo(crowmelding2);

        crowmelding2.setId(crowmelding1.getId());
        assertThat(crowmelding1).isEqualTo(crowmelding2);

        crowmelding2 = getCrowmeldingSample2();
        assertThat(crowmelding1).isNotEqualTo(crowmelding2);
    }
}
