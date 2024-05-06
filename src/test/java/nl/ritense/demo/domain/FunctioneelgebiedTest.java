package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FunctioneelgebiedTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FunctioneelgebiedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Functioneelgebied.class);
        Functioneelgebied functioneelgebied1 = getFunctioneelgebiedSample1();
        Functioneelgebied functioneelgebied2 = new Functioneelgebied();
        assertThat(functioneelgebied1).isNotEqualTo(functioneelgebied2);

        functioneelgebied2.setId(functioneelgebied1.getId());
        assertThat(functioneelgebied1).isEqualTo(functioneelgebied2);

        functioneelgebied2 = getFunctioneelgebiedSample2();
        assertThat(functioneelgebied1).isNotEqualTo(functioneelgebied2);
    }
}
