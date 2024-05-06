package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanduidingverblijfsrechtTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AanduidingverblijfsrechtTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aanduidingverblijfsrecht.class);
        Aanduidingverblijfsrecht aanduidingverblijfsrecht1 = getAanduidingverblijfsrechtSample1();
        Aanduidingverblijfsrecht aanduidingverblijfsrecht2 = new Aanduidingverblijfsrecht();
        assertThat(aanduidingverblijfsrecht1).isNotEqualTo(aanduidingverblijfsrecht2);

        aanduidingverblijfsrecht2.setId(aanduidingverblijfsrecht1.getId());
        assertThat(aanduidingverblijfsrecht1).isEqualTo(aanduidingverblijfsrecht2);

        aanduidingverblijfsrecht2 = getAanduidingverblijfsrechtSample2();
        assertThat(aanduidingverblijfsrecht1).isNotEqualTo(aanduidingverblijfsrecht2);
    }
}
