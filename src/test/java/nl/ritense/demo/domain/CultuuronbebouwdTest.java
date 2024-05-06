package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.CultuuronbebouwdTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CultuuronbebouwdTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cultuuronbebouwd.class);
        Cultuuronbebouwd cultuuronbebouwd1 = getCultuuronbebouwdSample1();
        Cultuuronbebouwd cultuuronbebouwd2 = new Cultuuronbebouwd();
        assertThat(cultuuronbebouwd1).isNotEqualTo(cultuuronbebouwd2);

        cultuuronbebouwd2.setId(cultuuronbebouwd1.getId());
        assertThat(cultuuronbebouwd1).isEqualTo(cultuuronbebouwd2);

        cultuuronbebouwd2 = getCultuuronbebouwdSample2();
        assertThat(cultuuronbebouwd1).isNotEqualTo(cultuuronbebouwd2);
    }
}
