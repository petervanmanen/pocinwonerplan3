package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OverigeadresseerbaarobjectaanduidingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OverigeadresseerbaarobjectaanduidingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Overigeadresseerbaarobjectaanduiding.class);
        Overigeadresseerbaarobjectaanduiding overigeadresseerbaarobjectaanduiding1 = getOverigeadresseerbaarobjectaanduidingSample1();
        Overigeadresseerbaarobjectaanduiding overigeadresseerbaarobjectaanduiding2 = new Overigeadresseerbaarobjectaanduiding();
        assertThat(overigeadresseerbaarobjectaanduiding1).isNotEqualTo(overigeadresseerbaarobjectaanduiding2);

        overigeadresseerbaarobjectaanduiding2.setId(overigeadresseerbaarobjectaanduiding1.getId());
        assertThat(overigeadresseerbaarobjectaanduiding1).isEqualTo(overigeadresseerbaarobjectaanduiding2);

        overigeadresseerbaarobjectaanduiding2 = getOverigeadresseerbaarobjectaanduidingSample2();
        assertThat(overigeadresseerbaarobjectaanduiding1).isNotEqualTo(overigeadresseerbaarobjectaanduiding2);
    }
}
