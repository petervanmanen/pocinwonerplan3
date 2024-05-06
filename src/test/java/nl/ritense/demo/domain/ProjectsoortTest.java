package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ProjectTestSamples.*;
import static nl.ritense.demo.domain.ProjectsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projectsoort.class);
        Projectsoort projectsoort1 = getProjectsoortSample1();
        Projectsoort projectsoort2 = new Projectsoort();
        assertThat(projectsoort1).isNotEqualTo(projectsoort2);

        projectsoort2.setId(projectsoort1.getId());
        assertThat(projectsoort1).isEqualTo(projectsoort2);

        projectsoort2 = getProjectsoortSample2();
        assertThat(projectsoort1).isNotEqualTo(projectsoort2);
    }

    @Test
    void soortprojectProjectTest() throws Exception {
        Projectsoort projectsoort = getProjectsoortRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        projectsoort.addSoortprojectProject(projectBack);
        assertThat(projectsoort.getSoortprojectProjects()).containsOnly(projectBack);
        assertThat(projectBack.getSoortprojectProjectsoort()).isEqualTo(projectsoort);

        projectsoort.removeSoortprojectProject(projectBack);
        assertThat(projectsoort.getSoortprojectProjects()).doesNotContain(projectBack);
        assertThat(projectBack.getSoortprojectProjectsoort()).isNull();

        projectsoort.soortprojectProjects(new HashSet<>(Set.of(projectBack)));
        assertThat(projectsoort.getSoortprojectProjects()).containsOnly(projectBack);
        assertThat(projectBack.getSoortprojectProjectsoort()).isEqualTo(projectsoort);

        projectsoort.setSoortprojectProjects(new HashSet<>());
        assertThat(projectsoort.getSoortprojectProjects()).doesNotContain(projectBack);
        assertThat(projectBack.getSoortprojectProjectsoort()).isNull();
    }
}
