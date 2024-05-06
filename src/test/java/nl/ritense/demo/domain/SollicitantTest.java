package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SollicitantTestSamples.*;
import static nl.ritense.demo.domain.SollicitatieTestSamples.*;
import static nl.ritense.demo.domain.SollicitatiegesprekTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SollicitantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sollicitant.class);
        Sollicitant sollicitant1 = getSollicitantSample1();
        Sollicitant sollicitant2 = new Sollicitant();
        assertThat(sollicitant1).isNotEqualTo(sollicitant2);

        sollicitant2.setId(sollicitant1.getId());
        assertThat(sollicitant1).isEqualTo(sollicitant2);

        sollicitant2 = getSollicitantSample2();
        assertThat(sollicitant1).isNotEqualTo(sollicitant2);
    }

    @Test
    void solliciteertopfunctieSollicitatieTest() throws Exception {
        Sollicitant sollicitant = getSollicitantRandomSampleGenerator();
        Sollicitatie sollicitatieBack = getSollicitatieRandomSampleGenerator();

        sollicitant.addSolliciteertopfunctieSollicitatie(sollicitatieBack);
        assertThat(sollicitant.getSolliciteertopfunctieSollicitaties()).containsOnly(sollicitatieBack);
        assertThat(sollicitatieBack.getSolliciteertopfunctieSollicitant()).isEqualTo(sollicitant);

        sollicitant.removeSolliciteertopfunctieSollicitatie(sollicitatieBack);
        assertThat(sollicitant.getSolliciteertopfunctieSollicitaties()).doesNotContain(sollicitatieBack);
        assertThat(sollicitatieBack.getSolliciteertopfunctieSollicitant()).isNull();

        sollicitant.solliciteertopfunctieSollicitaties(new HashSet<>(Set.of(sollicitatieBack)));
        assertThat(sollicitant.getSolliciteertopfunctieSollicitaties()).containsOnly(sollicitatieBack);
        assertThat(sollicitatieBack.getSolliciteertopfunctieSollicitant()).isEqualTo(sollicitant);

        sollicitant.setSolliciteertopfunctieSollicitaties(new HashSet<>());
        assertThat(sollicitant.getSolliciteertopfunctieSollicitaties()).doesNotContain(sollicitatieBack);
        assertThat(sollicitatieBack.getSolliciteertopfunctieSollicitant()).isNull();
    }

    @Test
    void kandidaatSollicitatiegesprekTest() throws Exception {
        Sollicitant sollicitant = getSollicitantRandomSampleGenerator();
        Sollicitatiegesprek sollicitatiegesprekBack = getSollicitatiegesprekRandomSampleGenerator();

        sollicitant.addKandidaatSollicitatiegesprek(sollicitatiegesprekBack);
        assertThat(sollicitant.getKandidaatSollicitatiegespreks()).containsOnly(sollicitatiegesprekBack);
        assertThat(sollicitatiegesprekBack.getKandidaatSollicitants()).containsOnly(sollicitant);

        sollicitant.removeKandidaatSollicitatiegesprek(sollicitatiegesprekBack);
        assertThat(sollicitant.getKandidaatSollicitatiegespreks()).doesNotContain(sollicitatiegesprekBack);
        assertThat(sollicitatiegesprekBack.getKandidaatSollicitants()).doesNotContain(sollicitant);

        sollicitant.kandidaatSollicitatiegespreks(new HashSet<>(Set.of(sollicitatiegesprekBack)));
        assertThat(sollicitant.getKandidaatSollicitatiegespreks()).containsOnly(sollicitatiegesprekBack);
        assertThat(sollicitatiegesprekBack.getKandidaatSollicitants()).containsOnly(sollicitant);

        sollicitant.setKandidaatSollicitatiegespreks(new HashSet<>());
        assertThat(sollicitant.getKandidaatSollicitatiegespreks()).doesNotContain(sollicitatiegesprekBack);
        assertThat(sollicitatiegesprekBack.getKandidaatSollicitants()).doesNotContain(sollicitant);
    }
}
