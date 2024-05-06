package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PlanTestSamples.*;
import static nl.ritense.demo.domain.ProjectleiderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectleiderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projectleider.class);
        Projectleider projectleider1 = getProjectleiderSample1();
        Projectleider projectleider2 = new Projectleider();
        assertThat(projectleider1).isNotEqualTo(projectleider2);

        projectleider2.setId(projectleider1.getId());
        assertThat(projectleider1).isEqualTo(projectleider2);

        projectleider2 = getProjectleiderSample2();
        assertThat(projectleider1).isNotEqualTo(projectleider2);
    }

    @Test
    void isprojectleidervanPlanTest() throws Exception {
        Projectleider projectleider = getProjectleiderRandomSampleGenerator();
        Plan planBack = getPlanRandomSampleGenerator();

        projectleider.addIsprojectleidervanPlan(planBack);
        assertThat(projectleider.getIsprojectleidervanPlans()).containsOnly(planBack);
        assertThat(planBack.getIsprojectleidervanProjectleider()).isEqualTo(projectleider);

        projectleider.removeIsprojectleidervanPlan(planBack);
        assertThat(projectleider.getIsprojectleidervanPlans()).doesNotContain(planBack);
        assertThat(planBack.getIsprojectleidervanProjectleider()).isNull();

        projectleider.isprojectleidervanPlans(new HashSet<>(Set.of(planBack)));
        assertThat(projectleider.getIsprojectleidervanPlans()).containsOnly(planBack);
        assertThat(planBack.getIsprojectleidervanProjectleider()).isEqualTo(projectleider);

        projectleider.setIsprojectleidervanPlans(new HashSet<>());
        assertThat(projectleider.getIsprojectleidervanPlans()).doesNotContain(planBack);
        assertThat(planBack.getIsprojectleidervanProjectleider()).isNull();
    }
}
