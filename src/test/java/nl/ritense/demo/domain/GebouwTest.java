package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GebouwTestSamples.*;
import static nl.ritense.demo.domain.PlanTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GebouwTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gebouw.class);
        Gebouw gebouw1 = getGebouwSample1();
        Gebouw gebouw2 = new Gebouw();
        assertThat(gebouw1).isNotEqualTo(gebouw2);

        gebouw2.setId(gebouw1.getId());
        assertThat(gebouw1).isEqualTo(gebouw2);

        gebouw2 = getGebouwSample2();
        assertThat(gebouw1).isNotEqualTo(gebouw2);
    }

    @Test
    void bestaatuitPlanTest() throws Exception {
        Gebouw gebouw = getGebouwRandomSampleGenerator();
        Plan planBack = getPlanRandomSampleGenerator();

        gebouw.setBestaatuitPlan(planBack);
        assertThat(gebouw.getBestaatuitPlan()).isEqualTo(planBack);

        gebouw.bestaatuitPlan(null);
        assertThat(gebouw.getBestaatuitPlan()).isNull();
    }
}
