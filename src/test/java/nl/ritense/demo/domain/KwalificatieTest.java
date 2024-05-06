package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanbestedingTestSamples.*;
import static nl.ritense.demo.domain.KwalificatieTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KwalificatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kwalificatie.class);
        Kwalificatie kwalificatie1 = getKwalificatieSample1();
        Kwalificatie kwalificatie2 = new Kwalificatie();
        assertThat(kwalificatie1).isNotEqualTo(kwalificatie2);

        kwalificatie2.setId(kwalificatie1.getId());
        assertThat(kwalificatie1).isEqualTo(kwalificatie2);

        kwalificatie2 = getKwalificatieSample2();
        assertThat(kwalificatie1).isNotEqualTo(kwalificatie2);
    }

    @Test
    void betreftAanbestedingTest() throws Exception {
        Kwalificatie kwalificatie = getKwalificatieRandomSampleGenerator();
        Aanbesteding aanbestedingBack = getAanbestedingRandomSampleGenerator();

        kwalificatie.setBetreftAanbesteding(aanbestedingBack);
        assertThat(kwalificatie.getBetreftAanbesteding()).isEqualTo(aanbestedingBack);

        kwalificatie.betreftAanbesteding(null);
        assertThat(kwalificatie.getBetreftAanbesteding()).isNull();
    }

    @Test
    void heeftLeverancierTest() throws Exception {
        Kwalificatie kwalificatie = getKwalificatieRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        kwalificatie.setHeeftLeverancier(leverancierBack);
        assertThat(kwalificatie.getHeeftLeverancier()).isEqualTo(leverancierBack);

        kwalificatie.heeftLeverancier(null);
        assertThat(kwalificatie.getHeeftLeverancier()).isNull();
    }
}
