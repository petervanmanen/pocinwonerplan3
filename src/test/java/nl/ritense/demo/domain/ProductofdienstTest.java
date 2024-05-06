package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ProductofdienstTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductofdienstTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Productofdienst.class);
        Productofdienst productofdienst1 = getProductofdienstSample1();
        Productofdienst productofdienst2 = new Productofdienst();
        assertThat(productofdienst1).isNotEqualTo(productofdienst2);

        productofdienst2.setId(productofdienst1.getId());
        assertThat(productofdienst1).isEqualTo(productofdienst2);

        productofdienst2 = getProductofdienstSample2();
        assertThat(productofdienst1).isNotEqualTo(productofdienst2);
    }
}
