package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OmgevingsvergunningTestSamples.*;
import static nl.ritense.demo.domain.PlanTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OmgevingsvergunningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Omgevingsvergunning.class);
        Omgevingsvergunning omgevingsvergunning1 = getOmgevingsvergunningSample1();
        Omgevingsvergunning omgevingsvergunning2 = new Omgevingsvergunning();
        assertThat(omgevingsvergunning1).isNotEqualTo(omgevingsvergunning2);

        omgevingsvergunning2.setId(omgevingsvergunning1.getId());
        assertThat(omgevingsvergunning1).isEqualTo(omgevingsvergunning2);

        omgevingsvergunning2 = getOmgevingsvergunningSample2();
        assertThat(omgevingsvergunning1).isNotEqualTo(omgevingsvergunning2);
    }

    @Test
    void betrekkingopPlanTest() throws Exception {
        Omgevingsvergunning omgevingsvergunning = getOmgevingsvergunningRandomSampleGenerator();
        Plan planBack = getPlanRandomSampleGenerator();

        omgevingsvergunning.setBetrekkingopPlan(planBack);
        assertThat(omgevingsvergunning.getBetrekkingopPlan()).isEqualTo(planBack);

        omgevingsvergunning.betrekkingopPlan(null);
        assertThat(omgevingsvergunning.getBetrekkingopPlan()).isNull();
    }
}
