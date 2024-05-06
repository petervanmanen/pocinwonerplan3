package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GezinsmigrantenoverigemigrantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GezinsmigrantenoverigemigrantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gezinsmigrantenoverigemigrant.class);
        Gezinsmigrantenoverigemigrant gezinsmigrantenoverigemigrant1 = getGezinsmigrantenoverigemigrantSample1();
        Gezinsmigrantenoverigemigrant gezinsmigrantenoverigemigrant2 = new Gezinsmigrantenoverigemigrant();
        assertThat(gezinsmigrantenoverigemigrant1).isNotEqualTo(gezinsmigrantenoverigemigrant2);

        gezinsmigrantenoverigemigrant2.setId(gezinsmigrantenoverigemigrant1.getId());
        assertThat(gezinsmigrantenoverigemigrant1).isEqualTo(gezinsmigrantenoverigemigrant2);

        gezinsmigrantenoverigemigrant2 = getGezinsmigrantenoverigemigrantSample2();
        assertThat(gezinsmigrantenoverigemigrant1).isNotEqualTo(gezinsmigrantenoverigemigrant2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Gezinsmigrantenoverigemigrant gezinsmigrantenoverigemigrant = new Gezinsmigrantenoverigemigrant();
        assertThat(gezinsmigrantenoverigemigrant.hashCode()).isZero();

        Gezinsmigrantenoverigemigrant gezinsmigrantenoverigemigrant1 = getGezinsmigrantenoverigemigrantSample1();
        gezinsmigrantenoverigemigrant.setId(gezinsmigrantenoverigemigrant1.getId());
        assertThat(gezinsmigrantenoverigemigrant).hasSameHashCodeAs(gezinsmigrantenoverigemigrant1);
    }
}
