package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VorderingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VorderingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vordering.class);
        Vordering vordering1 = getVorderingSample1();
        Vordering vordering2 = new Vordering();
        assertThat(vordering1).isNotEqualTo(vordering2);

        vordering2.setId(vordering1.getId());
        assertThat(vordering1).isEqualTo(vordering2);

        vordering2 = getVorderingSample2();
        assertThat(vordering1).isNotEqualTo(vordering2);
    }
}
