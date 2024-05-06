package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ParkeerzoneTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ParkeerzoneTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Parkeerzone.class);
        Parkeerzone parkeerzone1 = getParkeerzoneSample1();
        Parkeerzone parkeerzone2 = new Parkeerzone();
        assertThat(parkeerzone1).isNotEqualTo(parkeerzone2);

        parkeerzone2.setId(parkeerzone1.getId());
        assertThat(parkeerzone1).isEqualTo(parkeerzone2);

        parkeerzone2 = getParkeerzoneSample2();
        assertThat(parkeerzone1).isNotEqualTo(parkeerzone2);
    }
}
