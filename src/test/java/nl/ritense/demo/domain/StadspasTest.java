package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.StadspasTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StadspasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stadspas.class);
        Stadspas stadspas1 = getStadspasSample1();
        Stadspas stadspas2 = new Stadspas();
        assertThat(stadspas1).isNotEqualTo(stadspas2);

        stadspas2.setId(stadspas1.getId());
        assertThat(stadspas1).isEqualTo(stadspas2);

        stadspas2 = getStadspasSample2();
        assertThat(stadspas1).isNotEqualTo(stadspas2);
    }
}
