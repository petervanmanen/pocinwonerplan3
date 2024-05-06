package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeslissingTestSamples.*;
import static nl.ritense.demo.domain.LeerlingTestSamples.*;
import static nl.ritense.demo.domain.LeerplichtambtenaarTestSamples.*;
import static nl.ritense.demo.domain.SchoolTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BeslissingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beslissing.class);
        Beslissing beslissing1 = getBeslissingSample1();
        Beslissing beslissing2 = new Beslissing();
        assertThat(beslissing1).isNotEqualTo(beslissing2);

        beslissing2.setId(beslissing1.getId());
        assertThat(beslissing1).isEqualTo(beslissing2);

        beslissing2 = getBeslissingSample2();
        assertThat(beslissing1).isNotEqualTo(beslissing2);
    }

    @Test
    void betreftLeerlingTest() throws Exception {
        Beslissing beslissing = getBeslissingRandomSampleGenerator();
        Leerling leerlingBack = getLeerlingRandomSampleGenerator();

        beslissing.setBetreftLeerling(leerlingBack);
        assertThat(beslissing.getBetreftLeerling()).isEqualTo(leerlingBack);

        beslissing.betreftLeerling(null);
        assertThat(beslissing.getBetreftLeerling()).isNull();
    }

    @Test
    void behandelaarLeerplichtambtenaarTest() throws Exception {
        Beslissing beslissing = getBeslissingRandomSampleGenerator();
        Leerplichtambtenaar leerplichtambtenaarBack = getLeerplichtambtenaarRandomSampleGenerator();

        beslissing.setBehandelaarLeerplichtambtenaar(leerplichtambtenaarBack);
        assertThat(beslissing.getBehandelaarLeerplichtambtenaar()).isEqualTo(leerplichtambtenaarBack);

        beslissing.behandelaarLeerplichtambtenaar(null);
        assertThat(beslissing.getBehandelaarLeerplichtambtenaar()).isNull();
    }

    @Test
    void betreftSchoolTest() throws Exception {
        Beslissing beslissing = getBeslissingRandomSampleGenerator();
        School schoolBack = getSchoolRandomSampleGenerator();

        beslissing.setBetreftSchool(schoolBack);
        assertThat(beslissing.getBetreftSchool()).isEqualTo(schoolBack);

        beslissing.betreftSchool(null);
        assertThat(beslissing.getBetreftSchool()).isNull();
    }
}
