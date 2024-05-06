package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.StartformulieraanbestedenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StartformulieraanbestedenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Startformulieraanbesteden.class);
        Startformulieraanbesteden startformulieraanbesteden1 = getStartformulieraanbestedenSample1();
        Startformulieraanbesteden startformulieraanbesteden2 = new Startformulieraanbesteden();
        assertThat(startformulieraanbesteden1).isNotEqualTo(startformulieraanbesteden2);

        startformulieraanbesteden2.setId(startformulieraanbesteden1.getId());
        assertThat(startformulieraanbesteden1).isEqualTo(startformulieraanbesteden2);

        startformulieraanbesteden2 = getStartformulieraanbestedenSample2();
        assertThat(startformulieraanbesteden1).isNotEqualTo(startformulieraanbesteden2);
    }
}
