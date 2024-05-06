package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.RedenbeeindigingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RedenbeeindigingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Redenbeeindiging.class);
        Redenbeeindiging redenbeeindiging1 = getRedenbeeindigingSample1();
        Redenbeeindiging redenbeeindiging2 = new Redenbeeindiging();
        assertThat(redenbeeindiging1).isNotEqualTo(redenbeeindiging2);

        redenbeeindiging2.setId(redenbeeindiging1.getId());
        assertThat(redenbeeindiging1).isEqualTo(redenbeeindiging2);

        redenbeeindiging2 = getRedenbeeindigingSample2();
        assertThat(redenbeeindiging1).isNotEqualTo(redenbeeindiging2);
    }
}
