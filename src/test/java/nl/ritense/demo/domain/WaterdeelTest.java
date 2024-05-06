package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.WaterdeelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WaterdeelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Waterdeel.class);
        Waterdeel waterdeel1 = getWaterdeelSample1();
        Waterdeel waterdeel2 = new Waterdeel();
        assertThat(waterdeel1).isNotEqualTo(waterdeel2);

        waterdeel2.setId(waterdeel1.getId());
        assertThat(waterdeel1).isEqualTo(waterdeel2);

        waterdeel2 = getWaterdeelSample2();
        assertThat(waterdeel1).isNotEqualTo(waterdeel2);
    }
}
