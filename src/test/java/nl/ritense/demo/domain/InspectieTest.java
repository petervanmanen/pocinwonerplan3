package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.InspectieTestSamples.*;
import static nl.ritense.demo.domain.VastgoedobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InspectieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inspectie.class);
        Inspectie inspectie1 = getInspectieSample1();
        Inspectie inspectie2 = new Inspectie();
        assertThat(inspectie1).isNotEqualTo(inspectie2);

        inspectie2.setId(inspectie1.getId());
        assertThat(inspectie1).isEqualTo(inspectie2);

        inspectie2 = getInspectieSample2();
        assertThat(inspectie1).isNotEqualTo(inspectie2);
    }

    @Test
    void betreftVastgoedobjectTest() throws Exception {
        Inspectie inspectie = getInspectieRandomSampleGenerator();
        Vastgoedobject vastgoedobjectBack = getVastgoedobjectRandomSampleGenerator();

        inspectie.setBetreftVastgoedobject(vastgoedobjectBack);
        assertThat(inspectie.getBetreftVastgoedobject()).isEqualTo(vastgoedobjectBack);

        inspectie.betreftVastgoedobject(null);
        assertThat(inspectie.getBetreftVastgoedobject()).isNull();
    }
}
