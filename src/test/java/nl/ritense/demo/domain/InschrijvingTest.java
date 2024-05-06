package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanbestedingTestSamples.*;
import static nl.ritense.demo.domain.GunningTestSamples.*;
import static nl.ritense.demo.domain.InschrijvingTestSamples.*;
import static nl.ritense.demo.domain.LeerlingTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.SchoolTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InschrijvingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inschrijving.class);
        Inschrijving inschrijving1 = getInschrijvingSample1();
        Inschrijving inschrijving2 = new Inschrijving();
        assertThat(inschrijving1).isNotEqualTo(inschrijving2);

        inschrijving2.setId(inschrijving1.getId());
        assertThat(inschrijving1).isEqualTo(inschrijving2);

        inschrijving2 = getInschrijvingSample2();
        assertThat(inschrijving1).isNotEqualTo(inschrijving2);
    }

    @Test
    void heeftSchoolTest() throws Exception {
        Inschrijving inschrijving = getInschrijvingRandomSampleGenerator();
        School schoolBack = getSchoolRandomSampleGenerator();

        inschrijving.setHeeftSchool(schoolBack);
        assertThat(inschrijving.getHeeftSchool()).isEqualTo(schoolBack);

        inschrijving.heeftSchool(null);
        assertThat(inschrijving.getHeeftSchool()).isNull();
    }

    @Test
    void betreftAanbestedingTest() throws Exception {
        Inschrijving inschrijving = getInschrijvingRandomSampleGenerator();
        Aanbesteding aanbestedingBack = getAanbestedingRandomSampleGenerator();

        inschrijving.setBetreftAanbesteding(aanbestedingBack);
        assertThat(inschrijving.getBetreftAanbesteding()).isEqualTo(aanbestedingBack);

        inschrijving.betreftAanbesteding(null);
        assertThat(inschrijving.getBetreftAanbesteding()).isNull();
    }

    @Test
    void betreftGunningTest() throws Exception {
        Inschrijving inschrijving = getInschrijvingRandomSampleGenerator();
        Gunning gunningBack = getGunningRandomSampleGenerator();

        inschrijving.setBetreftGunning(gunningBack);
        assertThat(inschrijving.getBetreftGunning()).isEqualTo(gunningBack);
        assertThat(gunningBack.getBetreftInschrijving()).isEqualTo(inschrijving);

        inschrijving.betreftGunning(null);
        assertThat(inschrijving.getBetreftGunning()).isNull();
        assertThat(gunningBack.getBetreftInschrijving()).isNull();
    }

    @Test
    void heeftLeerlingTest() throws Exception {
        Inschrijving inschrijving = getInschrijvingRandomSampleGenerator();
        Leerling leerlingBack = getLeerlingRandomSampleGenerator();

        inschrijving.setHeeftLeerling(leerlingBack);
        assertThat(inschrijving.getHeeftLeerling()).isEqualTo(leerlingBack);

        inschrijving.heeftLeerling(null);
        assertThat(inschrijving.getHeeftLeerling()).isNull();
    }

    @Test
    void heeftLeverancierTest() throws Exception {
        Inschrijving inschrijving = getInschrijvingRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        inschrijving.setHeeftLeverancier(leverancierBack);
        assertThat(inschrijving.getHeeftLeverancier()).isEqualTo(leverancierBack);

        inschrijving.heeftLeverancier(null);
        assertThat(inschrijving.getHeeftLeverancier()).isNull();
    }
}
