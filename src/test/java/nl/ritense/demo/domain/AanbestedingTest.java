package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanbestedingTestSamples.*;
import static nl.ritense.demo.domain.AankondigingTestSamples.*;
import static nl.ritense.demo.domain.GunningTestSamples.*;
import static nl.ritense.demo.domain.InschrijvingTestSamples.*;
import static nl.ritense.demo.domain.KwalificatieTestSamples.*;
import static nl.ritense.demo.domain.MedewerkerTestSamples.*;
import static nl.ritense.demo.domain.OfferteTestSamples.*;
import static nl.ritense.demo.domain.OfferteaanvraagTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AanbestedingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aanbesteding.class);
        Aanbesteding aanbesteding1 = getAanbestedingSample1();
        Aanbesteding aanbesteding2 = new Aanbesteding();
        assertThat(aanbesteding1).isNotEqualTo(aanbesteding2);

        aanbesteding2.setId(aanbesteding1.getId());
        assertThat(aanbesteding1).isEqualTo(aanbesteding2);

        aanbesteding2 = getAanbestedingSample2();
        assertThat(aanbesteding1).isNotEqualTo(aanbesteding2);
    }

    @Test
    void betreftZaakTest() throws Exception {
        Aanbesteding aanbesteding = getAanbestedingRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        aanbesteding.setBetreftZaak(zaakBack);
        assertThat(aanbesteding.getBetreftZaak()).isEqualTo(zaakBack);

        aanbesteding.betreftZaak(null);
        assertThat(aanbesteding.getBetreftZaak()).isNull();
    }

    @Test
    void mondtuitGunningTest() throws Exception {
        Aanbesteding aanbesteding = getAanbestedingRandomSampleGenerator();
        Gunning gunningBack = getGunningRandomSampleGenerator();

        aanbesteding.setMondtuitGunning(gunningBack);
        assertThat(aanbesteding.getMondtuitGunning()).isEqualTo(gunningBack);

        aanbesteding.mondtuitGunning(null);
        assertThat(aanbesteding.getMondtuitGunning()).isNull();
    }

    @Test
    void procesleiderMedewerkerTest() throws Exception {
        Aanbesteding aanbesteding = getAanbestedingRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        aanbesteding.setProcesleiderMedewerker(medewerkerBack);
        assertThat(aanbesteding.getProcesleiderMedewerker()).isEqualTo(medewerkerBack);

        aanbesteding.procesleiderMedewerker(null);
        assertThat(aanbesteding.getProcesleiderMedewerker()).isNull();
    }

    @Test
    void betreftKwalificatieTest() throws Exception {
        Aanbesteding aanbesteding = getAanbestedingRandomSampleGenerator();
        Kwalificatie kwalificatieBack = getKwalificatieRandomSampleGenerator();

        aanbesteding.addBetreftKwalificatie(kwalificatieBack);
        assertThat(aanbesteding.getBetreftKwalificaties()).containsOnly(kwalificatieBack);
        assertThat(kwalificatieBack.getBetreftAanbesteding()).isEqualTo(aanbesteding);

        aanbesteding.removeBetreftKwalificatie(kwalificatieBack);
        assertThat(aanbesteding.getBetreftKwalificaties()).doesNotContain(kwalificatieBack);
        assertThat(kwalificatieBack.getBetreftAanbesteding()).isNull();

        aanbesteding.betreftKwalificaties(new HashSet<>(Set.of(kwalificatieBack)));
        assertThat(aanbesteding.getBetreftKwalificaties()).containsOnly(kwalificatieBack);
        assertThat(kwalificatieBack.getBetreftAanbesteding()).isEqualTo(aanbesteding);

        aanbesteding.setBetreftKwalificaties(new HashSet<>());
        assertThat(aanbesteding.getBetreftKwalificaties()).doesNotContain(kwalificatieBack);
        assertThat(kwalificatieBack.getBetreftAanbesteding()).isNull();
    }

    @Test
    void betreftOfferteaanvraagTest() throws Exception {
        Aanbesteding aanbesteding = getAanbestedingRandomSampleGenerator();
        Offerteaanvraag offerteaanvraagBack = getOfferteaanvraagRandomSampleGenerator();

        aanbesteding.addBetreftOfferteaanvraag(offerteaanvraagBack);
        assertThat(aanbesteding.getBetreftOfferteaanvraags()).containsOnly(offerteaanvraagBack);
        assertThat(offerteaanvraagBack.getBetreftAanbesteding()).isEqualTo(aanbesteding);

        aanbesteding.removeBetreftOfferteaanvraag(offerteaanvraagBack);
        assertThat(aanbesteding.getBetreftOfferteaanvraags()).doesNotContain(offerteaanvraagBack);
        assertThat(offerteaanvraagBack.getBetreftAanbesteding()).isNull();

        aanbesteding.betreftOfferteaanvraags(new HashSet<>(Set.of(offerteaanvraagBack)));
        assertThat(aanbesteding.getBetreftOfferteaanvraags()).containsOnly(offerteaanvraagBack);
        assertThat(offerteaanvraagBack.getBetreftAanbesteding()).isEqualTo(aanbesteding);

        aanbesteding.setBetreftOfferteaanvraags(new HashSet<>());
        assertThat(aanbesteding.getBetreftOfferteaanvraags()).doesNotContain(offerteaanvraagBack);
        assertThat(offerteaanvraagBack.getBetreftAanbesteding()).isNull();
    }

    @Test
    void mondtuitAankondigingTest() throws Exception {
        Aanbesteding aanbesteding = getAanbestedingRandomSampleGenerator();
        Aankondiging aankondigingBack = getAankondigingRandomSampleGenerator();

        aanbesteding.addMondtuitAankondiging(aankondigingBack);
        assertThat(aanbesteding.getMondtuitAankondigings()).containsOnly(aankondigingBack);
        assertThat(aankondigingBack.getMondtuitAanbesteding()).isEqualTo(aanbesteding);

        aanbesteding.removeMondtuitAankondiging(aankondigingBack);
        assertThat(aanbesteding.getMondtuitAankondigings()).doesNotContain(aankondigingBack);
        assertThat(aankondigingBack.getMondtuitAanbesteding()).isNull();

        aanbesteding.mondtuitAankondigings(new HashSet<>(Set.of(aankondigingBack)));
        assertThat(aanbesteding.getMondtuitAankondigings()).containsOnly(aankondigingBack);
        assertThat(aankondigingBack.getMondtuitAanbesteding()).isEqualTo(aanbesteding);

        aanbesteding.setMondtuitAankondigings(new HashSet<>());
        assertThat(aanbesteding.getMondtuitAankondigings()).doesNotContain(aankondigingBack);
        assertThat(aankondigingBack.getMondtuitAanbesteding()).isNull();
    }

    @Test
    void betreftOfferteTest() throws Exception {
        Aanbesteding aanbesteding = getAanbestedingRandomSampleGenerator();
        Offerte offerteBack = getOfferteRandomSampleGenerator();

        aanbesteding.addBetreftOfferte(offerteBack);
        assertThat(aanbesteding.getBetreftOffertes()).containsOnly(offerteBack);
        assertThat(offerteBack.getBetreftAanbesteding()).isEqualTo(aanbesteding);

        aanbesteding.removeBetreftOfferte(offerteBack);
        assertThat(aanbesteding.getBetreftOffertes()).doesNotContain(offerteBack);
        assertThat(offerteBack.getBetreftAanbesteding()).isNull();

        aanbesteding.betreftOffertes(new HashSet<>(Set.of(offerteBack)));
        assertThat(aanbesteding.getBetreftOffertes()).containsOnly(offerteBack);
        assertThat(offerteBack.getBetreftAanbesteding()).isEqualTo(aanbesteding);

        aanbesteding.setBetreftOffertes(new HashSet<>());
        assertThat(aanbesteding.getBetreftOffertes()).doesNotContain(offerteBack);
        assertThat(offerteBack.getBetreftAanbesteding()).isNull();
    }

    @Test
    void betreftInschrijvingTest() throws Exception {
        Aanbesteding aanbesteding = getAanbestedingRandomSampleGenerator();
        Inschrijving inschrijvingBack = getInschrijvingRandomSampleGenerator();

        aanbesteding.addBetreftInschrijving(inschrijvingBack);
        assertThat(aanbesteding.getBetreftInschrijvings()).containsOnly(inschrijvingBack);
        assertThat(inschrijvingBack.getBetreftAanbesteding()).isEqualTo(aanbesteding);

        aanbesteding.removeBetreftInschrijving(inschrijvingBack);
        assertThat(aanbesteding.getBetreftInschrijvings()).doesNotContain(inschrijvingBack);
        assertThat(inschrijvingBack.getBetreftAanbesteding()).isNull();

        aanbesteding.betreftInschrijvings(new HashSet<>(Set.of(inschrijvingBack)));
        assertThat(aanbesteding.getBetreftInschrijvings()).containsOnly(inschrijvingBack);
        assertThat(inschrijvingBack.getBetreftAanbesteding()).isEqualTo(aanbesteding);

        aanbesteding.setBetreftInschrijvings(new HashSet<>());
        assertThat(aanbesteding.getBetreftInschrijvings()).doesNotContain(inschrijvingBack);
        assertThat(inschrijvingBack.getBetreftAanbesteding()).isNull();
    }
}
