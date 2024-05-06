package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ApplicatieTestSamples.*;
import static nl.ritense.demo.domain.ClassificatieTestSamples.*;
import static nl.ritense.demo.domain.GegevenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GegevenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gegeven.class);
        Gegeven gegeven1 = getGegevenSample1();
        Gegeven gegeven2 = new Gegeven();
        assertThat(gegeven1).isNotEqualTo(gegeven2);

        gegeven2.setId(gegeven1.getId());
        assertThat(gegeven1).isEqualTo(gegeven2);

        gegeven2 = getGegevenSample2();
        assertThat(gegeven1).isNotEqualTo(gegeven2);
    }

    @Test
    void geclassificeerdalsClassificatieTest() throws Exception {
        Gegeven gegeven = getGegevenRandomSampleGenerator();
        Classificatie classificatieBack = getClassificatieRandomSampleGenerator();

        gegeven.addGeclassificeerdalsClassificatie(classificatieBack);
        assertThat(gegeven.getGeclassificeerdalsClassificaties()).containsOnly(classificatieBack);

        gegeven.removeGeclassificeerdalsClassificatie(classificatieBack);
        assertThat(gegeven.getGeclassificeerdalsClassificaties()).doesNotContain(classificatieBack);

        gegeven.geclassificeerdalsClassificaties(new HashSet<>(Set.of(classificatieBack)));
        assertThat(gegeven.getGeclassificeerdalsClassificaties()).containsOnly(classificatieBack);

        gegeven.setGeclassificeerdalsClassificaties(new HashSet<>());
        assertThat(gegeven.getGeclassificeerdalsClassificaties()).doesNotContain(classificatieBack);
    }

    @Test
    void bevatApplicatieTest() throws Exception {
        Gegeven gegeven = getGegevenRandomSampleGenerator();
        Applicatie applicatieBack = getApplicatieRandomSampleGenerator();

        gegeven.setBevatApplicatie(applicatieBack);
        assertThat(gegeven.getBevatApplicatie()).isEqualTo(applicatieBack);

        gegeven.bevatApplicatie(null);
        assertThat(gegeven.getBevatApplicatie()).isNull();
    }
}
