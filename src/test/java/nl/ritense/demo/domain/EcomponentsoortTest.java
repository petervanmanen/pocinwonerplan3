package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EcomponentsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EcomponentsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ecomponentsoort.class);
        Ecomponentsoort ecomponentsoort1 = getEcomponentsoortSample1();
        Ecomponentsoort ecomponentsoort2 = new Ecomponentsoort();
        assertThat(ecomponentsoort1).isNotEqualTo(ecomponentsoort2);

        ecomponentsoort2.setId(ecomponentsoort1.getId());
        assertThat(ecomponentsoort1).isEqualTo(ecomponentsoort2);

        ecomponentsoort2 = getEcomponentsoortSample2();
        assertThat(ecomponentsoort1).isNotEqualTo(ecomponentsoort2);
    }
}
