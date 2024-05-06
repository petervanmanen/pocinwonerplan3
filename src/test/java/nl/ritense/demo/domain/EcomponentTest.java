package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EcomponentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EcomponentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ecomponent.class);
        Ecomponent ecomponent1 = getEcomponentSample1();
        Ecomponent ecomponent2 = new Ecomponent();
        assertThat(ecomponent1).isNotEqualTo(ecomponent2);

        ecomponent2.setId(ecomponent1.getId());
        assertThat(ecomponent1).isEqualTo(ecomponent2);

        ecomponent2 = getEcomponentSample2();
        assertThat(ecomponent1).isNotEqualTo(ecomponent2);
    }
}
