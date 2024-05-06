package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BudgetuitputtingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BudgetuitputtingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Budgetuitputting.class);
        Budgetuitputting budgetuitputting1 = getBudgetuitputtingSample1();
        Budgetuitputting budgetuitputting2 = new Budgetuitputting();
        assertThat(budgetuitputting1).isNotEqualTo(budgetuitputting2);

        budgetuitputting2.setId(budgetuitputting1.getId());
        assertThat(budgetuitputting1).isEqualTo(budgetuitputting2);

        budgetuitputting2 = getBudgetuitputtingSample2();
        assertThat(budgetuitputting1).isNotEqualTo(budgetuitputting2);
    }
}
