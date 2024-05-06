package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanbestedingTestSamples.*;
import static nl.ritense.demo.domain.GunningTestSamples.*;
import static nl.ritense.demo.domain.InschrijvingTestSamples.*;
import static nl.ritense.demo.domain.KandidaatTestSamples.*;
import static nl.ritense.demo.domain.MedewerkerTestSamples.*;
import static nl.ritense.demo.domain.OfferteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GunningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gunning.class);
        Gunning gunning1 = getGunningSample1();
        Gunning gunning2 = new Gunning();
        assertThat(gunning1).isNotEqualTo(gunning2);

        gunning2.setId(gunning1.getId());
        assertThat(gunning1).isEqualTo(gunning2);

        gunning2 = getGunningSample2();
        assertThat(gunning1).isNotEqualTo(gunning2);
    }

    @Test
    void betreftInschrijvingTest() throws Exception {
        Gunning gunning = getGunningRandomSampleGenerator();
        Inschrijving inschrijvingBack = getInschrijvingRandomSampleGenerator();

        gunning.setBetreftInschrijving(inschrijvingBack);
        assertThat(gunning.getBetreftInschrijving()).isEqualTo(inschrijvingBack);

        gunning.betreftInschrijving(null);
        assertThat(gunning.getBetreftInschrijving()).isNull();
    }

    @Test
    void betreftKandidaatTest() throws Exception {
        Gunning gunning = getGunningRandomSampleGenerator();
        Kandidaat kandidaatBack = getKandidaatRandomSampleGenerator();

        gunning.setBetreftKandidaat(kandidaatBack);
        assertThat(gunning.getBetreftKandidaat()).isEqualTo(kandidaatBack);

        gunning.betreftKandidaat(null);
        assertThat(gunning.getBetreftKandidaat()).isNull();
    }

    @Test
    void betreftOfferteTest() throws Exception {
        Gunning gunning = getGunningRandomSampleGenerator();
        Offerte offerteBack = getOfferteRandomSampleGenerator();

        gunning.setBetreftOfferte(offerteBack);
        assertThat(gunning.getBetreftOfferte()).isEqualTo(offerteBack);

        gunning.betreftOfferte(null);
        assertThat(gunning.getBetreftOfferte()).isNull();
    }

    @Test
    void inhuurMedewerkerTest() throws Exception {
        Gunning gunning = getGunningRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        gunning.setInhuurMedewerker(medewerkerBack);
        assertThat(gunning.getInhuurMedewerker()).isEqualTo(medewerkerBack);

        gunning.inhuurMedewerker(null);
        assertThat(gunning.getInhuurMedewerker()).isNull();
    }

    @Test
    void mondtuitAanbestedingTest() throws Exception {
        Gunning gunning = getGunningRandomSampleGenerator();
        Aanbesteding aanbestedingBack = getAanbestedingRandomSampleGenerator();

        gunning.setMondtuitAanbesteding(aanbestedingBack);
        assertThat(gunning.getMondtuitAanbesteding()).isEqualTo(aanbestedingBack);
        assertThat(aanbestedingBack.getMondtuitGunning()).isEqualTo(gunning);

        gunning.mondtuitAanbesteding(null);
        assertThat(gunning.getMondtuitAanbesteding()).isNull();
        assertThat(aanbestedingBack.getMondtuitGunning()).isNull();
    }
}
