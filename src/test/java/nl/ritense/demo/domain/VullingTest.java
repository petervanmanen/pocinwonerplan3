package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SpoorTestSamples.*;
import static nl.ritense.demo.domain.VondstTestSamples.*;
import static nl.ritense.demo.domain.VullingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VullingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vulling.class);
        Vulling vulling1 = getVullingSample1();
        Vulling vulling2 = new Vulling();
        assertThat(vulling1).isNotEqualTo(vulling2);

        vulling2.setId(vulling1.getId());
        assertThat(vulling1).isEqualTo(vulling2);

        vulling2 = getVullingSample2();
        assertThat(vulling1).isNotEqualTo(vulling2);
    }

    @Test
    void heeftVondstTest() throws Exception {
        Vulling vulling = getVullingRandomSampleGenerator();
        Vondst vondstBack = getVondstRandomSampleGenerator();

        vulling.addHeeftVondst(vondstBack);
        assertThat(vulling.getHeeftVondsts()).containsOnly(vondstBack);
        assertThat(vondstBack.getHeeftVulling()).isEqualTo(vulling);

        vulling.removeHeeftVondst(vondstBack);
        assertThat(vulling.getHeeftVondsts()).doesNotContain(vondstBack);
        assertThat(vondstBack.getHeeftVulling()).isNull();

        vulling.heeftVondsts(new HashSet<>(Set.of(vondstBack)));
        assertThat(vulling.getHeeftVondsts()).containsOnly(vondstBack);
        assertThat(vondstBack.getHeeftVulling()).isEqualTo(vulling);

        vulling.setHeeftVondsts(new HashSet<>());
        assertThat(vulling.getHeeftVondsts()).doesNotContain(vondstBack);
        assertThat(vondstBack.getHeeftVulling()).isNull();
    }

    @Test
    void heeftSpoorTest() throws Exception {
        Vulling vulling = getVullingRandomSampleGenerator();
        Spoor spoorBack = getSpoorRandomSampleGenerator();

        vulling.setHeeftSpoor(spoorBack);
        assertThat(vulling.getHeeftSpoor()).isEqualTo(spoorBack);

        vulling.heeftSpoor(null);
        assertThat(vulling.getHeeftSpoor()).isNull();
    }
}
