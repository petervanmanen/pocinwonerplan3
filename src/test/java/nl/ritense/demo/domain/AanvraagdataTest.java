package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanvraagdataTestSamples.*;
import static nl.ritense.demo.domain.FormuliersoortveldTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AanvraagdataTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aanvraagdata.class);
        Aanvraagdata aanvraagdata1 = getAanvraagdataSample1();
        Aanvraagdata aanvraagdata2 = new Aanvraagdata();
        assertThat(aanvraagdata1).isNotEqualTo(aanvraagdata2);

        aanvraagdata2.setId(aanvraagdata1.getId());
        assertThat(aanvraagdata1).isEqualTo(aanvraagdata2);

        aanvraagdata2 = getAanvraagdataSample2();
        assertThat(aanvraagdata1).isNotEqualTo(aanvraagdata2);
    }

    @Test
    void isconformFormuliersoortveldTest() throws Exception {
        Aanvraagdata aanvraagdata = getAanvraagdataRandomSampleGenerator();
        Formuliersoortveld formuliersoortveldBack = getFormuliersoortveldRandomSampleGenerator();

        aanvraagdata.setIsconformFormuliersoortveld(formuliersoortveldBack);
        assertThat(aanvraagdata.getIsconformFormuliersoortveld()).isEqualTo(formuliersoortveldBack);

        aanvraagdata.isconformFormuliersoortveld(null);
        assertThat(aanvraagdata.getIsconformFormuliersoortveld()).isNull();
    }
}
