package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanbestedingTestSamples.*;
import static nl.ritense.demo.domain.BalieafspraakTestSamples.*;
import static nl.ritense.demo.domain.BedrijfsprocesTestSamples.*;
import static nl.ritense.demo.domain.BesluitTestSamples.*;
import static nl.ritense.demo.domain.BetalingTestSamples.*;
import static nl.ritense.demo.domain.BetrokkeneTestSamples.*;
import static nl.ritense.demo.domain.DocumentTestSamples.*;
import static nl.ritense.demo.domain.GrondslagTestSamples.*;
import static nl.ritense.demo.domain.HeffingTestSamples.*;
import static nl.ritense.demo.domain.KlantbeoordelingTestSamples.*;
import static nl.ritense.demo.domain.KlantcontactTestSamples.*;
import static nl.ritense.demo.domain.MedewerkerTestSamples.*;
import static nl.ritense.demo.domain.ProducttypeTestSamples.*;
import static nl.ritense.demo.domain.ProjectTestSamples.*;
import static nl.ritense.demo.domain.StatusTestSamples.*;
import static nl.ritense.demo.domain.SubsidieTestSamples.*;
import static nl.ritense.demo.domain.VerzoekTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static nl.ritense.demo.domain.ZaaktypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ZaakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zaak.class);
        Zaak zaak1 = getZaakSample1();
        Zaak zaak2 = new Zaak();
        assertThat(zaak1).isNotEqualTo(zaak2);

        zaak2.setId(zaak1.getId());
        assertThat(zaak1).isEqualTo(zaak2);

        zaak2 = getZaakSample2();
        assertThat(zaak1).isNotEqualTo(zaak2);
    }

    @Test
    void heeftproductProducttypeTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Producttype producttypeBack = getProducttypeRandomSampleGenerator();

        zaak.setHeeftproductProducttype(producttypeBack);
        assertThat(zaak.getHeeftproductProducttype()).isEqualTo(producttypeBack);

        zaak.heeftproductProducttype(null);
        assertThat(zaak.getHeeftproductProducttype()).isNull();
    }

    @Test
    void heeftKlantbeoordelingTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Klantbeoordeling klantbeoordelingBack = getKlantbeoordelingRandomSampleGenerator();

        zaak.setHeeftKlantbeoordeling(klantbeoordelingBack);
        assertThat(zaak.getHeeftKlantbeoordeling()).isEqualTo(klantbeoordelingBack);

        zaak.heeftKlantbeoordeling(null);
        assertThat(zaak.getHeeftKlantbeoordeling()).isNull();
    }

    @Test
    void heeftHeffingTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Heffing heffingBack = getHeffingRandomSampleGenerator();

        zaak.setHeeftHeffing(heffingBack);
        assertThat(zaak.getHeeftHeffing()).isEqualTo(heffingBack);

        zaak.heeftHeffing(null);
        assertThat(zaak.getHeeftHeffing()).isNull();
    }

    @Test
    void heeftbetalingBetalingTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Betaling betalingBack = getBetalingRandomSampleGenerator();

        zaak.addHeeftbetalingBetaling(betalingBack);
        assertThat(zaak.getHeeftbetalingBetalings()).containsOnly(betalingBack);
        assertThat(betalingBack.getHeeftbetalingZaak()).isEqualTo(zaak);

        zaak.removeHeeftbetalingBetaling(betalingBack);
        assertThat(zaak.getHeeftbetalingBetalings()).doesNotContain(betalingBack);
        assertThat(betalingBack.getHeeftbetalingZaak()).isNull();

        zaak.heeftbetalingBetalings(new HashSet<>(Set.of(betalingBack)));
        assertThat(zaak.getHeeftbetalingBetalings()).containsOnly(betalingBack);
        assertThat(betalingBack.getHeeftbetalingZaak()).isEqualTo(zaak);

        zaak.setHeeftbetalingBetalings(new HashSet<>());
        assertThat(zaak.getHeeftbetalingBetalings()).doesNotContain(betalingBack);
        assertThat(betalingBack.getHeeftbetalingZaak()).isNull();
    }

    @Test
    void heeftStatusTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Status statusBack = getStatusRandomSampleGenerator();

        zaak.addHeeftStatus(statusBack);
        assertThat(zaak.getHeeftStatuses()).containsOnly(statusBack);
        assertThat(statusBack.getHeeftZaak()).isEqualTo(zaak);

        zaak.removeHeeftStatus(statusBack);
        assertThat(zaak.getHeeftStatuses()).doesNotContain(statusBack);
        assertThat(statusBack.getHeeftZaak()).isNull();

        zaak.heeftStatuses(new HashSet<>(Set.of(statusBack)));
        assertThat(zaak.getHeeftStatuses()).containsOnly(statusBack);
        assertThat(statusBack.getHeeftZaak()).isEqualTo(zaak);

        zaak.setHeeftStatuses(new HashSet<>());
        assertThat(zaak.getHeeftStatuses()).doesNotContain(statusBack);
        assertThat(statusBack.getHeeftZaak()).isNull();
    }

    @Test
    void betreftProjectTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        zaak.setBetreftProject(projectBack);
        assertThat(zaak.getBetreftProject()).isEqualTo(projectBack);

        zaak.betreftProject(null);
        assertThat(zaak.getBetreftProject()).isNull();
    }

    @Test
    void isvanZaaktypeTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Zaaktype zaaktypeBack = getZaaktypeRandomSampleGenerator();

        zaak.setIsvanZaaktype(zaaktypeBack);
        assertThat(zaak.getIsvanZaaktype()).isEqualTo(zaaktypeBack);

        zaak.isvanZaaktype(null);
        assertThat(zaak.getIsvanZaaktype()).isNull();
    }

    @Test
    void kentDocumentTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        zaak.addKentDocument(documentBack);
        assertThat(zaak.getKentDocuments()).containsOnly(documentBack);

        zaak.removeKentDocument(documentBack);
        assertThat(zaak.getKentDocuments()).doesNotContain(documentBack);

        zaak.kentDocuments(new HashSet<>(Set.of(documentBack)));
        assertThat(zaak.getKentDocuments()).containsOnly(documentBack);

        zaak.setKentDocuments(new HashSet<>());
        assertThat(zaak.getKentDocuments()).doesNotContain(documentBack);
    }

    @Test
    void afhandelendmedewerkerMedewerkerTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        zaak.addAfhandelendmedewerkerMedewerker(medewerkerBack);
        assertThat(zaak.getAfhandelendmedewerkerMedewerkers()).containsOnly(medewerkerBack);

        zaak.removeAfhandelendmedewerkerMedewerker(medewerkerBack);
        assertThat(zaak.getAfhandelendmedewerkerMedewerkers()).doesNotContain(medewerkerBack);

        zaak.afhandelendmedewerkerMedewerkers(new HashSet<>(Set.of(medewerkerBack)));
        assertThat(zaak.getAfhandelendmedewerkerMedewerkers()).containsOnly(medewerkerBack);

        zaak.setAfhandelendmedewerkerMedewerkers(new HashSet<>());
        assertThat(zaak.getAfhandelendmedewerkerMedewerkers()).doesNotContain(medewerkerBack);
    }

    @Test
    void leidttotVerzoekTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Verzoek verzoekBack = getVerzoekRandomSampleGenerator();

        zaak.setLeidttotVerzoek(verzoekBack);
        assertThat(zaak.getLeidttotVerzoek()).isEqualTo(verzoekBack);
        assertThat(verzoekBack.getLeidttotZaak()).isEqualTo(zaak);

        zaak.leidttotVerzoek(null);
        assertThat(zaak.getLeidttotVerzoek()).isNull();
        assertThat(verzoekBack.getLeidttotZaak()).isNull();
    }

    @Test
    void heeftSubsidieTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Subsidie subsidieBack = getSubsidieRandomSampleGenerator();

        zaak.setHeeftSubsidie(subsidieBack);
        assertThat(zaak.getHeeftSubsidie()).isEqualTo(subsidieBack);
        assertThat(subsidieBack.getHeeftZaak()).isEqualTo(zaak);

        zaak.heeftSubsidie(null);
        assertThat(zaak.getHeeftSubsidie()).isNull();
        assertThat(subsidieBack.getHeeftZaak()).isNull();
    }

    @Test
    void betreftAanbestedingTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Aanbesteding aanbestedingBack = getAanbestedingRandomSampleGenerator();

        zaak.setBetreftAanbesteding(aanbestedingBack);
        assertThat(zaak.getBetreftAanbesteding()).isEqualTo(aanbestedingBack);
        assertThat(aanbestedingBack.getBetreftZaak()).isEqualTo(zaak);

        zaak.betreftAanbesteding(null);
        assertThat(zaak.getBetreftAanbesteding()).isNull();
        assertThat(aanbestedingBack.getBetreftZaak()).isNull();
    }

    @Test
    void heeftbetrekkingopBalieafspraakTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Balieafspraak balieafspraakBack = getBalieafspraakRandomSampleGenerator();

        zaak.addHeeftbetrekkingopBalieafspraak(balieafspraakBack);
        assertThat(zaak.getHeeftbetrekkingopBalieafspraaks()).containsOnly(balieafspraakBack);
        assertThat(balieafspraakBack.getHeeftbetrekkingopZaak()).isEqualTo(zaak);

        zaak.removeHeeftbetrekkingopBalieafspraak(balieafspraakBack);
        assertThat(zaak.getHeeftbetrekkingopBalieafspraaks()).doesNotContain(balieafspraakBack);
        assertThat(balieafspraakBack.getHeeftbetrekkingopZaak()).isNull();

        zaak.heeftbetrekkingopBalieafspraaks(new HashSet<>(Set.of(balieafspraakBack)));
        assertThat(zaak.getHeeftbetrekkingopBalieafspraaks()).containsOnly(balieafspraakBack);
        assertThat(balieafspraakBack.getHeeftbetrekkingopZaak()).isEqualTo(zaak);

        zaak.setHeeftbetrekkingopBalieafspraaks(new HashSet<>());
        assertThat(zaak.getHeeftbetrekkingopBalieafspraaks()).doesNotContain(balieafspraakBack);
        assertThat(balieafspraakBack.getHeeftbetrekkingopZaak()).isNull();
    }

    @Test
    void isuitkomstvanBesluitTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Besluit besluitBack = getBesluitRandomSampleGenerator();

        zaak.addIsuitkomstvanBesluit(besluitBack);
        assertThat(zaak.getIsuitkomstvanBesluits()).containsOnly(besluitBack);
        assertThat(besluitBack.getIsuitkomstvanZaak()).isEqualTo(zaak);

        zaak.removeIsuitkomstvanBesluit(besluitBack);
        assertThat(zaak.getIsuitkomstvanBesluits()).doesNotContain(besluitBack);
        assertThat(besluitBack.getIsuitkomstvanZaak()).isNull();

        zaak.isuitkomstvanBesluits(new HashSet<>(Set.of(besluitBack)));
        assertThat(zaak.getIsuitkomstvanBesluits()).containsOnly(besluitBack);
        assertThat(besluitBack.getIsuitkomstvanZaak()).isEqualTo(zaak);

        zaak.setIsuitkomstvanBesluits(new HashSet<>());
        assertThat(zaak.getIsuitkomstvanBesluits()).doesNotContain(besluitBack);
        assertThat(besluitBack.getIsuitkomstvanZaak()).isNull();
    }

    @Test
    void heeftbetrekkingopKlantcontactTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Klantcontact klantcontactBack = getKlantcontactRandomSampleGenerator();

        zaak.addHeeftbetrekkingopKlantcontact(klantcontactBack);
        assertThat(zaak.getHeeftbetrekkingopKlantcontacts()).containsOnly(klantcontactBack);
        assertThat(klantcontactBack.getHeeftbetrekkingopZaak()).isEqualTo(zaak);

        zaak.removeHeeftbetrekkingopKlantcontact(klantcontactBack);
        assertThat(zaak.getHeeftbetrekkingopKlantcontacts()).doesNotContain(klantcontactBack);
        assertThat(klantcontactBack.getHeeftbetrekkingopZaak()).isNull();

        zaak.heeftbetrekkingopKlantcontacts(new HashSet<>(Set.of(klantcontactBack)));
        assertThat(zaak.getHeeftbetrekkingopKlantcontacts()).containsOnly(klantcontactBack);
        assertThat(klantcontactBack.getHeeftbetrekkingopZaak()).isEqualTo(zaak);

        zaak.setHeeftbetrekkingopKlantcontacts(new HashSet<>());
        assertThat(zaak.getHeeftbetrekkingopKlantcontacts()).doesNotContain(klantcontactBack);
        assertThat(klantcontactBack.getHeeftbetrekkingopZaak()).isNull();
    }

    @Test
    void heeftGrondslagTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Grondslag grondslagBack = getGrondslagRandomSampleGenerator();

        zaak.addHeeftGrondslag(grondslagBack);
        assertThat(zaak.getHeeftGrondslags()).containsOnly(grondslagBack);
        assertThat(grondslagBack.getHeeftZaaks()).containsOnly(zaak);

        zaak.removeHeeftGrondslag(grondslagBack);
        assertThat(zaak.getHeeftGrondslags()).doesNotContain(grondslagBack);
        assertThat(grondslagBack.getHeeftZaaks()).doesNotContain(zaak);

        zaak.heeftGrondslags(new HashSet<>(Set.of(grondslagBack)));
        assertThat(zaak.getHeeftGrondslags()).containsOnly(grondslagBack);
        assertThat(grondslagBack.getHeeftZaaks()).containsOnly(zaak);

        zaak.setHeeftGrondslags(new HashSet<>());
        assertThat(zaak.getHeeftGrondslags()).doesNotContain(grondslagBack);
        assertThat(grondslagBack.getHeeftZaaks()).doesNotContain(zaak);
    }

    @Test
    void uitgevoerdbinnenBedrijfsprocesTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Bedrijfsproces bedrijfsprocesBack = getBedrijfsprocesRandomSampleGenerator();

        zaak.addUitgevoerdbinnenBedrijfsproces(bedrijfsprocesBack);
        assertThat(zaak.getUitgevoerdbinnenBedrijfsproces()).containsOnly(bedrijfsprocesBack);
        assertThat(bedrijfsprocesBack.getUitgevoerdbinnenZaaks()).containsOnly(zaak);

        zaak.removeUitgevoerdbinnenBedrijfsproces(bedrijfsprocesBack);
        assertThat(zaak.getUitgevoerdbinnenBedrijfsproces()).doesNotContain(bedrijfsprocesBack);
        assertThat(bedrijfsprocesBack.getUitgevoerdbinnenZaaks()).doesNotContain(zaak);

        zaak.uitgevoerdbinnenBedrijfsproces(new HashSet<>(Set.of(bedrijfsprocesBack)));
        assertThat(zaak.getUitgevoerdbinnenBedrijfsproces()).containsOnly(bedrijfsprocesBack);
        assertThat(bedrijfsprocesBack.getUitgevoerdbinnenZaaks()).containsOnly(zaak);

        zaak.setUitgevoerdbinnenBedrijfsproces(new HashSet<>());
        assertThat(zaak.getUitgevoerdbinnenBedrijfsproces()).doesNotContain(bedrijfsprocesBack);
        assertThat(bedrijfsprocesBack.getUitgevoerdbinnenZaaks()).doesNotContain(zaak);
    }

    @Test
    void oefentuitBetrokkeneTest() throws Exception {
        Zaak zaak = getZaakRandomSampleGenerator();
        Betrokkene betrokkeneBack = getBetrokkeneRandomSampleGenerator();

        zaak.addOefentuitBetrokkene(betrokkeneBack);
        assertThat(zaak.getOefentuitBetrokkenes()).containsOnly(betrokkeneBack);
        assertThat(betrokkeneBack.getOefentuitZaaks()).containsOnly(zaak);

        zaak.removeOefentuitBetrokkene(betrokkeneBack);
        assertThat(zaak.getOefentuitBetrokkenes()).doesNotContain(betrokkeneBack);
        assertThat(betrokkeneBack.getOefentuitZaaks()).doesNotContain(zaak);

        zaak.oefentuitBetrokkenes(new HashSet<>(Set.of(betrokkeneBack)));
        assertThat(zaak.getOefentuitBetrokkenes()).containsOnly(betrokkeneBack);
        assertThat(betrokkeneBack.getOefentuitZaaks()).containsOnly(zaak);

        zaak.setOefentuitBetrokkenes(new HashSet<>());
        assertThat(zaak.getOefentuitBetrokkenes()).doesNotContain(betrokkeneBack);
        assertThat(betrokkeneBack.getOefentuitZaaks()).doesNotContain(zaak);
    }
}
