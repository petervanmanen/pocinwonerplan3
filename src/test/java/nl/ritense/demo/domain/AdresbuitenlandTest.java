package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AdresbuitenlandTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdresbuitenlandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adresbuitenland.class);
        Adresbuitenland adresbuitenland1 = getAdresbuitenlandSample1();
        Adresbuitenland adresbuitenland2 = new Adresbuitenland();
        assertThat(adresbuitenland1).isNotEqualTo(adresbuitenland2);

        adresbuitenland2.setId(adresbuitenland1.getId());
        assertThat(adresbuitenland1).isEqualTo(adresbuitenland2);

        adresbuitenland2 = getAdresbuitenlandSample2();
        assertThat(adresbuitenland1).isNotEqualTo(adresbuitenland2);
    }
}
