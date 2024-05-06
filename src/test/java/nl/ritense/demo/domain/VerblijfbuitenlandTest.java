package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerblijfbuitenlandTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerblijfbuitenlandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verblijfbuitenland.class);
        Verblijfbuitenland verblijfbuitenland1 = getVerblijfbuitenlandSample1();
        Verblijfbuitenland verblijfbuitenland2 = new Verblijfbuitenland();
        assertThat(verblijfbuitenland1).isNotEqualTo(verblijfbuitenland2);

        verblijfbuitenland2.setId(verblijfbuitenland1.getId());
        assertThat(verblijfbuitenland1).isEqualTo(verblijfbuitenland2);

        verblijfbuitenland2 = getVerblijfbuitenlandSample2();
        assertThat(verblijfbuitenland1).isNotEqualTo(verblijfbuitenland2);
    }
}
