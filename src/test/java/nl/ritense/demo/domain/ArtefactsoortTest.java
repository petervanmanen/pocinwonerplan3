package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ArtefactTestSamples.*;
import static nl.ritense.demo.domain.ArtefactsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtefactsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Artefactsoort.class);
        Artefactsoort artefactsoort1 = getArtefactsoortSample1();
        Artefactsoort artefactsoort2 = new Artefactsoort();
        assertThat(artefactsoort1).isNotEqualTo(artefactsoort2);

        artefactsoort2.setId(artefactsoort1.getId());
        assertThat(artefactsoort1).isEqualTo(artefactsoort2);

        artefactsoort2 = getArtefactsoortSample2();
        assertThat(artefactsoort1).isNotEqualTo(artefactsoort2);
    }

    @Test
    void isvansoortArtefactTest() throws Exception {
        Artefactsoort artefactsoort = getArtefactsoortRandomSampleGenerator();
        Artefact artefactBack = getArtefactRandomSampleGenerator();

        artefactsoort.addIsvansoortArtefact(artefactBack);
        assertThat(artefactsoort.getIsvansoortArtefacts()).containsOnly(artefactBack);
        assertThat(artefactBack.getIsvansoortArtefactsoort()).isEqualTo(artefactsoort);

        artefactsoort.removeIsvansoortArtefact(artefactBack);
        assertThat(artefactsoort.getIsvansoortArtefacts()).doesNotContain(artefactBack);
        assertThat(artefactBack.getIsvansoortArtefactsoort()).isNull();

        artefactsoort.isvansoortArtefacts(new HashSet<>(Set.of(artefactBack)));
        assertThat(artefactsoort.getIsvansoortArtefacts()).containsOnly(artefactBack);
        assertThat(artefactBack.getIsvansoortArtefactsoort()).isEqualTo(artefactsoort);

        artefactsoort.setIsvansoortArtefacts(new HashSet<>());
        assertThat(artefactsoort.getIsvansoortArtefacts()).doesNotContain(artefactBack);
        assertThat(artefactBack.getIsvansoortArtefactsoort()).isNull();
    }
}
