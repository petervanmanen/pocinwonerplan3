package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.TelefoonstatusTestSamples.*;
import static nl.ritense.demo.domain.TelefoontjeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelefoonstatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Telefoonstatus.class);
        Telefoonstatus telefoonstatus1 = getTelefoonstatusSample1();
        Telefoonstatus telefoonstatus2 = new Telefoonstatus();
        assertThat(telefoonstatus1).isNotEqualTo(telefoonstatus2);

        telefoonstatus2.setId(telefoonstatus1.getId());
        assertThat(telefoonstatus1).isEqualTo(telefoonstatus2);

        telefoonstatus2 = getTelefoonstatusSample2();
        assertThat(telefoonstatus1).isNotEqualTo(telefoonstatus2);
    }

    @Test
    void heeftTelefoontjeTest() throws Exception {
        Telefoonstatus telefoonstatus = getTelefoonstatusRandomSampleGenerator();
        Telefoontje telefoontjeBack = getTelefoontjeRandomSampleGenerator();

        telefoonstatus.addHeeftTelefoontje(telefoontjeBack);
        assertThat(telefoonstatus.getHeeftTelefoontjes()).containsOnly(telefoontjeBack);
        assertThat(telefoontjeBack.getHeeftTelefoonstatus()).isEqualTo(telefoonstatus);

        telefoonstatus.removeHeeftTelefoontje(telefoontjeBack);
        assertThat(telefoonstatus.getHeeftTelefoontjes()).doesNotContain(telefoontjeBack);
        assertThat(telefoontjeBack.getHeeftTelefoonstatus()).isNull();

        telefoonstatus.heeftTelefoontjes(new HashSet<>(Set.of(telefoontjeBack)));
        assertThat(telefoonstatus.getHeeftTelefoontjes()).containsOnly(telefoontjeBack);
        assertThat(telefoontjeBack.getHeeftTelefoonstatus()).isEqualTo(telefoonstatus);

        telefoonstatus.setHeeftTelefoontjes(new HashSet<>());
        assertThat(telefoonstatus.getHeeftTelefoontjes()).doesNotContain(telefoontjeBack);
        assertThat(telefoontjeBack.getHeeftTelefoonstatus()).isNull();
    }
}
