package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PlanTestSamples.*;
import static nl.ritense.demo.domain.ProjectontwikkelaarTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectontwikkelaarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projectontwikkelaar.class);
        Projectontwikkelaar projectontwikkelaar1 = getProjectontwikkelaarSample1();
        Projectontwikkelaar projectontwikkelaar2 = new Projectontwikkelaar();
        assertThat(projectontwikkelaar1).isNotEqualTo(projectontwikkelaar2);

        projectontwikkelaar2.setId(projectontwikkelaar1.getId());
        assertThat(projectontwikkelaar1).isEqualTo(projectontwikkelaar2);

        projectontwikkelaar2 = getProjectontwikkelaarSample2();
        assertThat(projectontwikkelaar1).isNotEqualTo(projectontwikkelaar2);
    }

    @Test
    void heeftPlanTest() throws Exception {
        Projectontwikkelaar projectontwikkelaar = getProjectontwikkelaarRandomSampleGenerator();
        Plan planBack = getPlanRandomSampleGenerator();

        projectontwikkelaar.addHeeftPlan(planBack);
        assertThat(projectontwikkelaar.getHeeftPlans()).containsOnly(planBack);

        projectontwikkelaar.removeHeeftPlan(planBack);
        assertThat(projectontwikkelaar.getHeeftPlans()).doesNotContain(planBack);

        projectontwikkelaar.heeftPlans(new HashSet<>(Set.of(planBack)));
        assertThat(projectontwikkelaar.getHeeftPlans()).containsOnly(planBack);

        projectontwikkelaar.setHeeftPlans(new HashSet<>());
        assertThat(projectontwikkelaar.getHeeftPlans()).doesNotContain(planBack);
    }
}
