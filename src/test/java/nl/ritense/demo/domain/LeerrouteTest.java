package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LeerrouteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeerrouteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leerroute.class);
        Leerroute leerroute1 = getLeerrouteSample1();
        Leerroute leerroute2 = new Leerroute();
        assertThat(leerroute1).isNotEqualTo(leerroute2);

        leerroute2.setId(leerroute1.getId());
        assertThat(leerroute1).isEqualTo(leerroute2);

        leerroute2 = getLeerrouteSample2();
        assertThat(leerroute1).isNotEqualTo(leerroute2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Leerroute leerroute = new Leerroute();
        assertThat(leerroute.hashCode()).isZero();

        Leerroute leerroute1 = getLeerrouteSample1();
        leerroute.setId(leerroute1.getId());
        assertThat(leerroute).hasSameHashCodeAs(leerroute1);
    }
}
