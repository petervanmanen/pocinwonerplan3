package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ProductieeenheidTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductieeenheidTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Productieeenheid.class);
        Productieeenheid productieeenheid1 = getProductieeenheidSample1();
        Productieeenheid productieeenheid2 = new Productieeenheid();
        assertThat(productieeenheid1).isNotEqualTo(productieeenheid2);

        productieeenheid2.setId(productieeenheid1.getId());
        assertThat(productieeenheid1).isEqualTo(productieeenheid2);

        productieeenheid2 = getProductieeenheidSample2();
        assertThat(productieeenheid1).isNotEqualTo(productieeenheid2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Productieeenheid productieeenheid = new Productieeenheid();
        assertThat(productieeenheid.hashCode()).isZero();

        Productieeenheid productieeenheid1 = getProductieeenheidSample1();
        productieeenheid.setId(productieeenheid1.getId());
        assertThat(productieeenheid).hasSameHashCodeAs(productieeenheid1);
    }
}
