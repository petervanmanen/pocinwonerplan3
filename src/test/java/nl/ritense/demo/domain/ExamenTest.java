package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ExamenTestSamples.*;
import static nl.ritense.demo.domain.ExamenonderdeelTestSamples.*;
import static nl.ritense.demo.domain.InburgeringstrajectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ExamenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Examen.class);
        Examen examen1 = getExamenSample1();
        Examen examen2 = new Examen();
        assertThat(examen1).isNotEqualTo(examen2);

        examen2.setId(examen1.getId());
        assertThat(examen1).isEqualTo(examen2);

        examen2 = getExamenSample2();
        assertThat(examen1).isNotEqualTo(examen2);
    }

    @Test
    void afgerondmetInburgeringstrajectTest() throws Exception {
        Examen examen = getExamenRandomSampleGenerator();
        Inburgeringstraject inburgeringstrajectBack = getInburgeringstrajectRandomSampleGenerator();

        examen.setAfgerondmetInburgeringstraject(inburgeringstrajectBack);
        assertThat(examen.getAfgerondmetInburgeringstraject()).isEqualTo(inburgeringstrajectBack);

        examen.afgerondmetInburgeringstraject(null);
        assertThat(examen.getAfgerondmetInburgeringstraject()).isNull();
    }

    @Test
    void emptyExamenonderdeelTest() throws Exception {
        Examen examen = getExamenRandomSampleGenerator();
        Examenonderdeel examenonderdeelBack = getExamenonderdeelRandomSampleGenerator();

        examen.addEmptyExamenonderdeel(examenonderdeelBack);
        assertThat(examen.getEmptyExamenonderdeels()).containsOnly(examenonderdeelBack);
        assertThat(examenonderdeelBack.getEmptyExamen()).isEqualTo(examen);

        examen.removeEmptyExamenonderdeel(examenonderdeelBack);
        assertThat(examen.getEmptyExamenonderdeels()).doesNotContain(examenonderdeelBack);
        assertThat(examenonderdeelBack.getEmptyExamen()).isNull();

        examen.emptyExamenonderdeels(new HashSet<>(Set.of(examenonderdeelBack)));
        assertThat(examen.getEmptyExamenonderdeels()).containsOnly(examenonderdeelBack);
        assertThat(examenonderdeelBack.getEmptyExamen()).isEqualTo(examen);

        examen.setEmptyExamenonderdeels(new HashSet<>());
        assertThat(examen.getEmptyExamenonderdeels()).doesNotContain(examenonderdeelBack);
        assertThat(examenonderdeelBack.getEmptyExamen()).isNull();
    }
}
