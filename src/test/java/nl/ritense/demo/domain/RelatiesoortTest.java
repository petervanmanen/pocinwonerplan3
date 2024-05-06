package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.RelatieTestSamples.*;
import static nl.ritense.demo.domain.RelatiesoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RelatiesoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Relatiesoort.class);
        Relatiesoort relatiesoort1 = getRelatiesoortSample1();
        Relatiesoort relatiesoort2 = new Relatiesoort();
        assertThat(relatiesoort1).isNotEqualTo(relatiesoort2);

        relatiesoort2.setId(relatiesoort1.getId());
        assertThat(relatiesoort1).isEqualTo(relatiesoort2);

        relatiesoort2 = getRelatiesoortSample2();
        assertThat(relatiesoort1).isNotEqualTo(relatiesoort2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Relatiesoort relatiesoort = new Relatiesoort();
        assertThat(relatiesoort.hashCode()).isZero();

        Relatiesoort relatiesoort1 = getRelatiesoortSample1();
        relatiesoort.setId(relatiesoort1.getId());
        assertThat(relatiesoort).hasSameHashCodeAs(relatiesoort1);
    }

    @Test
    void issoortRelatieTest() throws Exception {
        Relatiesoort relatiesoort = getRelatiesoortRandomSampleGenerator();
        Relatie relatieBack = getRelatieRandomSampleGenerator();

        relatiesoort.addIssoortRelatie(relatieBack);
        assertThat(relatiesoort.getIssoortRelaties()).containsOnly(relatieBack);
        assertThat(relatieBack.getIssoortRelatiesoort()).isEqualTo(relatiesoort);

        relatiesoort.removeIssoortRelatie(relatieBack);
        assertThat(relatiesoort.getIssoortRelaties()).doesNotContain(relatieBack);
        assertThat(relatieBack.getIssoortRelatiesoort()).isNull();

        relatiesoort.issoortRelaties(new HashSet<>(Set.of(relatieBack)));
        assertThat(relatiesoort.getIssoortRelaties()).containsOnly(relatieBack);
        assertThat(relatieBack.getIssoortRelatiesoort()).isEqualTo(relatiesoort);

        relatiesoort.setIssoortRelaties(new HashSet<>());
        assertThat(relatiesoort.getIssoortRelaties()).doesNotContain(relatieBack);
        assertThat(relatieBack.getIssoortRelatiesoort()).isNull();
    }
}
