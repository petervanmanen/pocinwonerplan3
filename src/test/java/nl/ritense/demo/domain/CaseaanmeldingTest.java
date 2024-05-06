package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.CaseaanmeldingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CaseaanmeldingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Caseaanmelding.class);
        Caseaanmelding caseaanmelding1 = getCaseaanmeldingSample1();
        Caseaanmelding caseaanmelding2 = new Caseaanmelding();
        assertThat(caseaanmelding1).isNotEqualTo(caseaanmelding2);

        caseaanmelding2.setId(caseaanmelding1.getId());
        assertThat(caseaanmelding1).isEqualTo(caseaanmelding2);

        caseaanmelding2 = getCaseaanmeldingSample2();
        assertThat(caseaanmelding1).isNotEqualTo(caseaanmelding2);
    }
}
