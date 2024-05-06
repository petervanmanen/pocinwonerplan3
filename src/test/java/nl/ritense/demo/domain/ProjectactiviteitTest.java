package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ProjectTestSamples.*;
import static nl.ritense.demo.domain.ProjectactiviteitTestSamples.*;
import static nl.ritense.demo.domain.SpecificatieTestSamples.*;
import static nl.ritense.demo.domain.VerzoekTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectactiviteitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projectactiviteit.class);
        Projectactiviteit projectactiviteit1 = getProjectactiviteitSample1();
        Projectactiviteit projectactiviteit2 = new Projectactiviteit();
        assertThat(projectactiviteit1).isNotEqualTo(projectactiviteit2);

        projectactiviteit2.setId(projectactiviteit1.getId());
        assertThat(projectactiviteit1).isEqualTo(projectactiviteit2);

        projectactiviteit2 = getProjectactiviteitSample2();
        assertThat(projectactiviteit1).isNotEqualTo(projectactiviteit2);
    }

    @Test
    void heeftProjectTest() throws Exception {
        Projectactiviteit projectactiviteit = getProjectactiviteitRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        projectactiviteit.setHeeftProject(projectBack);
        assertThat(projectactiviteit.getHeeftProject()).isEqualTo(projectBack);

        projectactiviteit.heeftProject(null);
        assertThat(projectactiviteit.getHeeftProject()).isNull();
    }

    @Test
    void gedefinieerddoorSpecificatieTest() throws Exception {
        Projectactiviteit projectactiviteit = getProjectactiviteitRandomSampleGenerator();
        Specificatie specificatieBack = getSpecificatieRandomSampleGenerator();

        projectactiviteit.addGedefinieerddoorSpecificatie(specificatieBack);
        assertThat(projectactiviteit.getGedefinieerddoorSpecificaties()).containsOnly(specificatieBack);
        assertThat(specificatieBack.getGedefinieerddoorProjectactiviteit()).isEqualTo(projectactiviteit);

        projectactiviteit.removeGedefinieerddoorSpecificatie(specificatieBack);
        assertThat(projectactiviteit.getGedefinieerddoorSpecificaties()).doesNotContain(specificatieBack);
        assertThat(specificatieBack.getGedefinieerddoorProjectactiviteit()).isNull();

        projectactiviteit.gedefinieerddoorSpecificaties(new HashSet<>(Set.of(specificatieBack)));
        assertThat(projectactiviteit.getGedefinieerddoorSpecificaties()).containsOnly(specificatieBack);
        assertThat(specificatieBack.getGedefinieerddoorProjectactiviteit()).isEqualTo(projectactiviteit);

        projectactiviteit.setGedefinieerddoorSpecificaties(new HashSet<>());
        assertThat(projectactiviteit.getGedefinieerddoorSpecificaties()).doesNotContain(specificatieBack);
        assertThat(specificatieBack.getGedefinieerddoorProjectactiviteit()).isNull();
    }

    @Test
    void betreftVerzoekTest() throws Exception {
        Projectactiviteit projectactiviteit = getProjectactiviteitRandomSampleGenerator();
        Verzoek verzoekBack = getVerzoekRandomSampleGenerator();

        projectactiviteit.addBetreftVerzoek(verzoekBack);
        assertThat(projectactiviteit.getBetreftVerzoeks()).containsOnly(verzoekBack);
        assertThat(verzoekBack.getBetreftProjectactiviteits()).containsOnly(projectactiviteit);

        projectactiviteit.removeBetreftVerzoek(verzoekBack);
        assertThat(projectactiviteit.getBetreftVerzoeks()).doesNotContain(verzoekBack);
        assertThat(verzoekBack.getBetreftProjectactiviteits()).doesNotContain(projectactiviteit);

        projectactiviteit.betreftVerzoeks(new HashSet<>(Set.of(verzoekBack)));
        assertThat(projectactiviteit.getBetreftVerzoeks()).containsOnly(verzoekBack);
        assertThat(verzoekBack.getBetreftProjectactiviteits()).containsOnly(projectactiviteit);

        projectactiviteit.setBetreftVerzoeks(new HashSet<>());
        assertThat(projectactiviteit.getBetreftVerzoeks()).doesNotContain(verzoekBack);
        assertThat(verzoekBack.getBetreftProjectactiviteits()).doesNotContain(projectactiviteit);
    }
}
