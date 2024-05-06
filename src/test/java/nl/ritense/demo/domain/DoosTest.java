package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ArtefactTestSamples.*;
import static nl.ritense.demo.domain.DoosTestSamples.*;
import static nl.ritense.demo.domain.MagazijnlocatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DoosTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Doos.class);
        Doos doos1 = getDoosSample1();
        Doos doos2 = new Doos();
        assertThat(doos1).isNotEqualTo(doos2);

        doos2.setId(doos1.getId());
        assertThat(doos1).isEqualTo(doos2);

        doos2 = getDoosSample2();
        assertThat(doos1).isNotEqualTo(doos2);
    }

    @Test
    void staatopMagazijnlocatieTest() throws Exception {
        Doos doos = getDoosRandomSampleGenerator();
        Magazijnlocatie magazijnlocatieBack = getMagazijnlocatieRandomSampleGenerator();

        doos.setStaatopMagazijnlocatie(magazijnlocatieBack);
        assertThat(doos.getStaatopMagazijnlocatie()).isEqualTo(magazijnlocatieBack);

        doos.staatopMagazijnlocatie(null);
        assertThat(doos.getStaatopMagazijnlocatie()).isNull();
    }

    @Test
    void zitinArtefactTest() throws Exception {
        Doos doos = getDoosRandomSampleGenerator();
        Artefact artefactBack = getArtefactRandomSampleGenerator();

        doos.addZitinArtefact(artefactBack);
        assertThat(doos.getZitinArtefacts()).containsOnly(artefactBack);
        assertThat(artefactBack.getZitinDoos()).isEqualTo(doos);

        doos.removeZitinArtefact(artefactBack);
        assertThat(doos.getZitinArtefacts()).doesNotContain(artefactBack);
        assertThat(artefactBack.getZitinDoos()).isNull();

        doos.zitinArtefacts(new HashSet<>(Set.of(artefactBack)));
        assertThat(doos.getZitinArtefacts()).containsOnly(artefactBack);
        assertThat(artefactBack.getZitinDoos()).isEqualTo(doos);

        doos.setZitinArtefacts(new HashSet<>());
        assertThat(doos.getZitinArtefacts()).doesNotContain(artefactBack);
        assertThat(artefactBack.getZitinDoos()).isNull();
    }
}
