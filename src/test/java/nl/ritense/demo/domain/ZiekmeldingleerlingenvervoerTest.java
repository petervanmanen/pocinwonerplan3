package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ZiekmeldingleerlingenvervoerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ZiekmeldingleerlingenvervoerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ziekmeldingleerlingenvervoer.class);
        Ziekmeldingleerlingenvervoer ziekmeldingleerlingenvervoer1 = getZiekmeldingleerlingenvervoerSample1();
        Ziekmeldingleerlingenvervoer ziekmeldingleerlingenvervoer2 = new Ziekmeldingleerlingenvervoer();
        assertThat(ziekmeldingleerlingenvervoer1).isNotEqualTo(ziekmeldingleerlingenvervoer2);

        ziekmeldingleerlingenvervoer2.setId(ziekmeldingleerlingenvervoer1.getId());
        assertThat(ziekmeldingleerlingenvervoer1).isEqualTo(ziekmeldingleerlingenvervoer2);

        ziekmeldingleerlingenvervoer2 = getZiekmeldingleerlingenvervoerSample2();
        assertThat(ziekmeldingleerlingenvervoer1).isNotEqualTo(ziekmeldingleerlingenvervoer2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Ziekmeldingleerlingenvervoer ziekmeldingleerlingenvervoer = new Ziekmeldingleerlingenvervoer();
        assertThat(ziekmeldingleerlingenvervoer.hashCode()).isZero();

        Ziekmeldingleerlingenvervoer ziekmeldingleerlingenvervoer1 = getZiekmeldingleerlingenvervoerSample1();
        ziekmeldingleerlingenvervoer.setId(ziekmeldingleerlingenvervoer1.getId());
        assertThat(ziekmeldingleerlingenvervoer).hasSameHashCodeAs(ziekmeldingleerlingenvervoer1);
    }
}
