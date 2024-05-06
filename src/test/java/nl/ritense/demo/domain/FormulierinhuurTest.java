package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FormulierinhuurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormulierinhuurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Formulierinhuur.class);
        Formulierinhuur formulierinhuur1 = getFormulierinhuurSample1();
        Formulierinhuur formulierinhuur2 = new Formulierinhuur();
        assertThat(formulierinhuur1).isNotEqualTo(formulierinhuur2);

        formulierinhuur2.setId(formulierinhuur1.getId());
        assertThat(formulierinhuur1).isEqualTo(formulierinhuur2);

        formulierinhuur2 = getFormulierinhuurSample2();
        assertThat(formulierinhuur1).isNotEqualTo(formulierinhuur2);
    }
}
