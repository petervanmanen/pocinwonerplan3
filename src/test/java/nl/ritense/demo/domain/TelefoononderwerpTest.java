package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KlantcontactTestSamples.*;
import static nl.ritense.demo.domain.TelefoononderwerpTestSamples.*;
import static nl.ritense.demo.domain.TelefoontjeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelefoononderwerpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Telefoononderwerp.class);
        Telefoononderwerp telefoononderwerp1 = getTelefoononderwerpSample1();
        Telefoononderwerp telefoononderwerp2 = new Telefoononderwerp();
        assertThat(telefoononderwerp1).isNotEqualTo(telefoononderwerp2);

        telefoononderwerp2.setId(telefoononderwerp1.getId());
        assertThat(telefoononderwerp1).isEqualTo(telefoononderwerp2);

        telefoononderwerp2 = getTelefoononderwerpSample2();
        assertThat(telefoononderwerp1).isNotEqualTo(telefoononderwerp2);
    }

    @Test
    void heeftKlantcontactTest() throws Exception {
        Telefoononderwerp telefoononderwerp = getTelefoononderwerpRandomSampleGenerator();
        Klantcontact klantcontactBack = getKlantcontactRandomSampleGenerator();

        telefoononderwerp.addHeeftKlantcontact(klantcontactBack);
        assertThat(telefoononderwerp.getHeeftKlantcontacts()).containsOnly(klantcontactBack);
        assertThat(klantcontactBack.getHeeftTelefoononderwerp()).isEqualTo(telefoononderwerp);

        telefoononderwerp.removeHeeftKlantcontact(klantcontactBack);
        assertThat(telefoononderwerp.getHeeftKlantcontacts()).doesNotContain(klantcontactBack);
        assertThat(klantcontactBack.getHeeftTelefoononderwerp()).isNull();

        telefoononderwerp.heeftKlantcontacts(new HashSet<>(Set.of(klantcontactBack)));
        assertThat(telefoononderwerp.getHeeftKlantcontacts()).containsOnly(klantcontactBack);
        assertThat(klantcontactBack.getHeeftTelefoononderwerp()).isEqualTo(telefoononderwerp);

        telefoononderwerp.setHeeftKlantcontacts(new HashSet<>());
        assertThat(telefoononderwerp.getHeeftKlantcontacts()).doesNotContain(klantcontactBack);
        assertThat(klantcontactBack.getHeeftTelefoononderwerp()).isNull();
    }

    @Test
    void heeftTelefoontjeTest() throws Exception {
        Telefoononderwerp telefoononderwerp = getTelefoononderwerpRandomSampleGenerator();
        Telefoontje telefoontjeBack = getTelefoontjeRandomSampleGenerator();

        telefoononderwerp.addHeeftTelefoontje(telefoontjeBack);
        assertThat(telefoononderwerp.getHeeftTelefoontjes()).containsOnly(telefoontjeBack);
        assertThat(telefoontjeBack.getHeeftTelefoononderwerp()).isEqualTo(telefoononderwerp);

        telefoononderwerp.removeHeeftTelefoontje(telefoontjeBack);
        assertThat(telefoononderwerp.getHeeftTelefoontjes()).doesNotContain(telefoontjeBack);
        assertThat(telefoontjeBack.getHeeftTelefoononderwerp()).isNull();

        telefoononderwerp.heeftTelefoontjes(new HashSet<>(Set.of(telefoontjeBack)));
        assertThat(telefoononderwerp.getHeeftTelefoontjes()).containsOnly(telefoontjeBack);
        assertThat(telefoontjeBack.getHeeftTelefoononderwerp()).isEqualTo(telefoononderwerp);

        telefoononderwerp.setHeeftTelefoontjes(new HashSet<>());
        assertThat(telefoononderwerp.getHeeftTelefoontjes()).doesNotContain(telefoontjeBack);
        assertThat(telefoontjeBack.getHeeftTelefoononderwerp()).isNull();
    }
}
