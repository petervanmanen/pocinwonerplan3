package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ScheidingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ScheidingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Scheiding.class);
        Scheiding scheiding1 = getScheidingSample1();
        Scheiding scheiding2 = new Scheiding();
        assertThat(scheiding1).isNotEqualTo(scheiding2);

        scheiding2.setId(scheiding1.getId());
        assertThat(scheiding1).isEqualTo(scheiding2);

        scheiding2 = getScheidingSample2();
        assertThat(scheiding1).isNotEqualTo(scheiding2);
    }
}
