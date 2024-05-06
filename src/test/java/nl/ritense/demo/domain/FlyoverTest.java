package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FlyoverTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FlyoverTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Flyover.class);
        Flyover flyover1 = getFlyoverSample1();
        Flyover flyover2 = new Flyover();
        assertThat(flyover1).isNotEqualTo(flyover2);

        flyover2.setId(flyover1.getId());
        assertThat(flyover1).isEqualTo(flyover2);

        flyover2 = getFlyoverSample2();
        assertThat(flyover1).isNotEqualTo(flyover2);
    }
}
