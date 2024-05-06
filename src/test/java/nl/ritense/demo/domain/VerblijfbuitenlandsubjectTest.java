package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerblijfbuitenlandsubjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerblijfbuitenlandsubjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verblijfbuitenlandsubject.class);
        Verblijfbuitenlandsubject verblijfbuitenlandsubject1 = getVerblijfbuitenlandsubjectSample1();
        Verblijfbuitenlandsubject verblijfbuitenlandsubject2 = new Verblijfbuitenlandsubject();
        assertThat(verblijfbuitenlandsubject1).isNotEqualTo(verblijfbuitenlandsubject2);

        verblijfbuitenlandsubject2.setId(verblijfbuitenlandsubject1.getId());
        assertThat(verblijfbuitenlandsubject1).isEqualTo(verblijfbuitenlandsubject2);

        verblijfbuitenlandsubject2 = getVerblijfbuitenlandsubjectSample2();
        assertThat(verblijfbuitenlandsubject1).isNotEqualTo(verblijfbuitenlandsubject2);
    }
}
