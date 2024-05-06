package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AdresseerbaarobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdresseerbaarobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adresseerbaarobject.class);
        Adresseerbaarobject adresseerbaarobject1 = getAdresseerbaarobjectSample1();
        Adresseerbaarobject adresseerbaarobject2 = new Adresseerbaarobject();
        assertThat(adresseerbaarobject1).isNotEqualTo(adresseerbaarobject2);

        adresseerbaarobject2.setId(adresseerbaarobject1.getId());
        assertThat(adresseerbaarobject1).isEqualTo(adresseerbaarobject2);

        adresseerbaarobject2 = getAdresseerbaarobjectSample2();
        assertThat(adresseerbaarobject1).isNotEqualTo(adresseerbaarobject2);
    }
}
