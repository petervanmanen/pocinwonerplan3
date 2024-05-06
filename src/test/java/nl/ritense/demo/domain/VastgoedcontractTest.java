package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.RechtspersoonTestSamples.*;
import static nl.ritense.demo.domain.VastgoedcontractTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VastgoedcontractTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vastgoedcontract.class);
        Vastgoedcontract vastgoedcontract1 = getVastgoedcontractSample1();
        Vastgoedcontract vastgoedcontract2 = new Vastgoedcontract();
        assertThat(vastgoedcontract1).isNotEqualTo(vastgoedcontract2);

        vastgoedcontract2.setId(vastgoedcontract1.getId());
        assertThat(vastgoedcontract1).isEqualTo(vastgoedcontract2);

        vastgoedcontract2 = getVastgoedcontractSample2();
        assertThat(vastgoedcontract1).isNotEqualTo(vastgoedcontract2);
    }

    @Test
    void heeftRechtspersoonTest() throws Exception {
        Vastgoedcontract vastgoedcontract = getVastgoedcontractRandomSampleGenerator();
        Rechtspersoon rechtspersoonBack = getRechtspersoonRandomSampleGenerator();

        vastgoedcontract.setHeeftRechtspersoon(rechtspersoonBack);
        assertThat(vastgoedcontract.getHeeftRechtspersoon()).isEqualTo(rechtspersoonBack);

        vastgoedcontract.heeftRechtspersoon(null);
        assertThat(vastgoedcontract.getHeeftRechtspersoon()).isNull();
    }
}
