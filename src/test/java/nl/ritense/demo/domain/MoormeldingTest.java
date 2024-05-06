package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MoormeldingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MoormeldingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Moormelding.class);
        Moormelding moormelding1 = getMoormeldingSample1();
        Moormelding moormelding2 = new Moormelding();
        assertThat(moormelding1).isNotEqualTo(moormelding2);

        moormelding2.setId(moormelding1.getId());
        assertThat(moormelding1).isEqualTo(moormelding2);

        moormelding2 = getMoormeldingSample2();
        assertThat(moormelding1).isNotEqualTo(moormelding2);
    }
}
