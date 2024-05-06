package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MjopTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MjopTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mjop.class);
        Mjop mjop1 = getMjopSample1();
        Mjop mjop2 = new Mjop();
        assertThat(mjop1).isNotEqualTo(mjop2);

        mjop2.setId(mjop1.getId());
        assertThat(mjop1).isEqualTo(mjop2);

        mjop2 = getMjopSample2();
        assertThat(mjop1).isNotEqualTo(mjop2);
    }
}
