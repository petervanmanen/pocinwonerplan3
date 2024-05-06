package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BetrokkeneTestSamples.*;
import static nl.ritense.demo.domain.KlantbeoordelingTestSamples.*;
import static nl.ritense.demo.domain.KlantcontactTestSamples.*;
import static nl.ritense.demo.domain.MedewerkerTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BetrokkeneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Betrokkene.class);
        Betrokkene betrokkene1 = getBetrokkeneSample1();
        Betrokkene betrokkene2 = new Betrokkene();
        assertThat(betrokkene1).isNotEqualTo(betrokkene2);

        betrokkene2.setId(betrokkene1.getId());
        assertThat(betrokkene1).isEqualTo(betrokkene2);

        betrokkene2 = getBetrokkeneSample2();
        assertThat(betrokkene1).isNotEqualTo(betrokkene2);
    }

    @Test
    void isMedewerkerTest() throws Exception {
        Betrokkene betrokkene = getBetrokkeneRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        betrokkene.setIsMedewerker(medewerkerBack);
        assertThat(betrokkene.getIsMedewerker()).isEqualTo(medewerkerBack);

        betrokkene.isMedewerker(null);
        assertThat(betrokkene.getIsMedewerker()).isNull();
    }

    @Test
    void doetKlantbeoordelingTest() throws Exception {
        Betrokkene betrokkene = getBetrokkeneRandomSampleGenerator();
        Klantbeoordeling klantbeoordelingBack = getKlantbeoordelingRandomSampleGenerator();

        betrokkene.addDoetKlantbeoordeling(klantbeoordelingBack);
        assertThat(betrokkene.getDoetKlantbeoordelings()).containsOnly(klantbeoordelingBack);
        assertThat(klantbeoordelingBack.getDoetBetrokkene()).isEqualTo(betrokkene);

        betrokkene.removeDoetKlantbeoordeling(klantbeoordelingBack);
        assertThat(betrokkene.getDoetKlantbeoordelings()).doesNotContain(klantbeoordelingBack);
        assertThat(klantbeoordelingBack.getDoetBetrokkene()).isNull();

        betrokkene.doetKlantbeoordelings(new HashSet<>(Set.of(klantbeoordelingBack)));
        assertThat(betrokkene.getDoetKlantbeoordelings()).containsOnly(klantbeoordelingBack);
        assertThat(klantbeoordelingBack.getDoetBetrokkene()).isEqualTo(betrokkene);

        betrokkene.setDoetKlantbeoordelings(new HashSet<>());
        assertThat(betrokkene.getDoetKlantbeoordelings()).doesNotContain(klantbeoordelingBack);
        assertThat(klantbeoordelingBack.getDoetBetrokkene()).isNull();
    }

    @Test
    void oefentuitZaakTest() throws Exception {
        Betrokkene betrokkene = getBetrokkeneRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        betrokkene.addOefentuitZaak(zaakBack);
        assertThat(betrokkene.getOefentuitZaaks()).containsOnly(zaakBack);

        betrokkene.removeOefentuitZaak(zaakBack);
        assertThat(betrokkene.getOefentuitZaaks()).doesNotContain(zaakBack);

        betrokkene.oefentuitZaaks(new HashSet<>(Set.of(zaakBack)));
        assertThat(betrokkene.getOefentuitZaaks()).containsOnly(zaakBack);

        betrokkene.setOefentuitZaaks(new HashSet<>());
        assertThat(betrokkene.getOefentuitZaaks()).doesNotContain(zaakBack);
    }

    @Test
    void heeftklantcontactenKlantcontactTest() throws Exception {
        Betrokkene betrokkene = getBetrokkeneRandomSampleGenerator();
        Klantcontact klantcontactBack = getKlantcontactRandomSampleGenerator();

        betrokkene.addHeeftklantcontactenKlantcontact(klantcontactBack);
        assertThat(betrokkene.getHeeftklantcontactenKlantcontacts()).containsOnly(klantcontactBack);
        assertThat(klantcontactBack.getHeeftklantcontactenBetrokkene()).isEqualTo(betrokkene);

        betrokkene.removeHeeftklantcontactenKlantcontact(klantcontactBack);
        assertThat(betrokkene.getHeeftklantcontactenKlantcontacts()).doesNotContain(klantcontactBack);
        assertThat(klantcontactBack.getHeeftklantcontactenBetrokkene()).isNull();

        betrokkene.heeftklantcontactenKlantcontacts(new HashSet<>(Set.of(klantcontactBack)));
        assertThat(betrokkene.getHeeftklantcontactenKlantcontacts()).containsOnly(klantcontactBack);
        assertThat(klantcontactBack.getHeeftklantcontactenBetrokkene()).isEqualTo(betrokkene);

        betrokkene.setHeeftklantcontactenKlantcontacts(new HashSet<>());
        assertThat(betrokkene.getHeeftklantcontactenKlantcontacts()).doesNotContain(klantcontactBack);
        assertThat(klantcontactBack.getHeeftklantcontactenBetrokkene()).isNull();
    }
}
