package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanvraagdataTestSamples.*;
import static nl.ritense.demo.domain.FormuliersoortTestSamples.*;
import static nl.ritense.demo.domain.FormuliersoortveldTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormuliersoortveldTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Formuliersoortveld.class);
        Formuliersoortveld formuliersoortveld1 = getFormuliersoortveldSample1();
        Formuliersoortveld formuliersoortveld2 = new Formuliersoortveld();
        assertThat(formuliersoortveld1).isNotEqualTo(formuliersoortveld2);

        formuliersoortveld2.setId(formuliersoortveld1.getId());
        assertThat(formuliersoortveld1).isEqualTo(formuliersoortveld2);

        formuliersoortveld2 = getFormuliersoortveldSample2();
        assertThat(formuliersoortveld1).isNotEqualTo(formuliersoortveld2);
    }

    @Test
    void heeftveldenFormuliersoortTest() throws Exception {
        Formuliersoortveld formuliersoortveld = getFormuliersoortveldRandomSampleGenerator();
        Formuliersoort formuliersoortBack = getFormuliersoortRandomSampleGenerator();

        formuliersoortveld.setHeeftveldenFormuliersoort(formuliersoortBack);
        assertThat(formuliersoortveld.getHeeftveldenFormuliersoort()).isEqualTo(formuliersoortBack);

        formuliersoortveld.heeftveldenFormuliersoort(null);
        assertThat(formuliersoortveld.getHeeftveldenFormuliersoort()).isNull();
    }

    @Test
    void isconformAanvraagdataTest() throws Exception {
        Formuliersoortveld formuliersoortveld = getFormuliersoortveldRandomSampleGenerator();
        Aanvraagdata aanvraagdataBack = getAanvraagdataRandomSampleGenerator();

        formuliersoortveld.addIsconformAanvraagdata(aanvraagdataBack);
        assertThat(formuliersoortveld.getIsconformAanvraagdata()).containsOnly(aanvraagdataBack);
        assertThat(aanvraagdataBack.getIsconformFormuliersoortveld()).isEqualTo(formuliersoortveld);

        formuliersoortveld.removeIsconformAanvraagdata(aanvraagdataBack);
        assertThat(formuliersoortveld.getIsconformAanvraagdata()).doesNotContain(aanvraagdataBack);
        assertThat(aanvraagdataBack.getIsconformFormuliersoortveld()).isNull();

        formuliersoortveld.isconformAanvraagdata(new HashSet<>(Set.of(aanvraagdataBack)));
        assertThat(formuliersoortveld.getIsconformAanvraagdata()).containsOnly(aanvraagdataBack);
        assertThat(aanvraagdataBack.getIsconformFormuliersoortveld()).isEqualTo(formuliersoortveld);

        formuliersoortveld.setIsconformAanvraagdata(new HashSet<>());
        assertThat(formuliersoortveld.getIsconformAanvraagdata()).doesNotContain(aanvraagdataBack);
        assertThat(aanvraagdataBack.getIsconformFormuliersoortveld()).isNull();
    }
}
