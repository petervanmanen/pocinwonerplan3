package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerlofaanvraagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerlofaanvraagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verlofaanvraag.class);
        Verlofaanvraag verlofaanvraag1 = getVerlofaanvraagSample1();
        Verlofaanvraag verlofaanvraag2 = new Verlofaanvraag();
        assertThat(verlofaanvraag1).isNotEqualTo(verlofaanvraag2);

        verlofaanvraag2.setId(verlofaanvraag1.getId());
        assertThat(verlofaanvraag1).isEqualTo(verlofaanvraag2);

        verlofaanvraag2 = getVerlofaanvraagSample2();
        assertThat(verlofaanvraag1).isNotEqualTo(verlofaanvraag2);
    }
}
