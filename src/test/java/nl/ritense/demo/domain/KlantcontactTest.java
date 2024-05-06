package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BalieafspraakTestSamples.*;
import static nl.ritense.demo.domain.BetrokkeneTestSamples.*;
import static nl.ritense.demo.domain.KlantcontactTestSamples.*;
import static nl.ritense.demo.domain.MedewerkerTestSamples.*;
import static nl.ritense.demo.domain.TelefoononderwerpTestSamples.*;
import static nl.ritense.demo.domain.TelefoontjeTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KlantcontactTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Klantcontact.class);
        Klantcontact klantcontact1 = getKlantcontactSample1();
        Klantcontact klantcontact2 = new Klantcontact();
        assertThat(klantcontact1).isNotEqualTo(klantcontact2);

        klantcontact2.setId(klantcontact1.getId());
        assertThat(klantcontact1).isEqualTo(klantcontact2);

        klantcontact2 = getKlantcontactSample2();
        assertThat(klantcontact1).isNotEqualTo(klantcontact2);
    }

    @Test
    void heeftklantcontactenBetrokkeneTest() throws Exception {
        Klantcontact klantcontact = getKlantcontactRandomSampleGenerator();
        Betrokkene betrokkeneBack = getBetrokkeneRandomSampleGenerator();

        klantcontact.setHeeftklantcontactenBetrokkene(betrokkeneBack);
        assertThat(klantcontact.getHeeftklantcontactenBetrokkene()).isEqualTo(betrokkeneBack);

        klantcontact.heeftklantcontactenBetrokkene(null);
        assertThat(klantcontact.getHeeftklantcontactenBetrokkene()).isNull();
    }

    @Test
    void heeftbetrekkingopZaakTest() throws Exception {
        Klantcontact klantcontact = getKlantcontactRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        klantcontact.setHeeftbetrekkingopZaak(zaakBack);
        assertThat(klantcontact.getHeeftbetrekkingopZaak()).isEqualTo(zaakBack);

        klantcontact.heeftbetrekkingopZaak(null);
        assertThat(klantcontact.getHeeftbetrekkingopZaak()).isNull();
    }

    @Test
    void isgevoerddoorMedewerkerTest() throws Exception {
        Klantcontact klantcontact = getKlantcontactRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        klantcontact.setIsgevoerddoorMedewerker(medewerkerBack);
        assertThat(klantcontact.getIsgevoerddoorMedewerker()).isEqualTo(medewerkerBack);

        klantcontact.isgevoerddoorMedewerker(null);
        assertThat(klantcontact.getIsgevoerddoorMedewerker()).isNull();
    }

    @Test
    void mondtuitinBalieafspraakTest() throws Exception {
        Klantcontact klantcontact = getKlantcontactRandomSampleGenerator();
        Balieafspraak balieafspraakBack = getBalieafspraakRandomSampleGenerator();

        klantcontact.setMondtuitinBalieafspraak(balieafspraakBack);
        assertThat(klantcontact.getMondtuitinBalieafspraak()).isEqualTo(balieafspraakBack);
        assertThat(balieafspraakBack.getMondtuitinKlantcontact()).isEqualTo(klantcontact);

        klantcontact.mondtuitinBalieafspraak(null);
        assertThat(klantcontact.getMondtuitinBalieafspraak()).isNull();
        assertThat(balieafspraakBack.getMondtuitinKlantcontact()).isNull();
    }

    @Test
    void heeftTelefoononderwerpTest() throws Exception {
        Klantcontact klantcontact = getKlantcontactRandomSampleGenerator();
        Telefoononderwerp telefoononderwerpBack = getTelefoononderwerpRandomSampleGenerator();

        klantcontact.setHeeftTelefoononderwerp(telefoononderwerpBack);
        assertThat(klantcontact.getHeeftTelefoononderwerp()).isEqualTo(telefoononderwerpBack);

        klantcontact.heeftTelefoononderwerp(null);
        assertThat(klantcontact.getHeeftTelefoononderwerp()).isNull();
    }

    @Test
    void mondtuitinTelefoontjeTest() throws Exception {
        Klantcontact klantcontact = getKlantcontactRandomSampleGenerator();
        Telefoontje telefoontjeBack = getTelefoontjeRandomSampleGenerator();

        klantcontact.setMondtuitinTelefoontje(telefoontjeBack);
        assertThat(klantcontact.getMondtuitinTelefoontje()).isEqualTo(telefoontjeBack);

        klantcontact.mondtuitinTelefoontje(null);
        assertThat(klantcontact.getMondtuitinTelefoontje()).isNull();
    }
}
