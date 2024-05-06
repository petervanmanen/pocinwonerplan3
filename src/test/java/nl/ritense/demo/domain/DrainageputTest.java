package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DrainageputTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DrainageputTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Drainageput.class);
        Drainageput drainageput1 = getDrainageputSample1();
        Drainageput drainageput2 = new Drainageput();
        assertThat(drainageput1).isNotEqualTo(drainageput2);

        drainageput2.setId(drainageput1.getId());
        assertThat(drainageput1).isEqualTo(drainageput2);

        drainageput2 = getDrainageputSample2();
        assertThat(drainageput1).isNotEqualTo(drainageput2);
    }
}
