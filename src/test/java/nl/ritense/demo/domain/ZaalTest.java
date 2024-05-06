package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ReserveringTestSamples.*;
import static nl.ritense.demo.domain.ZaalTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ZaalTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zaal.class);
        Zaal zaal1 = getZaalSample1();
        Zaal zaal2 = new Zaal();
        assertThat(zaal1).isNotEqualTo(zaal2);

        zaal2.setId(zaal1.getId());
        assertThat(zaal1).isEqualTo(zaal2);

        zaal2 = getZaalSample2();
        assertThat(zaal1).isNotEqualTo(zaal2);
    }

    @Test
    void betreftReserveringTest() throws Exception {
        Zaal zaal = getZaalRandomSampleGenerator();
        Reservering reserveringBack = getReserveringRandomSampleGenerator();

        zaal.addBetreftReservering(reserveringBack);
        assertThat(zaal.getBetreftReserverings()).containsOnly(reserveringBack);
        assertThat(reserveringBack.getBetreftZaal()).isEqualTo(zaal);

        zaal.removeBetreftReservering(reserveringBack);
        assertThat(zaal.getBetreftReserverings()).doesNotContain(reserveringBack);
        assertThat(reserveringBack.getBetreftZaal()).isNull();

        zaal.betreftReserverings(new HashSet<>(Set.of(reserveringBack)));
        assertThat(zaal.getBetreftReserverings()).containsOnly(reserveringBack);
        assertThat(reserveringBack.getBetreftZaal()).isEqualTo(zaal);

        zaal.setBetreftReserverings(new HashSet<>());
        assertThat(zaal.getBetreftReserverings()).doesNotContain(reserveringBack);
        assertThat(reserveringBack.getBetreftZaal()).isNull();
    }
}
