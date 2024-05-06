package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AdresseerbaarobjectaanduidingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdresseerbaarobjectaanduidingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adresseerbaarobjectaanduiding.class);
        Adresseerbaarobjectaanduiding adresseerbaarobjectaanduiding1 = getAdresseerbaarobjectaanduidingSample1();
        Adresseerbaarobjectaanduiding adresseerbaarobjectaanduiding2 = new Adresseerbaarobjectaanduiding();
        assertThat(adresseerbaarobjectaanduiding1).isNotEqualTo(adresseerbaarobjectaanduiding2);

        adresseerbaarobjectaanduiding2.setId(adresseerbaarobjectaanduiding1.getId());
        assertThat(adresseerbaarobjectaanduiding1).isEqualTo(adresseerbaarobjectaanduiding2);

        adresseerbaarobjectaanduiding2 = getAdresseerbaarobjectaanduidingSample2();
        assertThat(adresseerbaarobjectaanduiding1).isNotEqualTo(adresseerbaarobjectaanduiding2);
    }
}
