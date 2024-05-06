package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DeclaratieTestSamples.*;
import static nl.ritense.demo.domain.DeclaratiesoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeclaratiesoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Declaratiesoort.class);
        Declaratiesoort declaratiesoort1 = getDeclaratiesoortSample1();
        Declaratiesoort declaratiesoort2 = new Declaratiesoort();
        assertThat(declaratiesoort1).isNotEqualTo(declaratiesoort2);

        declaratiesoort2.setId(declaratiesoort1.getId());
        assertThat(declaratiesoort1).isEqualTo(declaratiesoort2);

        declaratiesoort2 = getDeclaratiesoortSample2();
        assertThat(declaratiesoort1).isNotEqualTo(declaratiesoort2);
    }

    @Test
    void soortdeclaratieDeclaratieTest() throws Exception {
        Declaratiesoort declaratiesoort = getDeclaratiesoortRandomSampleGenerator();
        Declaratie declaratieBack = getDeclaratieRandomSampleGenerator();

        declaratiesoort.addSoortdeclaratieDeclaratie(declaratieBack);
        assertThat(declaratiesoort.getSoortdeclaratieDeclaraties()).containsOnly(declaratieBack);
        assertThat(declaratieBack.getSoortdeclaratieDeclaratiesoort()).isEqualTo(declaratiesoort);

        declaratiesoort.removeSoortdeclaratieDeclaratie(declaratieBack);
        assertThat(declaratiesoort.getSoortdeclaratieDeclaraties()).doesNotContain(declaratieBack);
        assertThat(declaratieBack.getSoortdeclaratieDeclaratiesoort()).isNull();

        declaratiesoort.soortdeclaratieDeclaraties(new HashSet<>(Set.of(declaratieBack)));
        assertThat(declaratiesoort.getSoortdeclaratieDeclaraties()).containsOnly(declaratieBack);
        assertThat(declaratieBack.getSoortdeclaratieDeclaratiesoort()).isEqualTo(declaratiesoort);

        declaratiesoort.setSoortdeclaratieDeclaraties(new HashSet<>());
        assertThat(declaratiesoort.getSoortdeclaratieDeclaraties()).doesNotContain(declaratieBack);
        assertThat(declaratieBack.getSoortdeclaratieDeclaratiesoort()).isNull();
    }
}
