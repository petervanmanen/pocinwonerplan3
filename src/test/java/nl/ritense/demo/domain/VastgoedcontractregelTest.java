package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VastgoedcontractregelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VastgoedcontractregelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vastgoedcontractregel.class);
        Vastgoedcontractregel vastgoedcontractregel1 = getVastgoedcontractregelSample1();
        Vastgoedcontractregel vastgoedcontractregel2 = new Vastgoedcontractregel();
        assertThat(vastgoedcontractregel1).isNotEqualTo(vastgoedcontractregel2);

        vastgoedcontractregel2.setId(vastgoedcontractregel1.getId());
        assertThat(vastgoedcontractregel1).isEqualTo(vastgoedcontractregel2);

        vastgoedcontractregel2 = getVastgoedcontractregelSample2();
        assertThat(vastgoedcontractregel1).isNotEqualTo(vastgoedcontractregel2);
    }
}
