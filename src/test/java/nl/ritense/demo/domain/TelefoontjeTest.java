package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KlantcontactTestSamples.*;
import static nl.ritense.demo.domain.TelefoononderwerpTestSamples.*;
import static nl.ritense.demo.domain.TelefoonstatusTestSamples.*;
import static nl.ritense.demo.domain.TelefoontjeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelefoontjeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Telefoontje.class);
        Telefoontje telefoontje1 = getTelefoontjeSample1();
        Telefoontje telefoontje2 = new Telefoontje();
        assertThat(telefoontje1).isNotEqualTo(telefoontje2);

        telefoontje2.setId(telefoontje1.getId());
        assertThat(telefoontje1).isEqualTo(telefoontje2);

        telefoontje2 = getTelefoontjeSample2();
        assertThat(telefoontje1).isNotEqualTo(telefoontje2);
    }

    @Test
    void mondtuitinKlantcontactTest() throws Exception {
        Telefoontje telefoontje = getTelefoontjeRandomSampleGenerator();
        Klantcontact klantcontactBack = getKlantcontactRandomSampleGenerator();

        telefoontje.addMondtuitinKlantcontact(klantcontactBack);
        assertThat(telefoontje.getMondtuitinKlantcontacts()).containsOnly(klantcontactBack);
        assertThat(klantcontactBack.getMondtuitinTelefoontje()).isEqualTo(telefoontje);

        telefoontje.removeMondtuitinKlantcontact(klantcontactBack);
        assertThat(telefoontje.getMondtuitinKlantcontacts()).doesNotContain(klantcontactBack);
        assertThat(klantcontactBack.getMondtuitinTelefoontje()).isNull();

        telefoontje.mondtuitinKlantcontacts(new HashSet<>(Set.of(klantcontactBack)));
        assertThat(telefoontje.getMondtuitinKlantcontacts()).containsOnly(klantcontactBack);
        assertThat(klantcontactBack.getMondtuitinTelefoontje()).isEqualTo(telefoontje);

        telefoontje.setMondtuitinKlantcontacts(new HashSet<>());
        assertThat(telefoontje.getMondtuitinKlantcontacts()).doesNotContain(klantcontactBack);
        assertThat(klantcontactBack.getMondtuitinTelefoontje()).isNull();
    }

    @Test
    void heeftTelefoonstatusTest() throws Exception {
        Telefoontje telefoontje = getTelefoontjeRandomSampleGenerator();
        Telefoonstatus telefoonstatusBack = getTelefoonstatusRandomSampleGenerator();

        telefoontje.setHeeftTelefoonstatus(telefoonstatusBack);
        assertThat(telefoontje.getHeeftTelefoonstatus()).isEqualTo(telefoonstatusBack);

        telefoontje.heeftTelefoonstatus(null);
        assertThat(telefoontje.getHeeftTelefoonstatus()).isNull();
    }

    @Test
    void heeftTelefoononderwerpTest() throws Exception {
        Telefoontje telefoontje = getTelefoontjeRandomSampleGenerator();
        Telefoononderwerp telefoononderwerpBack = getTelefoononderwerpRandomSampleGenerator();

        telefoontje.setHeeftTelefoononderwerp(telefoononderwerpBack);
        assertThat(telefoontje.getHeeftTelefoononderwerp()).isEqualTo(telefoononderwerpBack);

        telefoontje.heeftTelefoononderwerp(null);
        assertThat(telefoontje.getHeeftTelefoononderwerp()).isNull();
    }
}
