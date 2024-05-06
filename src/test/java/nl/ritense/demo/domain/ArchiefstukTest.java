package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanvraagTestSamples.*;
import static nl.ritense.demo.domain.ArchiefTestSamples.*;
import static nl.ritense.demo.domain.ArchiefstukTestSamples.*;
import static nl.ritense.demo.domain.IndelingTestSamples.*;
import static nl.ritense.demo.domain.OrdeningsschemaTestSamples.*;
import static nl.ritense.demo.domain.PeriodeTestSamples.*;
import static nl.ritense.demo.domain.UitgeverTestSamples.*;
import static nl.ritense.demo.domain.VindplaatsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArchiefstukTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Archiefstuk.class);
        Archiefstuk archiefstuk1 = getArchiefstukSample1();
        Archiefstuk archiefstuk2 = new Archiefstuk();
        assertThat(archiefstuk1).isNotEqualTo(archiefstuk2);

        archiefstuk2.setId(archiefstuk1.getId());
        assertThat(archiefstuk1).isEqualTo(archiefstuk2);

        archiefstuk2 = getArchiefstukSample2();
        assertThat(archiefstuk1).isNotEqualTo(archiefstuk2);
    }

    @Test
    void isonderdeelvanArchiefTest() throws Exception {
        Archiefstuk archiefstuk = getArchiefstukRandomSampleGenerator();
        Archief archiefBack = getArchiefRandomSampleGenerator();

        archiefstuk.setIsonderdeelvanArchief(archiefBack);
        assertThat(archiefstuk.getIsonderdeelvanArchief()).isEqualTo(archiefBack);

        archiefstuk.isonderdeelvanArchief(null);
        assertThat(archiefstuk.getIsonderdeelvanArchief()).isNull();
    }

    @Test
    void heeftUitgeverTest() throws Exception {
        Archiefstuk archiefstuk = getArchiefstukRandomSampleGenerator();
        Uitgever uitgeverBack = getUitgeverRandomSampleGenerator();

        archiefstuk.setHeeftUitgever(uitgeverBack);
        assertThat(archiefstuk.getHeeftUitgever()).isEqualTo(uitgeverBack);

        archiefstuk.heeftUitgever(null);
        assertThat(archiefstuk.getHeeftUitgever()).isNull();
    }

    @Test
    void heeftVindplaatsTest() throws Exception {
        Archiefstuk archiefstuk = getArchiefstukRandomSampleGenerator();
        Vindplaats vindplaatsBack = getVindplaatsRandomSampleGenerator();

        archiefstuk.setHeeftVindplaats(vindplaatsBack);
        assertThat(archiefstuk.getHeeftVindplaats()).isEqualTo(vindplaatsBack);

        archiefstuk.heeftVindplaats(null);
        assertThat(archiefstuk.getHeeftVindplaats()).isNull();
    }

    @Test
    void heeftOrdeningsschemaTest() throws Exception {
        Archiefstuk archiefstuk = getArchiefstukRandomSampleGenerator();
        Ordeningsschema ordeningsschemaBack = getOrdeningsschemaRandomSampleGenerator();

        archiefstuk.addHeeftOrdeningsschema(ordeningsschemaBack);
        assertThat(archiefstuk.getHeeftOrdeningsschemas()).containsOnly(ordeningsschemaBack);

        archiefstuk.removeHeeftOrdeningsschema(ordeningsschemaBack);
        assertThat(archiefstuk.getHeeftOrdeningsschemas()).doesNotContain(ordeningsschemaBack);

        archiefstuk.heeftOrdeningsschemas(new HashSet<>(Set.of(ordeningsschemaBack)));
        assertThat(archiefstuk.getHeeftOrdeningsschemas()).containsOnly(ordeningsschemaBack);

        archiefstuk.setHeeftOrdeningsschemas(new HashSet<>());
        assertThat(archiefstuk.getHeeftOrdeningsschemas()).doesNotContain(ordeningsschemaBack);
    }

    @Test
    void stamtuitPeriodeTest() throws Exception {
        Archiefstuk archiefstuk = getArchiefstukRandomSampleGenerator();
        Periode periodeBack = getPeriodeRandomSampleGenerator();

        archiefstuk.addStamtuitPeriode(periodeBack);
        assertThat(archiefstuk.getStamtuitPeriodes()).containsOnly(periodeBack);

        archiefstuk.removeStamtuitPeriode(periodeBack);
        assertThat(archiefstuk.getStamtuitPeriodes()).doesNotContain(periodeBack);

        archiefstuk.stamtuitPeriodes(new HashSet<>(Set.of(periodeBack)));
        assertThat(archiefstuk.getStamtuitPeriodes()).containsOnly(periodeBack);

        archiefstuk.setStamtuitPeriodes(new HashSet<>());
        assertThat(archiefstuk.getStamtuitPeriodes()).doesNotContain(periodeBack);
    }

    @Test
    void valtbinnenIndelingTest() throws Exception {
        Archiefstuk archiefstuk = getArchiefstukRandomSampleGenerator();
        Indeling indelingBack = getIndelingRandomSampleGenerator();

        archiefstuk.setValtbinnenIndeling(indelingBack);
        assertThat(archiefstuk.getValtbinnenIndeling()).isEqualTo(indelingBack);

        archiefstuk.valtbinnenIndeling(null);
        assertThat(archiefstuk.getValtbinnenIndeling()).isNull();
    }

    @Test
    void voorAanvraagTest() throws Exception {
        Archiefstuk archiefstuk = getArchiefstukRandomSampleGenerator();
        Aanvraag aanvraagBack = getAanvraagRandomSampleGenerator();

        archiefstuk.addVoorAanvraag(aanvraagBack);
        assertThat(archiefstuk.getVoorAanvraags()).containsOnly(aanvraagBack);
        assertThat(aanvraagBack.getVoorArchiefstuks()).containsOnly(archiefstuk);

        archiefstuk.removeVoorAanvraag(aanvraagBack);
        assertThat(archiefstuk.getVoorAanvraags()).doesNotContain(aanvraagBack);
        assertThat(aanvraagBack.getVoorArchiefstuks()).doesNotContain(archiefstuk);

        archiefstuk.voorAanvraags(new HashSet<>(Set.of(aanvraagBack)));
        assertThat(archiefstuk.getVoorAanvraags()).containsOnly(aanvraagBack);
        assertThat(aanvraagBack.getVoorArchiefstuks()).containsOnly(archiefstuk);

        archiefstuk.setVoorAanvraags(new HashSet<>());
        assertThat(archiefstuk.getVoorAanvraags()).doesNotContain(aanvraagBack);
        assertThat(aanvraagBack.getVoorArchiefstuks()).doesNotContain(archiefstuk);
    }
}
