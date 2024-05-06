package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LeidingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeidingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leiding.class);
        Leiding leiding1 = getLeidingSample1();
        Leiding leiding2 = new Leiding();
        assertThat(leiding1).isNotEqualTo(leiding2);

        leiding2.setId(leiding1.getId());
        assertThat(leiding1).isEqualTo(leiding2);

        leiding2 = getLeidingSample2();
        assertThat(leiding1).isNotEqualTo(leiding2);
    }
}
