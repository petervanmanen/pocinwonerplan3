package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EigenaarTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EigenaarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eigenaar.class);
        Eigenaar eigenaar1 = getEigenaarSample1();
        Eigenaar eigenaar2 = new Eigenaar();
        assertThat(eigenaar1).isNotEqualTo(eigenaar2);

        eigenaar2.setId(eigenaar1.getId());
        assertThat(eigenaar1).isEqualTo(eigenaar2);

        eigenaar2 = getEigenaarSample2();
        assertThat(eigenaar1).isNotEqualTo(eigenaar2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Eigenaar eigenaar = new Eigenaar();
        assertThat(eigenaar.hashCode()).isZero();

        Eigenaar eigenaar1 = getEigenaarSample1();
        eigenaar.setId(eigenaar1.getId());
        assertThat(eigenaar).hasSameHashCodeAs(eigenaar1);
    }
}
