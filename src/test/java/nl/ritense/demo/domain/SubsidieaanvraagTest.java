package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SubsidieTestSamples.*;
import static nl.ritense.demo.domain.SubsidieaanvraagTestSamples.*;
import static nl.ritense.demo.domain.SubsidiebeschikkingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubsidieaanvraagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subsidieaanvraag.class);
        Subsidieaanvraag subsidieaanvraag1 = getSubsidieaanvraagSample1();
        Subsidieaanvraag subsidieaanvraag2 = new Subsidieaanvraag();
        assertThat(subsidieaanvraag1).isNotEqualTo(subsidieaanvraag2);

        subsidieaanvraag2.setId(subsidieaanvraag1.getId());
        assertThat(subsidieaanvraag1).isEqualTo(subsidieaanvraag2);

        subsidieaanvraag2 = getSubsidieaanvraagSample2();
        assertThat(subsidieaanvraag1).isNotEqualTo(subsidieaanvraag2);
    }

    @Test
    void betreftSubsidieTest() throws Exception {
        Subsidieaanvraag subsidieaanvraag = getSubsidieaanvraagRandomSampleGenerator();
        Subsidie subsidieBack = getSubsidieRandomSampleGenerator();

        subsidieaanvraag.setBetreftSubsidie(subsidieBack);
        assertThat(subsidieaanvraag.getBetreftSubsidie()).isEqualTo(subsidieBack);

        subsidieaanvraag.betreftSubsidie(null);
        assertThat(subsidieaanvraag.getBetreftSubsidie()).isNull();
    }

    @Test
    void mondtuitSubsidiebeschikkingTest() throws Exception {
        Subsidieaanvraag subsidieaanvraag = getSubsidieaanvraagRandomSampleGenerator();
        Subsidiebeschikking subsidiebeschikkingBack = getSubsidiebeschikkingRandomSampleGenerator();

        subsidieaanvraag.setMondtuitSubsidiebeschikking(subsidiebeschikkingBack);
        assertThat(subsidieaanvraag.getMondtuitSubsidiebeschikking()).isEqualTo(subsidiebeschikkingBack);

        subsidieaanvraag.mondtuitSubsidiebeschikking(null);
        assertThat(subsidieaanvraag.getMondtuitSubsidiebeschikking()).isNull();
    }
}
