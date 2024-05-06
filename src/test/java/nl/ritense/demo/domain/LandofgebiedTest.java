package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LandofgebiedTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LandofgebiedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Landofgebied.class);
        Landofgebied landofgebied1 = getLandofgebiedSample1();
        Landofgebied landofgebied2 = new Landofgebied();
        assertThat(landofgebied1).isNotEqualTo(landofgebied2);

        landofgebied2.setId(landofgebied1.getId());
        assertThat(landofgebied1).isEqualTo(landofgebied2);

        landofgebied2 = getLandofgebiedSample2();
        assertThat(landofgebied1).isNotEqualTo(landofgebied2);
    }
}
