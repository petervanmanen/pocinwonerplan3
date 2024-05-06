package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LeerlingTestSamples.*;
import static nl.ritense.demo.domain.SchoolTestSamples.*;
import static nl.ritense.demo.domain.UitschrijvingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UitschrijvingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uitschrijving.class);
        Uitschrijving uitschrijving1 = getUitschrijvingSample1();
        Uitschrijving uitschrijving2 = new Uitschrijving();
        assertThat(uitschrijving1).isNotEqualTo(uitschrijving2);

        uitschrijving2.setId(uitschrijving1.getId());
        assertThat(uitschrijving1).isEqualTo(uitschrijving2);

        uitschrijving2 = getUitschrijvingSample2();
        assertThat(uitschrijving1).isNotEqualTo(uitschrijving2);
    }

    @Test
    void heeftLeerlingTest() throws Exception {
        Uitschrijving uitschrijving = getUitschrijvingRandomSampleGenerator();
        Leerling leerlingBack = getLeerlingRandomSampleGenerator();

        uitschrijving.setHeeftLeerling(leerlingBack);
        assertThat(uitschrijving.getHeeftLeerling()).isEqualTo(leerlingBack);

        uitschrijving.heeftLeerling(null);
        assertThat(uitschrijving.getHeeftLeerling()).isNull();
    }

    @Test
    void heeftSchoolTest() throws Exception {
        Uitschrijving uitschrijving = getUitschrijvingRandomSampleGenerator();
        School schoolBack = getSchoolRandomSampleGenerator();

        uitschrijving.setHeeftSchool(schoolBack);
        assertThat(uitschrijving.getHeeftSchool()).isEqualTo(schoolBack);

        uitschrijving.heeftSchool(null);
        assertThat(uitschrijving.getHeeftSchool()).isNull();
    }
}
