package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SubsidieprogrammaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubsidieprogrammaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subsidieprogramma.class);
        Subsidieprogramma subsidieprogramma1 = getSubsidieprogrammaSample1();
        Subsidieprogramma subsidieprogramma2 = new Subsidieprogramma();
        assertThat(subsidieprogramma1).isNotEqualTo(subsidieprogramma2);

        subsidieprogramma2.setId(subsidieprogramma1.getId());
        assertThat(subsidieprogramma1).isEqualTo(subsidieprogramma2);

        subsidieprogramma2 = getSubsidieprogrammaSample2();
        assertThat(subsidieprogramma1).isNotEqualTo(subsidieprogramma2);
    }
}
