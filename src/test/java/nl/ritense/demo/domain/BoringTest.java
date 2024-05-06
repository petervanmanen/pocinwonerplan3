package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BoringTestSamples.*;
import static nl.ritense.demo.domain.ProjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BoringTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Boring.class);
        Boring boring1 = getBoringSample1();
        Boring boring2 = new Boring();
        assertThat(boring1).isNotEqualTo(boring2);

        boring2.setId(boring1.getId());
        assertThat(boring1).isEqualTo(boring2);

        boring2 = getBoringSample2();
        assertThat(boring1).isNotEqualTo(boring2);
    }

    @Test
    void heeftProjectTest() throws Exception {
        Boring boring = getBoringRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        boring.setHeeftProject(projectBack);
        assertThat(boring.getHeeftProject()).isEqualTo(projectBack);

        boring.heeftProject(null);
        assertThat(boring.getHeeftProject()).isNull();
    }
}
