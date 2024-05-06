package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ApplicatieTestSamples.*;
import static nl.ritense.demo.domain.MedewerkerTestSamples.*;
import static nl.ritense.demo.domain.NotitieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotitieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notitie.class);
        Notitie notitie1 = getNotitieSample1();
        Notitie notitie2 = new Notitie();
        assertThat(notitie1).isNotEqualTo(notitie2);

        notitie2.setId(notitie1.getId());
        assertThat(notitie1).isEqualTo(notitie2);

        notitie2 = getNotitieSample2();
        assertThat(notitie1).isNotEqualTo(notitie2);
    }

    @Test
    void auteurMedewerkerTest() throws Exception {
        Notitie notitie = getNotitieRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        notitie.setAuteurMedewerker(medewerkerBack);
        assertThat(notitie.getAuteurMedewerker()).isEqualTo(medewerkerBack);

        notitie.auteurMedewerker(null);
        assertThat(notitie.getAuteurMedewerker()).isNull();
    }

    @Test
    void heeftnotitiesApplicatieTest() throws Exception {
        Notitie notitie = getNotitieRandomSampleGenerator();
        Applicatie applicatieBack = getApplicatieRandomSampleGenerator();

        notitie.setHeeftnotitiesApplicatie(applicatieBack);
        assertThat(notitie.getHeeftnotitiesApplicatie()).isEqualTo(applicatieBack);

        notitie.heeftnotitiesApplicatie(null);
        assertThat(notitie.getHeeftnotitiesApplicatie()).isNull();
    }
}
