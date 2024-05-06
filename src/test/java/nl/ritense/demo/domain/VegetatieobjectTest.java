package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VegetatieobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VegetatieobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vegetatieobject.class);
        Vegetatieobject vegetatieobject1 = getVegetatieobjectSample1();
        Vegetatieobject vegetatieobject2 = new Vegetatieobject();
        assertThat(vegetatieobject1).isNotEqualTo(vegetatieobject2);

        vegetatieobject2.setId(vegetatieobject1.getId());
        assertThat(vegetatieobject1).isEqualTo(vegetatieobject2);

        vegetatieobject2 = getVegetatieobjectSample2();
        assertThat(vegetatieobject1).isNotEqualTo(vegetatieobject2);
    }
}
