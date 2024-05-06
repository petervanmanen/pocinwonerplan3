package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.CultuurcodebebouwdTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CultuurcodebebouwdTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cultuurcodebebouwd.class);
        Cultuurcodebebouwd cultuurcodebebouwd1 = getCultuurcodebebouwdSample1();
        Cultuurcodebebouwd cultuurcodebebouwd2 = new Cultuurcodebebouwd();
        assertThat(cultuurcodebebouwd1).isNotEqualTo(cultuurcodebebouwd2);

        cultuurcodebebouwd2.setId(cultuurcodebebouwd1.getId());
        assertThat(cultuurcodebebouwd1).isEqualTo(cultuurcodebebouwd2);

        cultuurcodebebouwd2 = getCultuurcodebebouwdSample2();
        assertThat(cultuurcodebebouwd1).isNotEqualTo(cultuurcodebebouwd2);
    }
}
