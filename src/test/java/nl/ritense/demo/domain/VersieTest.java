package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ApplicatieTestSamples.*;
import static nl.ritense.demo.domain.VersieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VersieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Versie.class);
        Versie versie1 = getVersieSample1();
        Versie versie2 = new Versie();
        assertThat(versie1).isNotEqualTo(versie2);

        versie2.setId(versie1.getId());
        assertThat(versie1).isEqualTo(versie2);

        versie2 = getVersieSample2();
        assertThat(versie1).isNotEqualTo(versie2);
    }

    @Test
    void heeftversiesApplicatieTest() throws Exception {
        Versie versie = getVersieRandomSampleGenerator();
        Applicatie applicatieBack = getApplicatieRandomSampleGenerator();

        versie.setHeeftversiesApplicatie(applicatieBack);
        assertThat(versie.getHeeftversiesApplicatie()).isEqualTo(applicatieBack);

        versie.heeftversiesApplicatie(null);
        assertThat(versie.getHeeftversiesApplicatie()).isNull();
    }
}
