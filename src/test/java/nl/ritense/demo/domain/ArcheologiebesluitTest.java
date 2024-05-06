package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ArcheologiebesluitTestSamples.*;
import static nl.ritense.demo.domain.ProjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArcheologiebesluitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Archeologiebesluit.class);
        Archeologiebesluit archeologiebesluit1 = getArcheologiebesluitSample1();
        Archeologiebesluit archeologiebesluit2 = new Archeologiebesluit();
        assertThat(archeologiebesluit1).isNotEqualTo(archeologiebesluit2);

        archeologiebesluit2.setId(archeologiebesluit1.getId());
        assertThat(archeologiebesluit1).isEqualTo(archeologiebesluit2);

        archeologiebesluit2 = getArcheologiebesluitSample2();
        assertThat(archeologiebesluit1).isNotEqualTo(archeologiebesluit2);
    }

    @Test
    void heeftProjectTest() throws Exception {
        Archeologiebesluit archeologiebesluit = getArcheologiebesluitRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        archeologiebesluit.setHeeftProject(projectBack);
        assertThat(archeologiebesluit.getHeeftProject()).isEqualTo(projectBack);

        archeologiebesluit.heeftProject(null);
        assertThat(archeologiebesluit.getHeeftProject()).isNull();
    }
}
