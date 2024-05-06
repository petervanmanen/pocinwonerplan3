package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanbestedingTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.OfferteaanvraagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OfferteaanvraagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Offerteaanvraag.class);
        Offerteaanvraag offerteaanvraag1 = getOfferteaanvraagSample1();
        Offerteaanvraag offerteaanvraag2 = new Offerteaanvraag();
        assertThat(offerteaanvraag1).isNotEqualTo(offerteaanvraag2);

        offerteaanvraag2.setId(offerteaanvraag1.getId());
        assertThat(offerteaanvraag1).isEqualTo(offerteaanvraag2);

        offerteaanvraag2 = getOfferteaanvraagSample2();
        assertThat(offerteaanvraag1).isNotEqualTo(offerteaanvraag2);
    }

    @Test
    void betreftAanbestedingTest() throws Exception {
        Offerteaanvraag offerteaanvraag = getOfferteaanvraagRandomSampleGenerator();
        Aanbesteding aanbestedingBack = getAanbestedingRandomSampleGenerator();

        offerteaanvraag.setBetreftAanbesteding(aanbestedingBack);
        assertThat(offerteaanvraag.getBetreftAanbesteding()).isEqualTo(aanbestedingBack);

        offerteaanvraag.betreftAanbesteding(null);
        assertThat(offerteaanvraag.getBetreftAanbesteding()).isNull();
    }

    @Test
    void gerichtaanLeverancierTest() throws Exception {
        Offerteaanvraag offerteaanvraag = getOfferteaanvraagRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        offerteaanvraag.setGerichtaanLeverancier(leverancierBack);
        assertThat(offerteaanvraag.getGerichtaanLeverancier()).isEqualTo(leverancierBack);

        offerteaanvraag.gerichtaanLeverancier(null);
        assertThat(offerteaanvraag.getGerichtaanLeverancier()).isNull();
    }
}
