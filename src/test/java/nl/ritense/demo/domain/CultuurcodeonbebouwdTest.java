package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.CultuurcodeonbebouwdTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CultuurcodeonbebouwdTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cultuurcodeonbebouwd.class);
        Cultuurcodeonbebouwd cultuurcodeonbebouwd1 = getCultuurcodeonbebouwdSample1();
        Cultuurcodeonbebouwd cultuurcodeonbebouwd2 = new Cultuurcodeonbebouwd();
        assertThat(cultuurcodeonbebouwd1).isNotEqualTo(cultuurcodeonbebouwd2);

        cultuurcodeonbebouwd2.setId(cultuurcodeonbebouwd1.getId());
        assertThat(cultuurcodeonbebouwd1).isEqualTo(cultuurcodeonbebouwd2);

        cultuurcodeonbebouwd2 = getCultuurcodeonbebouwdSample2();
        assertThat(cultuurcodeonbebouwd1).isNotEqualTo(cultuurcodeonbebouwd2);
    }
}
