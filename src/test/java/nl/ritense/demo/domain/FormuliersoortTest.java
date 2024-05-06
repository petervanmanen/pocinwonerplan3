package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FormuliersoortTestSamples.*;
import static nl.ritense.demo.domain.FormuliersoortveldTestSamples.*;
import static nl.ritense.demo.domain.ZaaktypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormuliersoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Formuliersoort.class);
        Formuliersoort formuliersoort1 = getFormuliersoortSample1();
        Formuliersoort formuliersoort2 = new Formuliersoort();
        assertThat(formuliersoort1).isNotEqualTo(formuliersoort2);

        formuliersoort2.setId(formuliersoort1.getId());
        assertThat(formuliersoort1).isEqualTo(formuliersoort2);

        formuliersoort2 = getFormuliersoortSample2();
        assertThat(formuliersoort1).isNotEqualTo(formuliersoort2);
    }

    @Test
    void heeftveldenFormuliersoortveldTest() throws Exception {
        Formuliersoort formuliersoort = getFormuliersoortRandomSampleGenerator();
        Formuliersoortveld formuliersoortveldBack = getFormuliersoortveldRandomSampleGenerator();

        formuliersoort.addHeeftveldenFormuliersoortveld(formuliersoortveldBack);
        assertThat(formuliersoort.getHeeftveldenFormuliersoortvelds()).containsOnly(formuliersoortveldBack);
        assertThat(formuliersoortveldBack.getHeeftveldenFormuliersoort()).isEqualTo(formuliersoort);

        formuliersoort.removeHeeftveldenFormuliersoortveld(formuliersoortveldBack);
        assertThat(formuliersoort.getHeeftveldenFormuliersoortvelds()).doesNotContain(formuliersoortveldBack);
        assertThat(formuliersoortveldBack.getHeeftveldenFormuliersoort()).isNull();

        formuliersoort.heeftveldenFormuliersoortvelds(new HashSet<>(Set.of(formuliersoortveldBack)));
        assertThat(formuliersoort.getHeeftveldenFormuliersoortvelds()).containsOnly(formuliersoortveldBack);
        assertThat(formuliersoortveldBack.getHeeftveldenFormuliersoort()).isEqualTo(formuliersoort);

        formuliersoort.setHeeftveldenFormuliersoortvelds(new HashSet<>());
        assertThat(formuliersoort.getHeeftveldenFormuliersoortvelds()).doesNotContain(formuliersoortveldBack);
        assertThat(formuliersoortveldBack.getHeeftveldenFormuliersoort()).isNull();
    }

    @Test
    void isaanleidingvoorZaaktypeTest() throws Exception {
        Formuliersoort formuliersoort = getFormuliersoortRandomSampleGenerator();
        Zaaktype zaaktypeBack = getZaaktypeRandomSampleGenerator();

        formuliersoort.addIsaanleidingvoorZaaktype(zaaktypeBack);
        assertThat(formuliersoort.getIsaanleidingvoorZaaktypes()).containsOnly(zaaktypeBack);

        formuliersoort.removeIsaanleidingvoorZaaktype(zaaktypeBack);
        assertThat(formuliersoort.getIsaanleidingvoorZaaktypes()).doesNotContain(zaaktypeBack);

        formuliersoort.isaanleidingvoorZaaktypes(new HashSet<>(Set.of(zaaktypeBack)));
        assertThat(formuliersoort.getIsaanleidingvoorZaaktypes()).containsOnly(zaaktypeBack);

        formuliersoort.setIsaanleidingvoorZaaktypes(new HashSet<>());
        assertThat(formuliersoort.getIsaanleidingvoorZaaktypes()).doesNotContain(zaaktypeBack);
    }
}
