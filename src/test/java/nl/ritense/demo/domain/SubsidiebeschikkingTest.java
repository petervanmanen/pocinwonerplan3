package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SubsidieTestSamples.*;
import static nl.ritense.demo.domain.SubsidieaanvraagTestSamples.*;
import static nl.ritense.demo.domain.SubsidiebeschikkingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubsidiebeschikkingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subsidiebeschikking.class);
        Subsidiebeschikking subsidiebeschikking1 = getSubsidiebeschikkingSample1();
        Subsidiebeschikking subsidiebeschikking2 = new Subsidiebeschikking();
        assertThat(subsidiebeschikking1).isNotEqualTo(subsidiebeschikking2);

        subsidiebeschikking2.setId(subsidiebeschikking1.getId());
        assertThat(subsidiebeschikking1).isEqualTo(subsidiebeschikking2);

        subsidiebeschikking2 = getSubsidiebeschikkingSample2();
        assertThat(subsidiebeschikking1).isNotEqualTo(subsidiebeschikking2);
    }

    @Test
    void betreftSubsidieTest() throws Exception {
        Subsidiebeschikking subsidiebeschikking = getSubsidiebeschikkingRandomSampleGenerator();
        Subsidie subsidieBack = getSubsidieRandomSampleGenerator();

        subsidiebeschikking.setBetreftSubsidie(subsidieBack);
        assertThat(subsidiebeschikking.getBetreftSubsidie()).isEqualTo(subsidieBack);

        subsidiebeschikking.betreftSubsidie(null);
        assertThat(subsidiebeschikking.getBetreftSubsidie()).isNull();
    }

    @Test
    void mondtuitSubsidieaanvraagTest() throws Exception {
        Subsidiebeschikking subsidiebeschikking = getSubsidiebeschikkingRandomSampleGenerator();
        Subsidieaanvraag subsidieaanvraagBack = getSubsidieaanvraagRandomSampleGenerator();

        subsidiebeschikking.setMondtuitSubsidieaanvraag(subsidieaanvraagBack);
        assertThat(subsidiebeschikking.getMondtuitSubsidieaanvraag()).isEqualTo(subsidieaanvraagBack);
        assertThat(subsidieaanvraagBack.getMondtuitSubsidiebeschikking()).isEqualTo(subsidiebeschikking);

        subsidiebeschikking.mondtuitSubsidieaanvraag(null);
        assertThat(subsidiebeschikking.getMondtuitSubsidieaanvraag()).isNull();
        assertThat(subsidieaanvraagBack.getMondtuitSubsidiebeschikking()).isNull();
    }
}
