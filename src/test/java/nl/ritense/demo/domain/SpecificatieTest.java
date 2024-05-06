package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ProjectactiviteitTestSamples.*;
import static nl.ritense.demo.domain.SpecificatieTestSamples.*;
import static nl.ritense.demo.domain.VerzoekTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpecificatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Specificatie.class);
        Specificatie specificatie1 = getSpecificatieSample1();
        Specificatie specificatie2 = new Specificatie();
        assertThat(specificatie1).isNotEqualTo(specificatie2);

        specificatie2.setId(specificatie1.getId());
        assertThat(specificatie1).isEqualTo(specificatie2);

        specificatie2 = getSpecificatieSample2();
        assertThat(specificatie1).isNotEqualTo(specificatie2);
    }

    @Test
    void gedefinieerddoorProjectactiviteitTest() throws Exception {
        Specificatie specificatie = getSpecificatieRandomSampleGenerator();
        Projectactiviteit projectactiviteitBack = getProjectactiviteitRandomSampleGenerator();

        specificatie.setGedefinieerddoorProjectactiviteit(projectactiviteitBack);
        assertThat(specificatie.getGedefinieerddoorProjectactiviteit()).isEqualTo(projectactiviteitBack);

        specificatie.gedefinieerddoorProjectactiviteit(null);
        assertThat(specificatie.getGedefinieerddoorProjectactiviteit()).isNull();
    }

    @Test
    void bevatVerzoekTest() throws Exception {
        Specificatie specificatie = getSpecificatieRandomSampleGenerator();
        Verzoek verzoekBack = getVerzoekRandomSampleGenerator();

        specificatie.setBevatVerzoek(verzoekBack);
        assertThat(specificatie.getBevatVerzoek()).isEqualTo(verzoekBack);

        specificatie.bevatVerzoek(null);
        assertThat(specificatie.getBevatVerzoek()).isNull();
    }
}
