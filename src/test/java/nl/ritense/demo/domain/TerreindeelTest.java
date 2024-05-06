package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.TerreindeelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TerreindeelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Terreindeel.class);
        Terreindeel terreindeel1 = getTerreindeelSample1();
        Terreindeel terreindeel2 = new Terreindeel();
        assertThat(terreindeel1).isNotEqualTo(terreindeel2);

        terreindeel2.setId(terreindeel1.getId());
        assertThat(terreindeel1).isEqualTo(terreindeel2);

        terreindeel2 = getTerreindeelSample2();
        assertThat(terreindeel1).isNotEqualTo(terreindeel2);
    }
}
