package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClassificatieTestSamples.*;
import static nl.ritense.demo.domain.GegevenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClassificatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Classificatie.class);
        Classificatie classificatie1 = getClassificatieSample1();
        Classificatie classificatie2 = new Classificatie();
        assertThat(classificatie1).isNotEqualTo(classificatie2);

        classificatie2.setId(classificatie1.getId());
        assertThat(classificatie1).isEqualTo(classificatie2);

        classificatie2 = getClassificatieSample2();
        assertThat(classificatie1).isNotEqualTo(classificatie2);
    }

    @Test
    void geclassificeerdalsGegevenTest() throws Exception {
        Classificatie classificatie = getClassificatieRandomSampleGenerator();
        Gegeven gegevenBack = getGegevenRandomSampleGenerator();

        classificatie.addGeclassificeerdalsGegeven(gegevenBack);
        assertThat(classificatie.getGeclassificeerdalsGegevens()).containsOnly(gegevenBack);
        assertThat(gegevenBack.getGeclassificeerdalsClassificaties()).containsOnly(classificatie);

        classificatie.removeGeclassificeerdalsGegeven(gegevenBack);
        assertThat(classificatie.getGeclassificeerdalsGegevens()).doesNotContain(gegevenBack);
        assertThat(gegevenBack.getGeclassificeerdalsClassificaties()).doesNotContain(classificatie);

        classificatie.geclassificeerdalsGegevens(new HashSet<>(Set.of(gegevenBack)));
        assertThat(classificatie.getGeclassificeerdalsGegevens()).containsOnly(gegevenBack);
        assertThat(gegevenBack.getGeclassificeerdalsClassificaties()).containsOnly(classificatie);

        classificatie.setGeclassificeerdalsGegevens(new HashSet<>());
        assertThat(classificatie.getGeclassificeerdalsGegevens()).doesNotContain(gegevenBack);
        assertThat(gegevenBack.getGeclassificeerdalsClassificaties()).doesNotContain(classificatie);
    }
}
