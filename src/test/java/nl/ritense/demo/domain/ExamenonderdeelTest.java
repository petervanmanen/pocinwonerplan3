package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ExamenTestSamples.*;
import static nl.ritense.demo.domain.ExamenonderdeelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExamenonderdeelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Examenonderdeel.class);
        Examenonderdeel examenonderdeel1 = getExamenonderdeelSample1();
        Examenonderdeel examenonderdeel2 = new Examenonderdeel();
        assertThat(examenonderdeel1).isNotEqualTo(examenonderdeel2);

        examenonderdeel2.setId(examenonderdeel1.getId());
        assertThat(examenonderdeel1).isEqualTo(examenonderdeel2);

        examenonderdeel2 = getExamenonderdeelSample2();
        assertThat(examenonderdeel1).isNotEqualTo(examenonderdeel2);
    }

    @Test
    void emptyExamenTest() throws Exception {
        Examenonderdeel examenonderdeel = getExamenonderdeelRandomSampleGenerator();
        Examen examenBack = getExamenRandomSampleGenerator();

        examenonderdeel.setEmptyExamen(examenBack);
        assertThat(examenonderdeel.getEmptyExamen()).isEqualTo(examenBack);

        examenonderdeel.emptyExamen(null);
        assertThat(examenonderdeel.getEmptyExamen()).isNull();
    }
}
