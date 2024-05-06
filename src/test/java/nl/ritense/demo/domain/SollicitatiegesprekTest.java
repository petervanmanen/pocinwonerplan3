package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SollicitantTestSamples.*;
import static nl.ritense.demo.domain.SollicitatieTestSamples.*;
import static nl.ritense.demo.domain.SollicitatiegesprekTestSamples.*;
import static nl.ritense.demo.domain.WerknemerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SollicitatiegesprekTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sollicitatiegesprek.class);
        Sollicitatiegesprek sollicitatiegesprek1 = getSollicitatiegesprekSample1();
        Sollicitatiegesprek sollicitatiegesprek2 = new Sollicitatiegesprek();
        assertThat(sollicitatiegesprek1).isNotEqualTo(sollicitatiegesprek2);

        sollicitatiegesprek2.setId(sollicitatiegesprek1.getId());
        assertThat(sollicitatiegesprek1).isEqualTo(sollicitatiegesprek2);

        sollicitatiegesprek2 = getSollicitatiegesprekSample2();
        assertThat(sollicitatiegesprek1).isNotEqualTo(sollicitatiegesprek2);
    }

    @Test
    void inkadervanSollicitatieTest() throws Exception {
        Sollicitatiegesprek sollicitatiegesprek = getSollicitatiegesprekRandomSampleGenerator();
        Sollicitatie sollicitatieBack = getSollicitatieRandomSampleGenerator();

        sollicitatiegesprek.setInkadervanSollicitatie(sollicitatieBack);
        assertThat(sollicitatiegesprek.getInkadervanSollicitatie()).isEqualTo(sollicitatieBack);

        sollicitatiegesprek.inkadervanSollicitatie(null);
        assertThat(sollicitatiegesprek.getInkadervanSollicitatie()).isNull();
    }

    @Test
    void kandidaatSollicitantTest() throws Exception {
        Sollicitatiegesprek sollicitatiegesprek = getSollicitatiegesprekRandomSampleGenerator();
        Sollicitant sollicitantBack = getSollicitantRandomSampleGenerator();

        sollicitatiegesprek.addKandidaatSollicitant(sollicitantBack);
        assertThat(sollicitatiegesprek.getKandidaatSollicitants()).containsOnly(sollicitantBack);

        sollicitatiegesprek.removeKandidaatSollicitant(sollicitantBack);
        assertThat(sollicitatiegesprek.getKandidaatSollicitants()).doesNotContain(sollicitantBack);

        sollicitatiegesprek.kandidaatSollicitants(new HashSet<>(Set.of(sollicitantBack)));
        assertThat(sollicitatiegesprek.getKandidaatSollicitants()).containsOnly(sollicitantBack);

        sollicitatiegesprek.setKandidaatSollicitants(new HashSet<>());
        assertThat(sollicitatiegesprek.getKandidaatSollicitants()).doesNotContain(sollicitantBack);
    }

    @Test
    void doetsollicitatiegesprekWerknemerTest() throws Exception {
        Sollicitatiegesprek sollicitatiegesprek = getSollicitatiegesprekRandomSampleGenerator();
        Werknemer werknemerBack = getWerknemerRandomSampleGenerator();

        sollicitatiegesprek.addDoetsollicitatiegesprekWerknemer(werknemerBack);
        assertThat(sollicitatiegesprek.getDoetsollicitatiegesprekWerknemers()).containsOnly(werknemerBack);

        sollicitatiegesprek.removeDoetsollicitatiegesprekWerknemer(werknemerBack);
        assertThat(sollicitatiegesprek.getDoetsollicitatiegesprekWerknemers()).doesNotContain(werknemerBack);

        sollicitatiegesprek.doetsollicitatiegesprekWerknemers(new HashSet<>(Set.of(werknemerBack)));
        assertThat(sollicitatiegesprek.getDoetsollicitatiegesprekWerknemers()).containsOnly(werknemerBack);

        sollicitatiegesprek.setDoetsollicitatiegesprekWerknemers(new HashSet<>());
        assertThat(sollicitatiegesprek.getDoetsollicitatiegesprekWerknemers()).doesNotContain(werknemerBack);
    }
}
