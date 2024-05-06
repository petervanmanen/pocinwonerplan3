package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LeerlingTestSamples.*;
import static nl.ritense.demo.domain.StartkwalificatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StartkwalificatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Startkwalificatie.class);
        Startkwalificatie startkwalificatie1 = getStartkwalificatieSample1();
        Startkwalificatie startkwalificatie2 = new Startkwalificatie();
        assertThat(startkwalificatie1).isNotEqualTo(startkwalificatie2);

        startkwalificatie2.setId(startkwalificatie1.getId());
        assertThat(startkwalificatie1).isEqualTo(startkwalificatie2);

        startkwalificatie2 = getStartkwalificatieSample2();
        assertThat(startkwalificatie1).isNotEqualTo(startkwalificatie2);
    }

    @Test
    void heeftLeerlingTest() throws Exception {
        Startkwalificatie startkwalificatie = getStartkwalificatieRandomSampleGenerator();
        Leerling leerlingBack = getLeerlingRandomSampleGenerator();

        startkwalificatie.setHeeftLeerling(leerlingBack);
        assertThat(startkwalificatie.getHeeftLeerling()).isEqualTo(leerlingBack);
        assertThat(leerlingBack.getHeeftStartkwalificatie()).isEqualTo(startkwalificatie);

        startkwalificatie.heeftLeerling(null);
        assertThat(startkwalificatie.getHeeftLeerling()).isNull();
        assertThat(leerlingBack.getHeeftStartkwalificatie()).isNull();
    }
}
