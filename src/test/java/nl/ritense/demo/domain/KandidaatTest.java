package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GunningTestSamples.*;
import static nl.ritense.demo.domain.KandidaatTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KandidaatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kandidaat.class);
        Kandidaat kandidaat1 = getKandidaatSample1();
        Kandidaat kandidaat2 = new Kandidaat();
        assertThat(kandidaat1).isNotEqualTo(kandidaat2);

        kandidaat2.setId(kandidaat1.getId());
        assertThat(kandidaat1).isEqualTo(kandidaat2);

        kandidaat2 = getKandidaatSample2();
        assertThat(kandidaat1).isNotEqualTo(kandidaat2);
    }

    @Test
    void betreftGunningTest() throws Exception {
        Kandidaat kandidaat = getKandidaatRandomSampleGenerator();
        Gunning gunningBack = getGunningRandomSampleGenerator();

        kandidaat.setBetreftGunning(gunningBack);
        assertThat(kandidaat.getBetreftGunning()).isEqualTo(gunningBack);
        assertThat(gunningBack.getBetreftKandidaat()).isEqualTo(kandidaat);

        kandidaat.betreftGunning(null);
        assertThat(kandidaat.getBetreftGunning()).isNull();
        assertThat(gunningBack.getBetreftKandidaat()).isNull();
    }

    @Test
    void biedtaanLeverancierTest() throws Exception {
        Kandidaat kandidaat = getKandidaatRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        kandidaat.setBiedtaanLeverancier(leverancierBack);
        assertThat(kandidaat.getBiedtaanLeverancier()).isEqualTo(leverancierBack);

        kandidaat.biedtaanLeverancier(null);
        assertThat(kandidaat.getBiedtaanLeverancier()).isNull();
    }
}
