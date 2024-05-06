package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.IndividueelkeuzebudgetTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndividueelkeuzebudgetTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Individueelkeuzebudget.class);
        Individueelkeuzebudget individueelkeuzebudget1 = getIndividueelkeuzebudgetSample1();
        Individueelkeuzebudget individueelkeuzebudget2 = new Individueelkeuzebudget();
        assertThat(individueelkeuzebudget1).isNotEqualTo(individueelkeuzebudget2);

        individueelkeuzebudget2.setId(individueelkeuzebudget1.getId());
        assertThat(individueelkeuzebudget1).isEqualTo(individueelkeuzebudget2);

        individueelkeuzebudget2 = getIndividueelkeuzebudgetSample2();
        assertThat(individueelkeuzebudget1).isNotEqualTo(individueelkeuzebudget2);
    }
}
