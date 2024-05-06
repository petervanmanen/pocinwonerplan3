package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SoortscheidingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SoortscheidingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Soortscheiding.class);
        Soortscheiding soortscheiding1 = getSoortscheidingSample1();
        Soortscheiding soortscheiding2 = new Soortscheiding();
        assertThat(soortscheiding1).isNotEqualTo(soortscheiding2);

        soortscheiding2.setId(soortscheiding1.getId());
        assertThat(soortscheiding1).isEqualTo(soortscheiding2);

        soortscheiding2 = getSoortscheidingSample2();
        assertThat(soortscheiding1).isNotEqualTo(soortscheiding2);
    }
}
