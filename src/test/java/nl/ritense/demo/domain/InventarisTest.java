package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.InventarisTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InventarisTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inventaris.class);
        Inventaris inventaris1 = getInventarisSample1();
        Inventaris inventaris2 = new Inventaris();
        assertThat(inventaris1).isNotEqualTo(inventaris2);

        inventaris2.setId(inventaris1.getId());
        assertThat(inventaris1).isEqualTo(inventaris2);

        inventaris2 = getInventarisSample2();
        assertThat(inventaris1).isNotEqualTo(inventaris2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Inventaris inventaris = new Inventaris();
        assertThat(inventaris.hashCode()).isZero();

        Inventaris inventaris1 = getInventarisSample1();
        inventaris.setId(inventaris1.getId());
        assertThat(inventaris).hasSameHashCodeAs(inventaris1);
    }
}
