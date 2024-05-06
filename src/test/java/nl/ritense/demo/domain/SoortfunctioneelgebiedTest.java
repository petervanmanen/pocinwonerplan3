package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SoortfunctioneelgebiedTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SoortfunctioneelgebiedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Soortfunctioneelgebied.class);
        Soortfunctioneelgebied soortfunctioneelgebied1 = getSoortfunctioneelgebiedSample1();
        Soortfunctioneelgebied soortfunctioneelgebied2 = new Soortfunctioneelgebied();
        assertThat(soortfunctioneelgebied1).isNotEqualTo(soortfunctioneelgebied2);

        soortfunctioneelgebied2.setId(soortfunctioneelgebied1.getId());
        assertThat(soortfunctioneelgebied1).isEqualTo(soortfunctioneelgebied2);

        soortfunctioneelgebied2 = getSoortfunctioneelgebiedSample2();
        assertThat(soortfunctioneelgebied1).isNotEqualTo(soortfunctioneelgebied2);
    }
}
