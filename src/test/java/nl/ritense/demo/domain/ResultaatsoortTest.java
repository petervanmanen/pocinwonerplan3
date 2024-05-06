package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ResultaatTestSamples.*;
import static nl.ritense.demo.domain.ResultaatsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResultaatsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resultaatsoort.class);
        Resultaatsoort resultaatsoort1 = getResultaatsoortSample1();
        Resultaatsoort resultaatsoort2 = new Resultaatsoort();
        assertThat(resultaatsoort1).isNotEqualTo(resultaatsoort2);

        resultaatsoort2.setId(resultaatsoort1.getId());
        assertThat(resultaatsoort1).isEqualTo(resultaatsoort2);

        resultaatsoort2 = getResultaatsoortSample2();
        assertThat(resultaatsoort1).isNotEqualTo(resultaatsoort2);
    }

    @Test
    void soortresultaatResultaatTest() throws Exception {
        Resultaatsoort resultaatsoort = getResultaatsoortRandomSampleGenerator();
        Resultaat resultaatBack = getResultaatRandomSampleGenerator();

        resultaatsoort.addSoortresultaatResultaat(resultaatBack);
        assertThat(resultaatsoort.getSoortresultaatResultaats()).containsOnly(resultaatBack);
        assertThat(resultaatBack.getSoortresultaatResultaatsoort()).isEqualTo(resultaatsoort);

        resultaatsoort.removeSoortresultaatResultaat(resultaatBack);
        assertThat(resultaatsoort.getSoortresultaatResultaats()).doesNotContain(resultaatBack);
        assertThat(resultaatBack.getSoortresultaatResultaatsoort()).isNull();

        resultaatsoort.soortresultaatResultaats(new HashSet<>(Set.of(resultaatBack)));
        assertThat(resultaatsoort.getSoortresultaatResultaats()).containsOnly(resultaatBack);
        assertThat(resultaatBack.getSoortresultaatResultaatsoort()).isEqualTo(resultaatsoort);

        resultaatsoort.setSoortresultaatResultaats(new HashSet<>());
        assertThat(resultaatsoort.getSoortresultaatResultaats()).doesNotContain(resultaatBack);
        assertThat(resultaatBack.getSoortresultaatResultaatsoort()).isNull();
    }
}
