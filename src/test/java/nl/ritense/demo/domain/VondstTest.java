package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ArtefactTestSamples.*;
import static nl.ritense.demo.domain.VondstTestSamples.*;
import static nl.ritense.demo.domain.VullingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VondstTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vondst.class);
        Vondst vondst1 = getVondstSample1();
        Vondst vondst2 = new Vondst();
        assertThat(vondst1).isNotEqualTo(vondst2);

        vondst2.setId(vondst1.getId());
        assertThat(vondst1).isEqualTo(vondst2);

        vondst2 = getVondstSample2();
        assertThat(vondst1).isNotEqualTo(vondst2);
    }

    @Test
    void bevatArtefactTest() throws Exception {
        Vondst vondst = getVondstRandomSampleGenerator();
        Artefact artefactBack = getArtefactRandomSampleGenerator();

        vondst.addBevatArtefact(artefactBack);
        assertThat(vondst.getBevatArtefacts()).containsOnly(artefactBack);
        assertThat(artefactBack.getBevatVondst()).isEqualTo(vondst);

        vondst.removeBevatArtefact(artefactBack);
        assertThat(vondst.getBevatArtefacts()).doesNotContain(artefactBack);
        assertThat(artefactBack.getBevatVondst()).isNull();

        vondst.bevatArtefacts(new HashSet<>(Set.of(artefactBack)));
        assertThat(vondst.getBevatArtefacts()).containsOnly(artefactBack);
        assertThat(artefactBack.getBevatVondst()).isEqualTo(vondst);

        vondst.setBevatArtefacts(new HashSet<>());
        assertThat(vondst.getBevatArtefacts()).doesNotContain(artefactBack);
        assertThat(artefactBack.getBevatVondst()).isNull();
    }

    @Test
    void heeftVullingTest() throws Exception {
        Vondst vondst = getVondstRandomSampleGenerator();
        Vulling vullingBack = getVullingRandomSampleGenerator();

        vondst.setHeeftVulling(vullingBack);
        assertThat(vondst.getHeeftVulling()).isEqualTo(vullingBack);

        vondst.heeftVulling(null);
        assertThat(vondst.getHeeftVulling()).isNull();
    }
}
