package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanbestedingTestSamples.*;
import static nl.ritense.demo.domain.ApplicatieTestSamples.*;
import static nl.ritense.demo.domain.BalieafspraakTestSamples.*;
import static nl.ritense.demo.domain.BetrokkeneTestSamples.*;
import static nl.ritense.demo.domain.GunningTestSamples.*;
import static nl.ritense.demo.domain.KlantcontactTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.MedewerkerTestSamples.*;
import static nl.ritense.demo.domain.MeldingTestSamples.*;
import static nl.ritense.demo.domain.NotitieTestSamples.*;
import static nl.ritense.demo.domain.ParkeerscanTestSamples.*;
import static nl.ritense.demo.domain.SchouwrondeTestSamples.*;
import static nl.ritense.demo.domain.StremmingTestSamples.*;
import static nl.ritense.demo.domain.SubsidieTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static nl.ritense.demo.domain.ZaaktypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MedewerkerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medewerker.class);
        Medewerker medewerker1 = getMedewerkerSample1();
        Medewerker medewerker2 = new Medewerker();
        assertThat(medewerker1).isNotEqualTo(medewerker2);

        medewerker2.setId(medewerker1.getId());
        assertThat(medewerker1).isEqualTo(medewerker2);

        medewerker2 = getMedewerkerSample2();
        assertThat(medewerker1).isNotEqualTo(medewerker2);
    }

    @Test
    void ingevoerddoorStremmingTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Stremming stremmingBack = getStremmingRandomSampleGenerator();

        medewerker.addIngevoerddoorStremming(stremmingBack);
        assertThat(medewerker.getIngevoerddoorStremmings()).containsOnly(stremmingBack);
        assertThat(stremmingBack.getIngevoerddoorMedewerker()).isEqualTo(medewerker);

        medewerker.removeIngevoerddoorStremming(stremmingBack);
        assertThat(medewerker.getIngevoerddoorStremmings()).doesNotContain(stremmingBack);
        assertThat(stremmingBack.getIngevoerddoorMedewerker()).isNull();

        medewerker.ingevoerddoorStremmings(new HashSet<>(Set.of(stremmingBack)));
        assertThat(medewerker.getIngevoerddoorStremmings()).containsOnly(stremmingBack);
        assertThat(stremmingBack.getIngevoerddoorMedewerker()).isEqualTo(medewerker);

        medewerker.setIngevoerddoorStremmings(new HashSet<>());
        assertThat(medewerker.getIngevoerddoorStremmings()).doesNotContain(stremmingBack);
        assertThat(stremmingBack.getIngevoerddoorMedewerker()).isNull();
    }

    @Test
    void gewijzigddoorStremmingTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Stremming stremmingBack = getStremmingRandomSampleGenerator();

        medewerker.addGewijzigddoorStremming(stremmingBack);
        assertThat(medewerker.getGewijzigddoorStremmings()).containsOnly(stremmingBack);
        assertThat(stremmingBack.getGewijzigddoorMedewerker()).isEqualTo(medewerker);

        medewerker.removeGewijzigddoorStremming(stremmingBack);
        assertThat(medewerker.getGewijzigddoorStremmings()).doesNotContain(stremmingBack);
        assertThat(stremmingBack.getGewijzigddoorMedewerker()).isNull();

        medewerker.gewijzigddoorStremmings(new HashSet<>(Set.of(stremmingBack)));
        assertThat(medewerker.getGewijzigddoorStremmings()).containsOnly(stremmingBack);
        assertThat(stremmingBack.getGewijzigddoorMedewerker()).isEqualTo(medewerker);

        medewerker.setGewijzigddoorStremmings(new HashSet<>());
        assertThat(medewerker.getGewijzigddoorStremmings()).doesNotContain(stremmingBack);
        assertThat(stremmingBack.getGewijzigddoorMedewerker()).isNull();
    }

    @Test
    void voertuitSchouwrondeTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Schouwronde schouwrondeBack = getSchouwrondeRandomSampleGenerator();

        medewerker.addVoertuitSchouwronde(schouwrondeBack);
        assertThat(medewerker.getVoertuitSchouwrondes()).containsOnly(schouwrondeBack);
        assertThat(schouwrondeBack.getVoertuitMedewerker()).isEqualTo(medewerker);

        medewerker.removeVoertuitSchouwronde(schouwrondeBack);
        assertThat(medewerker.getVoertuitSchouwrondes()).doesNotContain(schouwrondeBack);
        assertThat(schouwrondeBack.getVoertuitMedewerker()).isNull();

        medewerker.voertuitSchouwrondes(new HashSet<>(Set.of(schouwrondeBack)));
        assertThat(medewerker.getVoertuitSchouwrondes()).containsOnly(schouwrondeBack);
        assertThat(schouwrondeBack.getVoertuitMedewerker()).isEqualTo(medewerker);

        medewerker.setVoertuitSchouwrondes(new HashSet<>());
        assertThat(medewerker.getVoertuitSchouwrondes()).doesNotContain(schouwrondeBack);
        assertThat(schouwrondeBack.getVoertuitMedewerker()).isNull();
    }

    @Test
    void aanvragerSubsidieTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Subsidie subsidieBack = getSubsidieRandomSampleGenerator();

        medewerker.addAanvragerSubsidie(subsidieBack);
        assertThat(medewerker.getAanvragerSubsidies()).containsOnly(subsidieBack);
        assertThat(subsidieBack.getAanvragerMedewerker()).isEqualTo(medewerker);

        medewerker.removeAanvragerSubsidie(subsidieBack);
        assertThat(medewerker.getAanvragerSubsidies()).doesNotContain(subsidieBack);
        assertThat(subsidieBack.getAanvragerMedewerker()).isNull();

        medewerker.aanvragerSubsidies(new HashSet<>(Set.of(subsidieBack)));
        assertThat(medewerker.getAanvragerSubsidies()).containsOnly(subsidieBack);
        assertThat(subsidieBack.getAanvragerMedewerker()).isEqualTo(medewerker);

        medewerker.setAanvragerSubsidies(new HashSet<>());
        assertThat(medewerker.getAanvragerSubsidies()).doesNotContain(subsidieBack);
        assertThat(subsidieBack.getAanvragerMedewerker()).isNull();
    }

    @Test
    void isverantwoordelijkevoorZaaktypeTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Zaaktype zaaktypeBack = getZaaktypeRandomSampleGenerator();

        medewerker.addIsverantwoordelijkevoorZaaktype(zaaktypeBack);
        assertThat(medewerker.getIsverantwoordelijkevoorZaaktypes()).containsOnly(zaaktypeBack);
        assertThat(zaaktypeBack.getIsverantwoordelijkevoorMedewerker()).isEqualTo(medewerker);

        medewerker.removeIsverantwoordelijkevoorZaaktype(zaaktypeBack);
        assertThat(medewerker.getIsverantwoordelijkevoorZaaktypes()).doesNotContain(zaaktypeBack);
        assertThat(zaaktypeBack.getIsverantwoordelijkevoorMedewerker()).isNull();

        medewerker.isverantwoordelijkevoorZaaktypes(new HashSet<>(Set.of(zaaktypeBack)));
        assertThat(medewerker.getIsverantwoordelijkevoorZaaktypes()).containsOnly(zaaktypeBack);
        assertThat(zaaktypeBack.getIsverantwoordelijkevoorMedewerker()).isEqualTo(medewerker);

        medewerker.setIsverantwoordelijkevoorZaaktypes(new HashSet<>());
        assertThat(medewerker.getIsverantwoordelijkevoorZaaktypes()).doesNotContain(zaaktypeBack);
        assertThat(zaaktypeBack.getIsverantwoordelijkevoorMedewerker()).isNull();
    }

    @Test
    void geleverdviaLeverancierTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        medewerker.setGeleverdviaLeverancier(leverancierBack);
        assertThat(medewerker.getGeleverdviaLeverancier()).isEqualTo(leverancierBack);

        medewerker.geleverdviaLeverancier(null);
        assertThat(medewerker.getGeleverdviaLeverancier()).isNull();
    }

    @Test
    void isBetrokkeneTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Betrokkene betrokkeneBack = getBetrokkeneRandomSampleGenerator();

        medewerker.setIsBetrokkene(betrokkeneBack);
        assertThat(medewerker.getIsBetrokkene()).isEqualTo(betrokkeneBack);
        assertThat(betrokkeneBack.getIsMedewerker()).isEqualTo(medewerker);

        medewerker.isBetrokkene(null);
        assertThat(medewerker.getIsBetrokkene()).isNull();
        assertThat(betrokkeneBack.getIsMedewerker()).isNull();
    }

    @Test
    void uitgevoerddoorParkeerscanTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Parkeerscan parkeerscanBack = getParkeerscanRandomSampleGenerator();

        medewerker.addUitgevoerddoorParkeerscan(parkeerscanBack);
        assertThat(medewerker.getUitgevoerddoorParkeerscans()).containsOnly(parkeerscanBack);
        assertThat(parkeerscanBack.getUitgevoerddoorMedewerker()).isEqualTo(medewerker);

        medewerker.removeUitgevoerddoorParkeerscan(parkeerscanBack);
        assertThat(medewerker.getUitgevoerddoorParkeerscans()).doesNotContain(parkeerscanBack);
        assertThat(parkeerscanBack.getUitgevoerddoorMedewerker()).isNull();

        medewerker.uitgevoerddoorParkeerscans(new HashSet<>(Set.of(parkeerscanBack)));
        assertThat(medewerker.getUitgevoerddoorParkeerscans()).containsOnly(parkeerscanBack);
        assertThat(parkeerscanBack.getUitgevoerddoorMedewerker()).isEqualTo(medewerker);

        medewerker.setUitgevoerddoorParkeerscans(new HashSet<>());
        assertThat(medewerker.getUitgevoerddoorParkeerscans()).doesNotContain(parkeerscanBack);
        assertThat(parkeerscanBack.getUitgevoerddoorMedewerker()).isNull();
    }

    @Test
    void melderMeldingTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Melding meldingBack = getMeldingRandomSampleGenerator();

        medewerker.addMelderMelding(meldingBack);
        assertThat(medewerker.getMelderMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getMelderMedewerker()).isEqualTo(medewerker);

        medewerker.removeMelderMelding(meldingBack);
        assertThat(medewerker.getMelderMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getMelderMedewerker()).isNull();

        medewerker.melderMeldings(new HashSet<>(Set.of(meldingBack)));
        assertThat(medewerker.getMelderMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getMelderMedewerker()).isEqualTo(medewerker);

        medewerker.setMelderMeldings(new HashSet<>());
        assertThat(medewerker.getMelderMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getMelderMedewerker()).isNull();
    }

    @Test
    void uitvoerderMeldingTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Melding meldingBack = getMeldingRandomSampleGenerator();

        medewerker.addUitvoerderMelding(meldingBack);
        assertThat(medewerker.getUitvoerderMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getUitvoerderMedewerker()).isEqualTo(medewerker);

        medewerker.removeUitvoerderMelding(meldingBack);
        assertThat(medewerker.getUitvoerderMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getUitvoerderMedewerker()).isNull();

        medewerker.uitvoerderMeldings(new HashSet<>(Set.of(meldingBack)));
        assertThat(medewerker.getUitvoerderMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getUitvoerderMedewerker()).isEqualTo(medewerker);

        medewerker.setUitvoerderMeldings(new HashSet<>());
        assertThat(medewerker.getUitvoerderMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getUitvoerderMedewerker()).isNull();
    }

    @Test
    void auteurNotitieTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Notitie notitieBack = getNotitieRandomSampleGenerator();

        medewerker.addAuteurNotitie(notitieBack);
        assertThat(medewerker.getAuteurNotities()).containsOnly(notitieBack);
        assertThat(notitieBack.getAuteurMedewerker()).isEqualTo(medewerker);

        medewerker.removeAuteurNotitie(notitieBack);
        assertThat(medewerker.getAuteurNotities()).doesNotContain(notitieBack);
        assertThat(notitieBack.getAuteurMedewerker()).isNull();

        medewerker.auteurNotities(new HashSet<>(Set.of(notitieBack)));
        assertThat(medewerker.getAuteurNotities()).containsOnly(notitieBack);
        assertThat(notitieBack.getAuteurMedewerker()).isEqualTo(medewerker);

        medewerker.setAuteurNotities(new HashSet<>());
        assertThat(medewerker.getAuteurNotities()).doesNotContain(notitieBack);
        assertThat(notitieBack.getAuteurMedewerker()).isNull();
    }

    @Test
    void behandelaarSubsidieTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Subsidie subsidieBack = getSubsidieRandomSampleGenerator();

        medewerker.addBehandelaarSubsidie(subsidieBack);
        assertThat(medewerker.getBehandelaarSubsidies()).containsOnly(subsidieBack);
        assertThat(subsidieBack.getBehandelaarMedewerker()).isEqualTo(medewerker);

        medewerker.removeBehandelaarSubsidie(subsidieBack);
        assertThat(medewerker.getBehandelaarSubsidies()).doesNotContain(subsidieBack);
        assertThat(subsidieBack.getBehandelaarMedewerker()).isNull();

        medewerker.behandelaarSubsidies(new HashSet<>(Set.of(subsidieBack)));
        assertThat(medewerker.getBehandelaarSubsidies()).containsOnly(subsidieBack);
        assertThat(subsidieBack.getBehandelaarMedewerker()).isEqualTo(medewerker);

        medewerker.setBehandelaarSubsidies(new HashSet<>());
        assertThat(medewerker.getBehandelaarSubsidies()).doesNotContain(subsidieBack);
        assertThat(subsidieBack.getBehandelaarMedewerker()).isNull();
    }

    @Test
    void procesleiderAanbestedingTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Aanbesteding aanbestedingBack = getAanbestedingRandomSampleGenerator();

        medewerker.addProcesleiderAanbesteding(aanbestedingBack);
        assertThat(medewerker.getProcesleiderAanbestedings()).containsOnly(aanbestedingBack);
        assertThat(aanbestedingBack.getProcesleiderMedewerker()).isEqualTo(medewerker);

        medewerker.removeProcesleiderAanbesteding(aanbestedingBack);
        assertThat(medewerker.getProcesleiderAanbestedings()).doesNotContain(aanbestedingBack);
        assertThat(aanbestedingBack.getProcesleiderMedewerker()).isNull();

        medewerker.procesleiderAanbestedings(new HashSet<>(Set.of(aanbestedingBack)));
        assertThat(medewerker.getProcesleiderAanbestedings()).containsOnly(aanbestedingBack);
        assertThat(aanbestedingBack.getProcesleiderMedewerker()).isEqualTo(medewerker);

        medewerker.setProcesleiderAanbestedings(new HashSet<>());
        assertThat(medewerker.getProcesleiderAanbestedings()).doesNotContain(aanbestedingBack);
        assertThat(aanbestedingBack.getProcesleiderMedewerker()).isNull();
    }

    @Test
    void inhuurGunningTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Gunning gunningBack = getGunningRandomSampleGenerator();

        medewerker.addInhuurGunning(gunningBack);
        assertThat(medewerker.getInhuurGunnings()).containsOnly(gunningBack);
        assertThat(gunningBack.getInhuurMedewerker()).isEqualTo(medewerker);

        medewerker.removeInhuurGunning(gunningBack);
        assertThat(medewerker.getInhuurGunnings()).doesNotContain(gunningBack);
        assertThat(gunningBack.getInhuurMedewerker()).isNull();

        medewerker.inhuurGunnings(new HashSet<>(Set.of(gunningBack)));
        assertThat(medewerker.getInhuurGunnings()).containsOnly(gunningBack);
        assertThat(gunningBack.getInhuurMedewerker()).isEqualTo(medewerker);

        medewerker.setInhuurGunnings(new HashSet<>());
        assertThat(medewerker.getInhuurGunnings()).doesNotContain(gunningBack);
        assertThat(gunningBack.getInhuurMedewerker()).isNull();
    }

    @Test
    void metBalieafspraakTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Balieafspraak balieafspraakBack = getBalieafspraakRandomSampleGenerator();

        medewerker.addMetBalieafspraak(balieafspraakBack);
        assertThat(medewerker.getMetBalieafspraaks()).containsOnly(balieafspraakBack);
        assertThat(balieafspraakBack.getMetMedewerker()).isEqualTo(medewerker);

        medewerker.removeMetBalieafspraak(balieafspraakBack);
        assertThat(medewerker.getMetBalieafspraaks()).doesNotContain(balieafspraakBack);
        assertThat(balieafspraakBack.getMetMedewerker()).isNull();

        medewerker.metBalieafspraaks(new HashSet<>(Set.of(balieafspraakBack)));
        assertThat(medewerker.getMetBalieafspraaks()).containsOnly(balieafspraakBack);
        assertThat(balieafspraakBack.getMetMedewerker()).isEqualTo(medewerker);

        medewerker.setMetBalieafspraaks(new HashSet<>());
        assertThat(medewerker.getMetBalieafspraaks()).doesNotContain(balieafspraakBack);
        assertThat(balieafspraakBack.getMetMedewerker()).isNull();
    }

    @Test
    void isgevoerddoorKlantcontactTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Klantcontact klantcontactBack = getKlantcontactRandomSampleGenerator();

        medewerker.addIsgevoerddoorKlantcontact(klantcontactBack);
        assertThat(medewerker.getIsgevoerddoorKlantcontacts()).containsOnly(klantcontactBack);
        assertThat(klantcontactBack.getIsgevoerddoorMedewerker()).isEqualTo(medewerker);

        medewerker.removeIsgevoerddoorKlantcontact(klantcontactBack);
        assertThat(medewerker.getIsgevoerddoorKlantcontacts()).doesNotContain(klantcontactBack);
        assertThat(klantcontactBack.getIsgevoerddoorMedewerker()).isNull();

        medewerker.isgevoerddoorKlantcontacts(new HashSet<>(Set.of(klantcontactBack)));
        assertThat(medewerker.getIsgevoerddoorKlantcontacts()).containsOnly(klantcontactBack);
        assertThat(klantcontactBack.getIsgevoerddoorMedewerker()).isEqualTo(medewerker);

        medewerker.setIsgevoerddoorKlantcontacts(new HashSet<>());
        assertThat(medewerker.getIsgevoerddoorKlantcontacts()).doesNotContain(klantcontactBack);
        assertThat(klantcontactBack.getIsgevoerddoorMedewerker()).isNull();
    }

    @Test
    void rollenApplicatieTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Applicatie applicatieBack = getApplicatieRandomSampleGenerator();

        medewerker.addRollenApplicatie(applicatieBack);
        assertThat(medewerker.getRollenApplicaties()).containsOnly(applicatieBack);
        assertThat(applicatieBack.getRollenMedewerkers()).containsOnly(medewerker);

        medewerker.removeRollenApplicatie(applicatieBack);
        assertThat(medewerker.getRollenApplicaties()).doesNotContain(applicatieBack);
        assertThat(applicatieBack.getRollenMedewerkers()).doesNotContain(medewerker);

        medewerker.rollenApplicaties(new HashSet<>(Set.of(applicatieBack)));
        assertThat(medewerker.getRollenApplicaties()).containsOnly(applicatieBack);
        assertThat(applicatieBack.getRollenMedewerkers()).containsOnly(medewerker);

        medewerker.setRollenApplicaties(new HashSet<>());
        assertThat(medewerker.getRollenApplicaties()).doesNotContain(applicatieBack);
        assertThat(applicatieBack.getRollenMedewerkers()).doesNotContain(medewerker);
    }

    @Test
    void afhandelendmedewerkerZaakTest() throws Exception {
        Medewerker medewerker = getMedewerkerRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        medewerker.addAfhandelendmedewerkerZaak(zaakBack);
        assertThat(medewerker.getAfhandelendmedewerkerZaaks()).containsOnly(zaakBack);
        assertThat(zaakBack.getAfhandelendmedewerkerMedewerkers()).containsOnly(medewerker);

        medewerker.removeAfhandelendmedewerkerZaak(zaakBack);
        assertThat(medewerker.getAfhandelendmedewerkerZaaks()).doesNotContain(zaakBack);
        assertThat(zaakBack.getAfhandelendmedewerkerMedewerkers()).doesNotContain(medewerker);

        medewerker.afhandelendmedewerkerZaaks(new HashSet<>(Set.of(zaakBack)));
        assertThat(medewerker.getAfhandelendmedewerkerZaaks()).containsOnly(zaakBack);
        assertThat(zaakBack.getAfhandelendmedewerkerMedewerkers()).containsOnly(medewerker);

        medewerker.setAfhandelendmedewerkerZaaks(new HashSet<>());
        assertThat(medewerker.getAfhandelendmedewerkerZaaks()).doesNotContain(zaakBack);
        assertThat(zaakBack.getAfhandelendmedewerkerMedewerkers()).doesNotContain(medewerker);
    }
}
