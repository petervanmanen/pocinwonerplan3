package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.IdealisatieTestSamples.*;
import static nl.ritense.demo.domain.RegeltekstTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IdealisatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Idealisatie.class);
        Idealisatie idealisatie1 = getIdealisatieSample1();
        Idealisatie idealisatie2 = new Idealisatie();
        assertThat(idealisatie1).isNotEqualTo(idealisatie2);

        idealisatie2.setId(idealisatie1.getId());
        assertThat(idealisatie1).isEqualTo(idealisatie2);

        idealisatie2 = getIdealisatieSample2();
        assertThat(idealisatie1).isNotEqualTo(idealisatie2);
    }

    @Test
    void heeftidealisatieRegeltekstTest() throws Exception {
        Idealisatie idealisatie = getIdealisatieRandomSampleGenerator();
        Regeltekst regeltekstBack = getRegeltekstRandomSampleGenerator();

        idealisatie.addHeeftidealisatieRegeltekst(regeltekstBack);
        assertThat(idealisatie.getHeeftidealisatieRegelteksts()).containsOnly(regeltekstBack);
        assertThat(regeltekstBack.getHeeftidealisatieIdealisaties()).containsOnly(idealisatie);

        idealisatie.removeHeeftidealisatieRegeltekst(regeltekstBack);
        assertThat(idealisatie.getHeeftidealisatieRegelteksts()).doesNotContain(regeltekstBack);
        assertThat(regeltekstBack.getHeeftidealisatieIdealisaties()).doesNotContain(idealisatie);

        idealisatie.heeftidealisatieRegelteksts(new HashSet<>(Set.of(regeltekstBack)));
        assertThat(idealisatie.getHeeftidealisatieRegelteksts()).containsOnly(regeltekstBack);
        assertThat(regeltekstBack.getHeeftidealisatieIdealisaties()).containsOnly(idealisatie);

        idealisatie.setHeeftidealisatieRegelteksts(new HashSet<>());
        assertThat(idealisatie.getHeeftidealisatieRegelteksts()).doesNotContain(regeltekstBack);
        assertThat(regeltekstBack.getHeeftidealisatieIdealisaties()).doesNotContain(idealisatie);
    }
}
