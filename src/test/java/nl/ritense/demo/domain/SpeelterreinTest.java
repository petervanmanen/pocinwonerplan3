package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SpeelterreinTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpeelterreinTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Speelterrein.class);
        Speelterrein speelterrein1 = getSpeelterreinSample1();
        Speelterrein speelterrein2 = new Speelterrein();
        assertThat(speelterrein1).isNotEqualTo(speelterrein2);

        speelterrein2.setId(speelterrein1.getId());
        assertThat(speelterrein1).isEqualTo(speelterrein2);

        speelterrein2 = getSpeelterreinSample2();
        assertThat(speelterrein1).isNotEqualTo(speelterrein2);
    }
}
