package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ParkeergarageTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParkeergarageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parkeergarage.class);
        Parkeergarage parkeergarage1 = getParkeergarageSample1();
        Parkeergarage parkeergarage2 = new Parkeergarage();
        assertThat(parkeergarage1).isNotEqualTo(parkeergarage2);

        parkeergarage2.setId(parkeergarage1.getId());
        assertThat(parkeergarage1).isEqualTo(parkeergarage2);

        parkeergarage2 = getParkeergarageSample2();
        assertThat(parkeergarage1).isNotEqualTo(parkeergarage2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Parkeergarage parkeergarage = new Parkeergarage();
        assertThat(parkeergarage.hashCode()).isZero();

        Parkeergarage parkeergarage1 = getParkeergarageSample1();
        parkeergarage.setId(parkeergarage1.getId());
        assertThat(parkeergarage).hasSameHashCodeAs(parkeergarage1);
    }
}
