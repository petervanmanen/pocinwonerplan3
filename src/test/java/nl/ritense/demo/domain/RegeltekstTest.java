package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.IdealisatieTestSamples.*;
import static nl.ritense.demo.domain.LocatieTestSamples.*;
import static nl.ritense.demo.domain.OmgevingsdocumentTestSamples.*;
import static nl.ritense.demo.domain.RegeltekstTestSamples.*;
import static nl.ritense.demo.domain.RegeltekstTestSamples.*;
import static nl.ritense.demo.domain.ThemaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegeltekstTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Regeltekst.class);
        Regeltekst regeltekst1 = getRegeltekstSample1();
        Regeltekst regeltekst2 = new Regeltekst();
        assertThat(regeltekst1).isNotEqualTo(regeltekst2);

        regeltekst2.setId(regeltekst1.getId());
        assertThat(regeltekst1).isEqualTo(regeltekst2);

        regeltekst2 = getRegeltekstSample2();
        assertThat(regeltekst1).isNotEqualTo(regeltekst2);
    }

    @Test
    void werkingsgebiedRegeltekstTest() throws Exception {
        Regeltekst regeltekst = getRegeltekstRandomSampleGenerator();
        Regeltekst regeltekstBack = getRegeltekstRandomSampleGenerator();

        regeltekst.addWerkingsgebiedRegeltekst(regeltekstBack);
        assertThat(regeltekst.getWerkingsgebiedRegelteksts()).containsOnly(regeltekstBack);
        assertThat(regeltekstBack.getWerkingsgebiedRegeltekst2()).isEqualTo(regeltekst);

        regeltekst.removeWerkingsgebiedRegeltekst(regeltekstBack);
        assertThat(regeltekst.getWerkingsgebiedRegelteksts()).doesNotContain(regeltekstBack);
        assertThat(regeltekstBack.getWerkingsgebiedRegeltekst2()).isNull();

        regeltekst.werkingsgebiedRegelteksts(new HashSet<>(Set.of(regeltekstBack)));
        assertThat(regeltekst.getWerkingsgebiedRegelteksts()).containsOnly(regeltekstBack);
        assertThat(regeltekstBack.getWerkingsgebiedRegeltekst2()).isEqualTo(regeltekst);

        regeltekst.setWerkingsgebiedRegelteksts(new HashSet<>());
        assertThat(regeltekst.getWerkingsgebiedRegelteksts()).doesNotContain(regeltekstBack);
        assertThat(regeltekstBack.getWerkingsgebiedRegeltekst2()).isNull();
    }

    @Test
    void isgerelateerdRegeltekstTest() throws Exception {
        Regeltekst regeltekst = getRegeltekstRandomSampleGenerator();
        Regeltekst regeltekstBack = getRegeltekstRandomSampleGenerator();

        regeltekst.addIsgerelateerdRegeltekst(regeltekstBack);
        assertThat(regeltekst.getIsgerelateerdRegelteksts()).containsOnly(regeltekstBack);
        assertThat(regeltekstBack.getIsgerelateerdRegeltekst2()).isEqualTo(regeltekst);

        regeltekst.removeIsgerelateerdRegeltekst(regeltekstBack);
        assertThat(regeltekst.getIsgerelateerdRegelteksts()).doesNotContain(regeltekstBack);
        assertThat(regeltekstBack.getIsgerelateerdRegeltekst2()).isNull();

        regeltekst.isgerelateerdRegelteksts(new HashSet<>(Set.of(regeltekstBack)));
        assertThat(regeltekst.getIsgerelateerdRegelteksts()).containsOnly(regeltekstBack);
        assertThat(regeltekstBack.getIsgerelateerdRegeltekst2()).isEqualTo(regeltekst);

        regeltekst.setIsgerelateerdRegelteksts(new HashSet<>());
        assertThat(regeltekst.getIsgerelateerdRegelteksts()).doesNotContain(regeltekstBack);
        assertThat(regeltekstBack.getIsgerelateerdRegeltekst2()).isNull();
    }

    @Test
    void heeftthemaThemaTest() throws Exception {
        Regeltekst regeltekst = getRegeltekstRandomSampleGenerator();
        Thema themaBack = getThemaRandomSampleGenerator();

        regeltekst.addHeeftthemaThema(themaBack);
        assertThat(regeltekst.getHeeftthemaThemas()).containsOnly(themaBack);

        regeltekst.removeHeeftthemaThema(themaBack);
        assertThat(regeltekst.getHeeftthemaThemas()).doesNotContain(themaBack);

        regeltekst.heeftthemaThemas(new HashSet<>(Set.of(themaBack)));
        assertThat(regeltekst.getHeeftthemaThemas()).containsOnly(themaBack);

        regeltekst.setHeeftthemaThemas(new HashSet<>());
        assertThat(regeltekst.getHeeftthemaThemas()).doesNotContain(themaBack);
    }

    @Test
    void heeftidealisatieIdealisatieTest() throws Exception {
        Regeltekst regeltekst = getRegeltekstRandomSampleGenerator();
        Idealisatie idealisatieBack = getIdealisatieRandomSampleGenerator();

        regeltekst.addHeeftidealisatieIdealisatie(idealisatieBack);
        assertThat(regeltekst.getHeeftidealisatieIdealisaties()).containsOnly(idealisatieBack);

        regeltekst.removeHeeftidealisatieIdealisatie(idealisatieBack);
        assertThat(regeltekst.getHeeftidealisatieIdealisaties()).doesNotContain(idealisatieBack);

        regeltekst.heeftidealisatieIdealisaties(new HashSet<>(Set.of(idealisatieBack)));
        assertThat(regeltekst.getHeeftidealisatieIdealisaties()).containsOnly(idealisatieBack);

        regeltekst.setHeeftidealisatieIdealisaties(new HashSet<>());
        assertThat(regeltekst.getHeeftidealisatieIdealisaties()).doesNotContain(idealisatieBack);
    }

    @Test
    void werkingsgebiedLocatieTest() throws Exception {
        Regeltekst regeltekst = getRegeltekstRandomSampleGenerator();
        Locatie locatieBack = getLocatieRandomSampleGenerator();

        regeltekst.addWerkingsgebiedLocatie(locatieBack);
        assertThat(regeltekst.getWerkingsgebiedLocaties()).containsOnly(locatieBack);

        regeltekst.removeWerkingsgebiedLocatie(locatieBack);
        assertThat(regeltekst.getWerkingsgebiedLocaties()).doesNotContain(locatieBack);

        regeltekst.werkingsgebiedLocaties(new HashSet<>(Set.of(locatieBack)));
        assertThat(regeltekst.getWerkingsgebiedLocaties()).containsOnly(locatieBack);

        regeltekst.setWerkingsgebiedLocaties(new HashSet<>());
        assertThat(regeltekst.getWerkingsgebiedLocaties()).doesNotContain(locatieBack);
    }

    @Test
    void bevatOmgevingsdocumentTest() throws Exception {
        Regeltekst regeltekst = getRegeltekstRandomSampleGenerator();
        Omgevingsdocument omgevingsdocumentBack = getOmgevingsdocumentRandomSampleGenerator();

        regeltekst.setBevatOmgevingsdocument(omgevingsdocumentBack);
        assertThat(regeltekst.getBevatOmgevingsdocument()).isEqualTo(omgevingsdocumentBack);

        regeltekst.bevatOmgevingsdocument(null);
        assertThat(regeltekst.getBevatOmgevingsdocument()).isNull();
    }

    @Test
    void werkingsgebiedRegeltekst2Test() throws Exception {
        Regeltekst regeltekst = getRegeltekstRandomSampleGenerator();
        Regeltekst regeltekstBack = getRegeltekstRandomSampleGenerator();

        regeltekst.setWerkingsgebiedRegeltekst2(regeltekstBack);
        assertThat(regeltekst.getWerkingsgebiedRegeltekst2()).isEqualTo(regeltekstBack);

        regeltekst.werkingsgebiedRegeltekst2(null);
        assertThat(regeltekst.getWerkingsgebiedRegeltekst2()).isNull();
    }

    @Test
    void isgerelateerdRegeltekst2Test() throws Exception {
        Regeltekst regeltekst = getRegeltekstRandomSampleGenerator();
        Regeltekst regeltekstBack = getRegeltekstRandomSampleGenerator();

        regeltekst.setIsgerelateerdRegeltekst2(regeltekstBack);
        assertThat(regeltekst.getIsgerelateerdRegeltekst2()).isEqualTo(regeltekstBack);

        regeltekst.isgerelateerdRegeltekst2(null);
        assertThat(regeltekst.getIsgerelateerdRegeltekst2()).isNull();
    }
}
