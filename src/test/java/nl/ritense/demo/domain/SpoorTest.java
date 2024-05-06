package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SpoorTestSamples.*;
import static nl.ritense.demo.domain.VlakTestSamples.*;
import static nl.ritense.demo.domain.VullingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SpoorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Spoor.class);
        Spoor spoor1 = getSpoorSample1();
        Spoor spoor2 = new Spoor();
        assertThat(spoor1).isNotEqualTo(spoor2);

        spoor2.setId(spoor1.getId());
        assertThat(spoor1).isEqualTo(spoor2);

        spoor2 = getSpoorSample2();
        assertThat(spoor1).isNotEqualTo(spoor2);
    }

    @Test
    void heeftVullingTest() throws Exception {
        Spoor spoor = getSpoorRandomSampleGenerator();
        Vulling vullingBack = getVullingRandomSampleGenerator();

        spoor.addHeeftVulling(vullingBack);
        assertThat(spoor.getHeeftVullings()).containsOnly(vullingBack);
        assertThat(vullingBack.getHeeftSpoor()).isEqualTo(spoor);

        spoor.removeHeeftVulling(vullingBack);
        assertThat(spoor.getHeeftVullings()).doesNotContain(vullingBack);
        assertThat(vullingBack.getHeeftSpoor()).isNull();

        spoor.heeftVullings(new HashSet<>(Set.of(vullingBack)));
        assertThat(spoor.getHeeftVullings()).containsOnly(vullingBack);
        assertThat(vullingBack.getHeeftSpoor()).isEqualTo(spoor);

        spoor.setHeeftVullings(new HashSet<>());
        assertThat(spoor.getHeeftVullings()).doesNotContain(vullingBack);
        assertThat(vullingBack.getHeeftSpoor()).isNull();
    }

    @Test
    void heeftVlakTest() throws Exception {
        Spoor spoor = getSpoorRandomSampleGenerator();
        Vlak vlakBack = getVlakRandomSampleGenerator();

        spoor.setHeeftVlak(vlakBack);
        assertThat(spoor.getHeeftVlak()).isEqualTo(vlakBack);

        spoor.heeftVlak(null);
        assertThat(spoor.getHeeftVlak()).isNull();
    }
}
