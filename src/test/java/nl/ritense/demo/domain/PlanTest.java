package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GebouwTestSamples.*;
import static nl.ritense.demo.domain.OmgevingsvergunningTestSamples.*;
import static nl.ritense.demo.domain.PlanTestSamples.*;
import static nl.ritense.demo.domain.ProgrammaTestSamples.*;
import static nl.ritense.demo.domain.ProjectleiderTestSamples.*;
import static nl.ritense.demo.domain.ProjectontwikkelaarTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Plan.class);
        Plan plan1 = getPlanSample1();
        Plan plan2 = new Plan();
        assertThat(plan1).isNotEqualTo(plan2);

        plan2.setId(plan1.getId());
        assertThat(plan1).isEqualTo(plan2);

        plan2 = getPlanSample2();
        assertThat(plan1).isNotEqualTo(plan2);
    }

    @Test
    void bestaatuitGebouwTest() throws Exception {
        Plan plan = getPlanRandomSampleGenerator();
        Gebouw gebouwBack = getGebouwRandomSampleGenerator();

        plan.addBestaatuitGebouw(gebouwBack);
        assertThat(plan.getBestaatuitGebouws()).containsOnly(gebouwBack);
        assertThat(gebouwBack.getBestaatuitPlan()).isEqualTo(plan);

        plan.removeBestaatuitGebouw(gebouwBack);
        assertThat(plan.getBestaatuitGebouws()).doesNotContain(gebouwBack);
        assertThat(gebouwBack.getBestaatuitPlan()).isNull();

        plan.bestaatuitGebouws(new HashSet<>(Set.of(gebouwBack)));
        assertThat(plan.getBestaatuitGebouws()).containsOnly(gebouwBack);
        assertThat(gebouwBack.getBestaatuitPlan()).isEqualTo(plan);

        plan.setBestaatuitGebouws(new HashSet<>());
        assertThat(plan.getBestaatuitGebouws()).doesNotContain(gebouwBack);
        assertThat(gebouwBack.getBestaatuitPlan()).isNull();
    }

    @Test
    void binnenprogrammaProgrammaTest() throws Exception {
        Plan plan = getPlanRandomSampleGenerator();
        Programma programmaBack = getProgrammaRandomSampleGenerator();

        plan.setBinnenprogrammaProgramma(programmaBack);
        assertThat(plan.getBinnenprogrammaProgramma()).isEqualTo(programmaBack);

        plan.binnenprogrammaProgramma(null);
        assertThat(plan.getBinnenprogrammaProgramma()).isNull();
    }

    @Test
    void isprojectleidervanProjectleiderTest() throws Exception {
        Plan plan = getPlanRandomSampleGenerator();
        Projectleider projectleiderBack = getProjectleiderRandomSampleGenerator();

        plan.setIsprojectleidervanProjectleider(projectleiderBack);
        assertThat(plan.getIsprojectleidervanProjectleider()).isEqualTo(projectleiderBack);

        plan.isprojectleidervanProjectleider(null);
        assertThat(plan.getIsprojectleidervanProjectleider()).isNull();
    }

    @Test
    void betrekkingopOmgevingsvergunningTest() throws Exception {
        Plan plan = getPlanRandomSampleGenerator();
        Omgevingsvergunning omgevingsvergunningBack = getOmgevingsvergunningRandomSampleGenerator();

        plan.addBetrekkingopOmgevingsvergunning(omgevingsvergunningBack);
        assertThat(plan.getBetrekkingopOmgevingsvergunnings()).containsOnly(omgevingsvergunningBack);
        assertThat(omgevingsvergunningBack.getBetrekkingopPlan()).isEqualTo(plan);

        plan.removeBetrekkingopOmgevingsvergunning(omgevingsvergunningBack);
        assertThat(plan.getBetrekkingopOmgevingsvergunnings()).doesNotContain(omgevingsvergunningBack);
        assertThat(omgevingsvergunningBack.getBetrekkingopPlan()).isNull();

        plan.betrekkingopOmgevingsvergunnings(new HashSet<>(Set.of(omgevingsvergunningBack)));
        assertThat(plan.getBetrekkingopOmgevingsvergunnings()).containsOnly(omgevingsvergunningBack);
        assertThat(omgevingsvergunningBack.getBetrekkingopPlan()).isEqualTo(plan);

        plan.setBetrekkingopOmgevingsvergunnings(new HashSet<>());
        assertThat(plan.getBetrekkingopOmgevingsvergunnings()).doesNotContain(omgevingsvergunningBack);
        assertThat(omgevingsvergunningBack.getBetrekkingopPlan()).isNull();
    }

    @Test
    void heeftProjectontwikkelaarTest() throws Exception {
        Plan plan = getPlanRandomSampleGenerator();
        Projectontwikkelaar projectontwikkelaarBack = getProjectontwikkelaarRandomSampleGenerator();

        plan.addHeeftProjectontwikkelaar(projectontwikkelaarBack);
        assertThat(plan.getHeeftProjectontwikkelaars()).containsOnly(projectontwikkelaarBack);
        assertThat(projectontwikkelaarBack.getHeeftPlans()).containsOnly(plan);

        plan.removeHeeftProjectontwikkelaar(projectontwikkelaarBack);
        assertThat(plan.getHeeftProjectontwikkelaars()).doesNotContain(projectontwikkelaarBack);
        assertThat(projectontwikkelaarBack.getHeeftPlans()).doesNotContain(plan);

        plan.heeftProjectontwikkelaars(new HashSet<>(Set.of(projectontwikkelaarBack)));
        assertThat(plan.getHeeftProjectontwikkelaars()).containsOnly(projectontwikkelaarBack);
        assertThat(projectontwikkelaarBack.getHeeftPlans()).containsOnly(plan);

        plan.setHeeftProjectontwikkelaars(new HashSet<>());
        assertThat(plan.getHeeftProjectontwikkelaars()).doesNotContain(projectontwikkelaarBack);
        assertThat(projectontwikkelaarBack.getHeeftPlans()).doesNotContain(plan);
    }
}
