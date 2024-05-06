package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OverstortconstructieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OverstortconstructieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Overstortconstructie.class);
        Overstortconstructie overstortconstructie1 = getOverstortconstructieSample1();
        Overstortconstructie overstortconstructie2 = new Overstortconstructie();
        assertThat(overstortconstructie1).isNotEqualTo(overstortconstructie2);

        overstortconstructie2.setId(overstortconstructie1.getId());
        assertThat(overstortconstructie1).isEqualTo(overstortconstructie2);

        overstortconstructie2 = getOverstortconstructieSample2();
        assertThat(overstortconstructie1).isNotEqualTo(overstortconstructie2);
    }
}
