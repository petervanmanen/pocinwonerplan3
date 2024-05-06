package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SollicitantTestSamples.*;
import static nl.ritense.demo.domain.SollicitatieTestSamples.*;
import static nl.ritense.demo.domain.SollicitatiegesprekTestSamples.*;
import static nl.ritense.demo.domain.VacatureTestSamples.*;
import static nl.ritense.demo.domain.WerknemerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SollicitatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sollicitatie.class);
        Sollicitatie sollicitatie1 = getSollicitatieSample1();
        Sollicitatie sollicitatie2 = new Sollicitatie();
        assertThat(sollicitatie1).isNotEqualTo(sollicitatie2);

        sollicitatie2.setId(sollicitatie1.getId());
        assertThat(sollicitatie1).isEqualTo(sollicitatie2);

        sollicitatie2 = getSollicitatieSample2();
        assertThat(sollicitatie1).isNotEqualTo(sollicitatie2);
    }

    @Test
    void opvacatureVacatureTest() throws Exception {
        Sollicitatie sollicitatie = getSollicitatieRandomSampleGenerator();
        Vacature vacatureBack = getVacatureRandomSampleGenerator();

        sollicitatie.setOpvacatureVacature(vacatureBack);
        assertThat(sollicitatie.getOpvacatureVacature()).isEqualTo(vacatureBack);

        sollicitatie.opvacatureVacature(null);
        assertThat(sollicitatie.getOpvacatureVacature()).isNull();
    }

    @Test
    void solliciteertopfunctieSollicitantTest() throws Exception {
        Sollicitatie sollicitatie = getSollicitatieRandomSampleGenerator();
        Sollicitant sollicitantBack = getSollicitantRandomSampleGenerator();

        sollicitatie.setSolliciteertopfunctieSollicitant(sollicitantBack);
        assertThat(sollicitatie.getSolliciteertopfunctieSollicitant()).isEqualTo(sollicitantBack);

        sollicitatie.solliciteertopfunctieSollicitant(null);
        assertThat(sollicitatie.getSolliciteertopfunctieSollicitant()).isNull();
    }

    @Test
    void solliciteertWerknemerTest() throws Exception {
        Sollicitatie sollicitatie = getSollicitatieRandomSampleGenerator();
        Werknemer werknemerBack = getWerknemerRandomSampleGenerator();

        sollicitatie.setSolliciteertWerknemer(werknemerBack);
        assertThat(sollicitatie.getSolliciteertWerknemer()).isEqualTo(werknemerBack);

        sollicitatie.solliciteertWerknemer(null);
        assertThat(sollicitatie.getSolliciteertWerknemer()).isNull();
    }

    @Test
    void inkadervanSollicitatiegesprekTest() throws Exception {
        Sollicitatie sollicitatie = getSollicitatieRandomSampleGenerator();
        Sollicitatiegesprek sollicitatiegesprekBack = getSollicitatiegesprekRandomSampleGenerator();

        sollicitatie.addInkadervanSollicitatiegesprek(sollicitatiegesprekBack);
        assertThat(sollicitatie.getInkadervanSollicitatiegespreks()).containsOnly(sollicitatiegesprekBack);
        assertThat(sollicitatiegesprekBack.getInkadervanSollicitatie()).isEqualTo(sollicitatie);

        sollicitatie.removeInkadervanSollicitatiegesprek(sollicitatiegesprekBack);
        assertThat(sollicitatie.getInkadervanSollicitatiegespreks()).doesNotContain(sollicitatiegesprekBack);
        assertThat(sollicitatiegesprekBack.getInkadervanSollicitatie()).isNull();

        sollicitatie.inkadervanSollicitatiegespreks(new HashSet<>(Set.of(sollicitatiegesprekBack)));
        assertThat(sollicitatie.getInkadervanSollicitatiegespreks()).containsOnly(sollicitatiegesprekBack);
        assertThat(sollicitatiegesprekBack.getInkadervanSollicitatie()).isEqualTo(sollicitatie);

        sollicitatie.setInkadervanSollicitatiegespreks(new HashSet<>());
        assertThat(sollicitatie.getInkadervanSollicitatiegespreks()).doesNotContain(sollicitatiegesprekBack);
        assertThat(sollicitatiegesprekBack.getInkadervanSollicitatie()).isNull();
    }
}
