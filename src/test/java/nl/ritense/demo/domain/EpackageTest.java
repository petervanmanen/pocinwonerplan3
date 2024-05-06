package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EpackageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EpackageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Epackage.class);
        Epackage epackage1 = getEpackageSample1();
        Epackage epackage2 = new Epackage();
        assertThat(epackage1).isNotEqualTo(epackage2);

        epackage2.setId(epackage1.getId());
        assertThat(epackage1).isEqualTo(epackage2);

        epackage2 = getEpackageSample2();
        assertThat(epackage1).isNotEqualTo(epackage2);
    }
}
