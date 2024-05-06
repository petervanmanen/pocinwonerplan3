package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AfspraakstatusTestSamples.*;
import static nl.ritense.demo.domain.BalieafspraakTestSamples.*;
import static nl.ritense.demo.domain.KlantcontactTestSamples.*;
import static nl.ritense.demo.domain.MedewerkerTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BalieafspraakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Balieafspraak.class);
        Balieafspraak balieafspraak1 = getBalieafspraakSample1();
        Balieafspraak balieafspraak2 = new Balieafspraak();
        assertThat(balieafspraak1).isNotEqualTo(balieafspraak2);

        balieafspraak2.setId(balieafspraak1.getId());
        assertThat(balieafspraak1).isEqualTo(balieafspraak2);

        balieafspraak2 = getBalieafspraakSample2();
        assertThat(balieafspraak1).isNotEqualTo(balieafspraak2);
    }

    @Test
    void mondtuitinKlantcontactTest() throws Exception {
        Balieafspraak balieafspraak = getBalieafspraakRandomSampleGenerator();
        Klantcontact klantcontactBack = getKlantcontactRandomSampleGenerator();

        balieafspraak.setMondtuitinKlantcontact(klantcontactBack);
        assertThat(balieafspraak.getMondtuitinKlantcontact()).isEqualTo(klantcontactBack);

        balieafspraak.mondtuitinKlantcontact(null);
        assertThat(balieafspraak.getMondtuitinKlantcontact()).isNull();
    }

    @Test
    void heeftAfspraakstatusTest() throws Exception {
        Balieafspraak balieafspraak = getBalieafspraakRandomSampleGenerator();
        Afspraakstatus afspraakstatusBack = getAfspraakstatusRandomSampleGenerator();

        balieafspraak.setHeeftAfspraakstatus(afspraakstatusBack);
        assertThat(balieafspraak.getHeeftAfspraakstatus()).isEqualTo(afspraakstatusBack);

        balieafspraak.heeftAfspraakstatus(null);
        assertThat(balieafspraak.getHeeftAfspraakstatus()).isNull();
    }

    @Test
    void metMedewerkerTest() throws Exception {
        Balieafspraak balieafspraak = getBalieafspraakRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        balieafspraak.setMetMedewerker(medewerkerBack);
        assertThat(balieafspraak.getMetMedewerker()).isEqualTo(medewerkerBack);

        balieafspraak.metMedewerker(null);
        assertThat(balieafspraak.getMetMedewerker()).isNull();
    }

    @Test
    void heeftbetrekkingopZaakTest() throws Exception {
        Balieafspraak balieafspraak = getBalieafspraakRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        balieafspraak.setHeeftbetrekkingopZaak(zaakBack);
        assertThat(balieafspraak.getHeeftbetrekkingopZaak()).isEqualTo(zaakBack);

        balieafspraak.heeftbetrekkingopZaak(null);
        assertThat(balieafspraak.getHeeftbetrekkingopZaak()).isNull();
    }
}
