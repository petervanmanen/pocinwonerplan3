package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FinancielesituatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FinancielesituatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Financielesituatie.class);
        Financielesituatie financielesituatie1 = getFinancielesituatieSample1();
        Financielesituatie financielesituatie2 = new Financielesituatie();
        assertThat(financielesituatie1).isNotEqualTo(financielesituatie2);

        financielesituatie2.setId(financielesituatie1.getId());
        assertThat(financielesituatie1).isEqualTo(financielesituatie2);

        financielesituatie2 = getFinancielesituatieSample2();
        assertThat(financielesituatie1).isNotEqualTo(financielesituatie2);
    }
}
