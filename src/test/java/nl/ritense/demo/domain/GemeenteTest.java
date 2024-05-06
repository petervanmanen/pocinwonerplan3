package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AsielstatushouderTestSamples.*;
import static nl.ritense.demo.domain.GemeenteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GemeenteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gemeente.class);
        Gemeente gemeente1 = getGemeenteSample1();
        Gemeente gemeente2 = new Gemeente();
        assertThat(gemeente1).isNotEqualTo(gemeente2);

        gemeente2.setId(gemeente1.getId());
        assertThat(gemeente1).isEqualTo(gemeente2);

        gemeente2 = getGemeenteSample2();
        assertThat(gemeente1).isNotEqualTo(gemeente2);
    }

    @Test
    void isgekoppeldaanAsielstatushouderTest() throws Exception {
        Gemeente gemeente = getGemeenteRandomSampleGenerator();
        Asielstatushouder asielstatushouderBack = getAsielstatushouderRandomSampleGenerator();

        gemeente.addIsgekoppeldaanAsielstatushouder(asielstatushouderBack);
        assertThat(gemeente.getIsgekoppeldaanAsielstatushouders()).containsOnly(asielstatushouderBack);
        assertThat(asielstatushouderBack.getIsgekoppeldaanGemeente()).isEqualTo(gemeente);

        gemeente.removeIsgekoppeldaanAsielstatushouder(asielstatushouderBack);
        assertThat(gemeente.getIsgekoppeldaanAsielstatushouders()).doesNotContain(asielstatushouderBack);
        assertThat(asielstatushouderBack.getIsgekoppeldaanGemeente()).isNull();

        gemeente.isgekoppeldaanAsielstatushouders(new HashSet<>(Set.of(asielstatushouderBack)));
        assertThat(gemeente.getIsgekoppeldaanAsielstatushouders()).containsOnly(asielstatushouderBack);
        assertThat(asielstatushouderBack.getIsgekoppeldaanGemeente()).isEqualTo(gemeente);

        gemeente.setIsgekoppeldaanAsielstatushouders(new HashSet<>());
        assertThat(gemeente.getIsgekoppeldaanAsielstatushouders()).doesNotContain(asielstatushouderBack);
        assertThat(asielstatushouderBack.getIsgekoppeldaanGemeente()).isNull();
    }
}
