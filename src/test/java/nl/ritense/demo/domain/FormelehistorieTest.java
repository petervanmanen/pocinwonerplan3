package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FormelehistorieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FormelehistorieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Formelehistorie.class);
        Formelehistorie formelehistorie1 = getFormelehistorieSample1();
        Formelehistorie formelehistorie2 = new Formelehistorie();
        assertThat(formelehistorie1).isNotEqualTo(formelehistorie2);

        formelehistorie2.setId(formelehistorie1.getId());
        assertThat(formelehistorie1).isEqualTo(formelehistorie2);

        formelehistorie2 = getFormelehistorieSample2();
        assertThat(formelehistorie1).isNotEqualTo(formelehistorie2);
    }
}
