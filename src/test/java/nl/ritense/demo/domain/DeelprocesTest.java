package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DeelprocesTestSamples.*;
import static nl.ritense.demo.domain.DeelprocestypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeelprocesTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deelproces.class);
        Deelproces deelproces1 = getDeelprocesSample1();
        Deelproces deelproces2 = new Deelproces();
        assertThat(deelproces1).isNotEqualTo(deelproces2);

        deelproces2.setId(deelproces1.getId());
        assertThat(deelproces1).isEqualTo(deelproces2);

        deelproces2 = getDeelprocesSample2();
        assertThat(deelproces1).isNotEqualTo(deelproces2);
    }

    @Test
    void isvanDeelprocestypeTest() throws Exception {
        Deelproces deelproces = getDeelprocesRandomSampleGenerator();
        Deelprocestype deelprocestypeBack = getDeelprocestypeRandomSampleGenerator();

        deelproces.setIsvanDeelprocestype(deelprocestypeBack);
        assertThat(deelproces.getIsvanDeelprocestype()).isEqualTo(deelprocestypeBack);

        deelproces.isvanDeelprocestype(null);
        assertThat(deelproces.getIsvanDeelprocestype()).isNull();
    }
}
