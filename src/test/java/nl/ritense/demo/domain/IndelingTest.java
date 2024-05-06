package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ArchiefTestSamples.*;
import static nl.ritense.demo.domain.ArchiefstukTestSamples.*;
import static nl.ritense.demo.domain.IndelingTestSamples.*;
import static nl.ritense.demo.domain.IndelingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndelingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Indeling.class);
        Indeling indeling1 = getIndelingSample1();
        Indeling indeling2 = new Indeling();
        assertThat(indeling1).isNotEqualTo(indeling2);

        indeling2.setId(indeling1.getId());
        assertThat(indeling1).isEqualTo(indeling2);

        indeling2 = getIndelingSample2();
        assertThat(indeling1).isNotEqualTo(indeling2);
    }

    @Test
    void valtbinnenArchiefstukTest() throws Exception {
        Indeling indeling = getIndelingRandomSampleGenerator();
        Archiefstuk archiefstukBack = getArchiefstukRandomSampleGenerator();

        indeling.addValtbinnenArchiefstuk(archiefstukBack);
        assertThat(indeling.getValtbinnenArchiefstuks()).containsOnly(archiefstukBack);
        assertThat(archiefstukBack.getValtbinnenIndeling()).isEqualTo(indeling);

        indeling.removeValtbinnenArchiefstuk(archiefstukBack);
        assertThat(indeling.getValtbinnenArchiefstuks()).doesNotContain(archiefstukBack);
        assertThat(archiefstukBack.getValtbinnenIndeling()).isNull();

        indeling.valtbinnenArchiefstuks(new HashSet<>(Set.of(archiefstukBack)));
        assertThat(indeling.getValtbinnenArchiefstuks()).containsOnly(archiefstukBack);
        assertThat(archiefstukBack.getValtbinnenIndeling()).isEqualTo(indeling);

        indeling.setValtbinnenArchiefstuks(new HashSet<>());
        assertThat(indeling.getValtbinnenArchiefstuks()).doesNotContain(archiefstukBack);
        assertThat(archiefstukBack.getValtbinnenIndeling()).isNull();
    }

    @Test
    void valtbinnenIndelingTest() throws Exception {
        Indeling indeling = getIndelingRandomSampleGenerator();
        Indeling indelingBack = getIndelingRandomSampleGenerator();

        indeling.addValtbinnenIndeling(indelingBack);
        assertThat(indeling.getValtbinnenIndelings()).containsOnly(indelingBack);
        assertThat(indelingBack.getValtbinnenIndeling2()).isEqualTo(indeling);

        indeling.removeValtbinnenIndeling(indelingBack);
        assertThat(indeling.getValtbinnenIndelings()).doesNotContain(indelingBack);
        assertThat(indelingBack.getValtbinnenIndeling2()).isNull();

        indeling.valtbinnenIndelings(new HashSet<>(Set.of(indelingBack)));
        assertThat(indeling.getValtbinnenIndelings()).containsOnly(indelingBack);
        assertThat(indelingBack.getValtbinnenIndeling2()).isEqualTo(indeling);

        indeling.setValtbinnenIndelings(new HashSet<>());
        assertThat(indeling.getValtbinnenIndelings()).doesNotContain(indelingBack);
        assertThat(indelingBack.getValtbinnenIndeling2()).isNull();
    }

    @Test
    void hoortbijArchiefTest() throws Exception {
        Indeling indeling = getIndelingRandomSampleGenerator();
        Archief archiefBack = getArchiefRandomSampleGenerator();

        indeling.setHoortbijArchief(archiefBack);
        assertThat(indeling.getHoortbijArchief()).isEqualTo(archiefBack);

        indeling.hoortbijArchief(null);
        assertThat(indeling.getHoortbijArchief()).isNull();
    }

    @Test
    void valtbinnenIndeling2Test() throws Exception {
        Indeling indeling = getIndelingRandomSampleGenerator();
        Indeling indelingBack = getIndelingRandomSampleGenerator();

        indeling.setValtbinnenIndeling2(indelingBack);
        assertThat(indeling.getValtbinnenIndeling2()).isEqualTo(indelingBack);

        indeling.valtbinnenIndeling2(null);
        assertThat(indeling.getValtbinnenIndeling2()).isNull();
    }
}
