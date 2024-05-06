package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ContainerTestSamples.*;
import static nl.ritense.demo.domain.VulgraadmetingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VulgraadmetingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vulgraadmeting.class);
        Vulgraadmeting vulgraadmeting1 = getVulgraadmetingSample1();
        Vulgraadmeting vulgraadmeting2 = new Vulgraadmeting();
        assertThat(vulgraadmeting1).isNotEqualTo(vulgraadmeting2);

        vulgraadmeting2.setId(vulgraadmeting1.getId());
        assertThat(vulgraadmeting1).isEqualTo(vulgraadmeting2);

        vulgraadmeting2 = getVulgraadmetingSample2();
        assertThat(vulgraadmeting1).isNotEqualTo(vulgraadmeting2);
    }

    @Test
    void heeftContainerTest() throws Exception {
        Vulgraadmeting vulgraadmeting = getVulgraadmetingRandomSampleGenerator();
        Container containerBack = getContainerRandomSampleGenerator();

        vulgraadmeting.setHeeftContainer(containerBack);
        assertThat(vulgraadmeting.getHeeftContainer()).isEqualTo(containerBack);

        vulgraadmeting.heeftContainer(null);
        assertThat(vulgraadmeting.getHeeftContainer()).isNull();
    }
}
