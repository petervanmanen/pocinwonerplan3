package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LeerlingTestSamples.*;
import static nl.ritense.demo.domain.SchoolTestSamples.*;
import static nl.ritense.demo.domain.VrijstellingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VrijstellingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vrijstelling.class);
        Vrijstelling vrijstelling1 = getVrijstellingSample1();
        Vrijstelling vrijstelling2 = new Vrijstelling();
        assertThat(vrijstelling1).isNotEqualTo(vrijstelling2);

        vrijstelling2.setId(vrijstelling1.getId());
        assertThat(vrijstelling1).isEqualTo(vrijstelling2);

        vrijstelling2 = getVrijstellingSample2();
        assertThat(vrijstelling1).isNotEqualTo(vrijstelling2);
    }

    @Test
    void heeftSchoolTest() throws Exception {
        Vrijstelling vrijstelling = getVrijstellingRandomSampleGenerator();
        School schoolBack = getSchoolRandomSampleGenerator();

        vrijstelling.setHeeftSchool(schoolBack);
        assertThat(vrijstelling.getHeeftSchool()).isEqualTo(schoolBack);

        vrijstelling.heeftSchool(null);
        assertThat(vrijstelling.getHeeftSchool()).isNull();
    }

    @Test
    void heeftLeerlingTest() throws Exception {
        Vrijstelling vrijstelling = getVrijstellingRandomSampleGenerator();
        Leerling leerlingBack = getLeerlingRandomSampleGenerator();

        vrijstelling.setHeeftLeerling(leerlingBack);
        assertThat(vrijstelling.getHeeftLeerling()).isEqualTo(leerlingBack);

        vrijstelling.heeftLeerling(null);
        assertThat(vrijstelling.getHeeftLeerling()).isNull();
    }
}
