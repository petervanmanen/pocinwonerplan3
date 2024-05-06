package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ExamenTestSamples.*;
import static nl.ritense.demo.domain.InburgeringstrajectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InburgeringstrajectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inburgeringstraject.class);
        Inburgeringstraject inburgeringstraject1 = getInburgeringstrajectSample1();
        Inburgeringstraject inburgeringstraject2 = new Inburgeringstraject();
        assertThat(inburgeringstraject1).isNotEqualTo(inburgeringstraject2);

        inburgeringstraject2.setId(inburgeringstraject1.getId());
        assertThat(inburgeringstraject1).isEqualTo(inburgeringstraject2);

        inburgeringstraject2 = getInburgeringstrajectSample2();
        assertThat(inburgeringstraject1).isNotEqualTo(inburgeringstraject2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Inburgeringstraject inburgeringstraject = new Inburgeringstraject();
        assertThat(inburgeringstraject.hashCode()).isZero();

        Inburgeringstraject inburgeringstraject1 = getInburgeringstrajectSample1();
        inburgeringstraject.setId(inburgeringstraject1.getId());
        assertThat(inburgeringstraject).hasSameHashCodeAs(inburgeringstraject1);
    }

    @Test
    void afgerondmetExamenTest() throws Exception {
        Inburgeringstraject inburgeringstraject = getInburgeringstrajectRandomSampleGenerator();
        Examen examenBack = getExamenRandomSampleGenerator();

        inburgeringstraject.setAfgerondmetExamen(examenBack);
        assertThat(inburgeringstraject.getAfgerondmetExamen()).isEqualTo(examenBack);
        assertThat(examenBack.getAfgerondmetInburgeringstraject()).isEqualTo(inburgeringstraject);

        inburgeringstraject.afgerondmetExamen(null);
        assertThat(inburgeringstraject.getAfgerondmetExamen()).isNull();
        assertThat(examenBack.getAfgerondmetInburgeringstraject()).isNull();
    }
}
