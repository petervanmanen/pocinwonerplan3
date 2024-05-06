package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BevindingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BevindingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bevinding.class);
        Bevinding bevinding1 = getBevindingSample1();
        Bevinding bevinding2 = new Bevinding();
        assertThat(bevinding1).isNotEqualTo(bevinding2);

        bevinding2.setId(bevinding1.getId());
        assertThat(bevinding1).isEqualTo(bevinding2);

        bevinding2 = getBevindingSample2();
        assertThat(bevinding1).isNotEqualTo(bevinding2);
    }
}
