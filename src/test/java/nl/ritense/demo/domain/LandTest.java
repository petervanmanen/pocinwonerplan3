package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LandTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Land.class);
        Land land1 = getLandSample1();
        Land land2 = new Land();
        assertThat(land1).isNotEqualTo(land2);

        land2.setId(land1.getId());
        assertThat(land1).isEqualTo(land2);

        land2 = getLandSample2();
        assertThat(land1).isNotEqualTo(land2);
    }
}
