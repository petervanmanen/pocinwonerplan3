package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ArtefactTestSamples.*;
import static nl.ritense.demo.domain.ArtefactsoortTestSamples.*;
import static nl.ritense.demo.domain.DoosTestSamples.*;
import static nl.ritense.demo.domain.VondstTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtefactTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Artefact.class);
        Artefact artefact1 = getArtefactSample1();
        Artefact artefact2 = new Artefact();
        assertThat(artefact1).isNotEqualTo(artefact2);

        artefact2.setId(artefact1.getId());
        assertThat(artefact1).isEqualTo(artefact2);

        artefact2 = getArtefactSample2();
        assertThat(artefact1).isNotEqualTo(artefact2);
    }

    @Test
    void zitinDoosTest() throws Exception {
        Artefact artefact = getArtefactRandomSampleGenerator();
        Doos doosBack = getDoosRandomSampleGenerator();

        artefact.setZitinDoos(doosBack);
        assertThat(artefact.getZitinDoos()).isEqualTo(doosBack);

        artefact.zitinDoos(null);
        assertThat(artefact.getZitinDoos()).isNull();
    }

    @Test
    void isvansoortArtefactsoortTest() throws Exception {
        Artefact artefact = getArtefactRandomSampleGenerator();
        Artefactsoort artefactsoortBack = getArtefactsoortRandomSampleGenerator();

        artefact.setIsvansoortArtefactsoort(artefactsoortBack);
        assertThat(artefact.getIsvansoortArtefactsoort()).isEqualTo(artefactsoortBack);

        artefact.isvansoortArtefactsoort(null);
        assertThat(artefact.getIsvansoortArtefactsoort()).isNull();
    }

    @Test
    void bevatVondstTest() throws Exception {
        Artefact artefact = getArtefactRandomSampleGenerator();
        Vondst vondstBack = getVondstRandomSampleGenerator();

        artefact.setBevatVondst(vondstBack);
        assertThat(artefact.getBevatVondst()).isEqualTo(vondstBack);

        artefact.bevatVondst(null);
        assertThat(artefact.getBevatVondst()).isNull();
    }
}
