package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanbestedingTestSamples.*;
import static nl.ritense.demo.domain.GunningTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.OfferteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OfferteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Offerte.class);
        Offerte offerte1 = getOfferteSample1();
        Offerte offerte2 = new Offerte();
        assertThat(offerte1).isNotEqualTo(offerte2);

        offerte2.setId(offerte1.getId());
        assertThat(offerte1).isEqualTo(offerte2);

        offerte2 = getOfferteSample2();
        assertThat(offerte1).isNotEqualTo(offerte2);
    }

    @Test
    void betreftAanbestedingTest() throws Exception {
        Offerte offerte = getOfferteRandomSampleGenerator();
        Aanbesteding aanbestedingBack = getAanbestedingRandomSampleGenerator();

        offerte.setBetreftAanbesteding(aanbestedingBack);
        assertThat(offerte.getBetreftAanbesteding()).isEqualTo(aanbestedingBack);

        offerte.betreftAanbesteding(null);
        assertThat(offerte.getBetreftAanbesteding()).isNull();
    }

    @Test
    void ingedienddoorLeverancierTest() throws Exception {
        Offerte offerte = getOfferteRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        offerte.setIngedienddoorLeverancier(leverancierBack);
        assertThat(offerte.getIngedienddoorLeverancier()).isEqualTo(leverancierBack);

        offerte.ingedienddoorLeverancier(null);
        assertThat(offerte.getIngedienddoorLeverancier()).isNull();
    }

    @Test
    void betreftGunningTest() throws Exception {
        Offerte offerte = getOfferteRandomSampleGenerator();
        Gunning gunningBack = getGunningRandomSampleGenerator();

        offerte.setBetreftGunning(gunningBack);
        assertThat(offerte.getBetreftGunning()).isEqualTo(gunningBack);
        assertThat(gunningBack.getBetreftOfferte()).isEqualTo(offerte);

        offerte.betreftGunning(null);
        assertThat(offerte.getBetreftGunning()).isNull();
        assertThat(gunningBack.getBetreftOfferte()).isNull();
    }
}
