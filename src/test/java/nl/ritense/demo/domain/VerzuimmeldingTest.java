package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LeerlingTestSamples.*;
import static nl.ritense.demo.domain.SchoolTestSamples.*;
import static nl.ritense.demo.domain.VerzuimmeldingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerzuimmeldingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verzuimmelding.class);
        Verzuimmelding verzuimmelding1 = getVerzuimmeldingSample1();
        Verzuimmelding verzuimmelding2 = new Verzuimmelding();
        assertThat(verzuimmelding1).isNotEqualTo(verzuimmelding2);

        verzuimmelding2.setId(verzuimmelding1.getId());
        assertThat(verzuimmelding1).isEqualTo(verzuimmelding2);

        verzuimmelding2 = getVerzuimmeldingSample2();
        assertThat(verzuimmelding1).isNotEqualTo(verzuimmelding2);
    }

    @Test
    void heeftSchoolTest() throws Exception {
        Verzuimmelding verzuimmelding = getVerzuimmeldingRandomSampleGenerator();
        School schoolBack = getSchoolRandomSampleGenerator();

        verzuimmelding.setHeeftSchool(schoolBack);
        assertThat(verzuimmelding.getHeeftSchool()).isEqualTo(schoolBack);

        verzuimmelding.heeftSchool(null);
        assertThat(verzuimmelding.getHeeftSchool()).isNull();
    }

    @Test
    void heeftLeerlingTest() throws Exception {
        Verzuimmelding verzuimmelding = getVerzuimmeldingRandomSampleGenerator();
        Leerling leerlingBack = getLeerlingRandomSampleGenerator();

        verzuimmelding.setHeeftLeerling(leerlingBack);
        assertThat(verzuimmelding.getHeeftLeerling()).isEqualTo(leerlingBack);

        verzuimmelding.heeftLeerling(null);
        assertThat(verzuimmelding.getHeeftLeerling()).isNull();
    }
}
