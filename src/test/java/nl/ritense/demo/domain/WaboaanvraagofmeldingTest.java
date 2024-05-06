package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.WaboaanvraagofmeldingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WaboaanvraagofmeldingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Waboaanvraagofmelding.class);
        Waboaanvraagofmelding waboaanvraagofmelding1 = getWaboaanvraagofmeldingSample1();
        Waboaanvraagofmelding waboaanvraagofmelding2 = new Waboaanvraagofmelding();
        assertThat(waboaanvraagofmelding1).isNotEqualTo(waboaanvraagofmelding2);

        waboaanvraagofmelding2.setId(waboaanvraagofmelding1.getId());
        assertThat(waboaanvraagofmelding1).isEqualTo(waboaanvraagofmelding2);

        waboaanvraagofmelding2 = getWaboaanvraagofmeldingSample2();
        assertThat(waboaanvraagofmelding1).isNotEqualTo(waboaanvraagofmelding2);
    }
}
