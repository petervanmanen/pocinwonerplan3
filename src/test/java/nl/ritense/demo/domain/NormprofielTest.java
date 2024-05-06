package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.NormprofielTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NormprofielTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Normprofiel.class);
        Normprofiel normprofiel1 = getNormprofielSample1();
        Normprofiel normprofiel2 = new Normprofiel();
        assertThat(normprofiel1).isNotEqualTo(normprofiel2);

        normprofiel2.setId(normprofiel1.getId());
        assertThat(normprofiel1).isEqualTo(normprofiel2);

        normprofiel2 = getNormprofielSample2();
        assertThat(normprofiel1).isNotEqualTo(normprofiel2);
    }
}
