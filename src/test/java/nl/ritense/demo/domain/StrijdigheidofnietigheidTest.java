package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.StrijdigheidofnietigheidTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StrijdigheidofnietigheidTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Strijdigheidofnietigheid.class);
        Strijdigheidofnietigheid strijdigheidofnietigheid1 = getStrijdigheidofnietigheidSample1();
        Strijdigheidofnietigheid strijdigheidofnietigheid2 = new Strijdigheidofnietigheid();
        assertThat(strijdigheidofnietigheid1).isNotEqualTo(strijdigheidofnietigheid2);

        strijdigheidofnietigheid2.setId(strijdigheidofnietigheid1.getId());
        assertThat(strijdigheidofnietigheid1).isEqualTo(strijdigheidofnietigheid2);

        strijdigheidofnietigheid2 = getStrijdigheidofnietigheidSample2();
        assertThat(strijdigheidofnietigheid1).isNotEqualTo(strijdigheidofnietigheid2);
    }
}
