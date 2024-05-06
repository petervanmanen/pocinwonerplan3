package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ArchiefstukTestSamples.*;
import static nl.ritense.demo.domain.DepotTestSamples.*;
import static nl.ritense.demo.domain.KastTestSamples.*;
import static nl.ritense.demo.domain.PlankTestSamples.*;
import static nl.ritense.demo.domain.ProjectTestSamples.*;
import static nl.ritense.demo.domain.StellingTestSamples.*;
import static nl.ritense.demo.domain.VindplaatsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VindplaatsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vindplaats.class);
        Vindplaats vindplaats1 = getVindplaatsSample1();
        Vindplaats vindplaats2 = new Vindplaats();
        assertThat(vindplaats1).isNotEqualTo(vindplaats2);

        vindplaats2.setId(vindplaats1.getId());
        assertThat(vindplaats1).isEqualTo(vindplaats2);

        vindplaats2 = getVindplaatsSample2();
        assertThat(vindplaats1).isNotEqualTo(vindplaats2);
    }

    @Test
    void hoortbijProjectTest() throws Exception {
        Vindplaats vindplaats = getVindplaatsRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        vindplaats.setHoortbijProject(projectBack);
        assertThat(vindplaats.getHoortbijProject()).isEqualTo(projectBack);

        vindplaats.hoortbijProject(null);
        assertThat(vindplaats.getHoortbijProject()).isNull();
    }

    @Test
    void istevindeninDepotTest() throws Exception {
        Vindplaats vindplaats = getVindplaatsRandomSampleGenerator();
        Depot depotBack = getDepotRandomSampleGenerator();

        vindplaats.setIstevindeninDepot(depotBack);
        assertThat(vindplaats.getIstevindeninDepot()).isEqualTo(depotBack);

        vindplaats.istevindeninDepot(null);
        assertThat(vindplaats.getIstevindeninDepot()).isNull();
    }

    @Test
    void istevindeninKastTest() throws Exception {
        Vindplaats vindplaats = getVindplaatsRandomSampleGenerator();
        Kast kastBack = getKastRandomSampleGenerator();

        vindplaats.setIstevindeninKast(kastBack);
        assertThat(vindplaats.getIstevindeninKast()).isEqualTo(kastBack);

        vindplaats.istevindeninKast(null);
        assertThat(vindplaats.getIstevindeninKast()).isNull();
    }

    @Test
    void istevindeninPlankTest() throws Exception {
        Vindplaats vindplaats = getVindplaatsRandomSampleGenerator();
        Plank plankBack = getPlankRandomSampleGenerator();

        vindplaats.setIstevindeninPlank(plankBack);
        assertThat(vindplaats.getIstevindeninPlank()).isEqualTo(plankBack);

        vindplaats.istevindeninPlank(null);
        assertThat(vindplaats.getIstevindeninPlank()).isNull();
    }

    @Test
    void istevindeninStellingTest() throws Exception {
        Vindplaats vindplaats = getVindplaatsRandomSampleGenerator();
        Stelling stellingBack = getStellingRandomSampleGenerator();

        vindplaats.setIstevindeninStelling(stellingBack);
        assertThat(vindplaats.getIstevindeninStelling()).isEqualTo(stellingBack);

        vindplaats.istevindeninStelling(null);
        assertThat(vindplaats.getIstevindeninStelling()).isNull();
    }

    @Test
    void heeftArchiefstukTest() throws Exception {
        Vindplaats vindplaats = getVindplaatsRandomSampleGenerator();
        Archiefstuk archiefstukBack = getArchiefstukRandomSampleGenerator();

        vindplaats.addHeeftArchiefstuk(archiefstukBack);
        assertThat(vindplaats.getHeeftArchiefstuks()).containsOnly(archiefstukBack);
        assertThat(archiefstukBack.getHeeftVindplaats()).isEqualTo(vindplaats);

        vindplaats.removeHeeftArchiefstuk(archiefstukBack);
        assertThat(vindplaats.getHeeftArchiefstuks()).doesNotContain(archiefstukBack);
        assertThat(archiefstukBack.getHeeftVindplaats()).isNull();

        vindplaats.heeftArchiefstuks(new HashSet<>(Set.of(archiefstukBack)));
        assertThat(vindplaats.getHeeftArchiefstuks()).containsOnly(archiefstukBack);
        assertThat(archiefstukBack.getHeeftVindplaats()).isEqualTo(vindplaats);

        vindplaats.setHeeftArchiefstuks(new HashSet<>());
        assertThat(vindplaats.getHeeftArchiefstuks()).doesNotContain(archiefstukBack);
        assertThat(archiefstukBack.getHeeftVindplaats()).isNull();
    }
}
