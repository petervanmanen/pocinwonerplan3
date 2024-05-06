package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VthaanvraagofmeldingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VthaanvraagofmeldingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vthaanvraagofmelding.class);
        Vthaanvraagofmelding vthaanvraagofmelding1 = getVthaanvraagofmeldingSample1();
        Vthaanvraagofmelding vthaanvraagofmelding2 = new Vthaanvraagofmelding();
        assertThat(vthaanvraagofmelding1).isNotEqualTo(vthaanvraagofmelding2);

        vthaanvraagofmelding2.setId(vthaanvraagofmelding1.getId());
        assertThat(vthaanvraagofmelding1).isEqualTo(vthaanvraagofmelding2);

        vthaanvraagofmelding2 = getVthaanvraagofmeldingSample2();
        assertThat(vthaanvraagofmelding1).isNotEqualTo(vthaanvraagofmelding2);
    }
}
