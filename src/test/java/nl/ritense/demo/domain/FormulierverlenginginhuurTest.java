package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FormulierverlenginginhuurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormulierverlenginginhuurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Formulierverlenginginhuur.class);
        Formulierverlenginginhuur formulierverlenginginhuur1 = getFormulierverlenginginhuurSample1();
        Formulierverlenginginhuur formulierverlenginginhuur2 = new Formulierverlenginginhuur();
        assertThat(formulierverlenginginhuur1).isNotEqualTo(formulierverlenginginhuur2);

        formulierverlenginginhuur2.setId(formulierverlenginginhuur1.getId());
        assertThat(formulierverlenginginhuur1).isEqualTo(formulierverlenginginhuur2);

        formulierverlenginginhuur2 = getFormulierverlenginginhuurSample2();
        assertThat(formulierverlenginginhuur1).isNotEqualTo(formulierverlenginginhuur2);
    }
}
