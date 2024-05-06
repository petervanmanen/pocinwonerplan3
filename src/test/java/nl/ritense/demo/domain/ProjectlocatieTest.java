package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LocatieTestSamples.*;
import static nl.ritense.demo.domain.ProjectTestSamples.*;
import static nl.ritense.demo.domain.ProjectlocatieTestSamples.*;
import static nl.ritense.demo.domain.VerzoekTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProjectlocatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Projectlocatie.class);
        Projectlocatie projectlocatie1 = getProjectlocatieSample1();
        Projectlocatie projectlocatie2 = new Projectlocatie();
        assertThat(projectlocatie1).isNotEqualTo(projectlocatie2);

        projectlocatie2.setId(projectlocatie1.getId());
        assertThat(projectlocatie1).isEqualTo(projectlocatie2);

        projectlocatie2 = getProjectlocatieSample2();
        assertThat(projectlocatie1).isNotEqualTo(projectlocatie2);
    }

    @Test
    void betreftLocatieTest() throws Exception {
        Projectlocatie projectlocatie = getProjectlocatieRandomSampleGenerator();
        Locatie locatieBack = getLocatieRandomSampleGenerator();

        projectlocatie.setBetreftLocatie(locatieBack);
        assertThat(projectlocatie.getBetreftLocatie()).isEqualTo(locatieBack);

        projectlocatie.betreftLocatie(null);
        assertThat(projectlocatie.getBetreftLocatie()).isNull();
    }

    @Test
    void heeftProjectTest() throws Exception {
        Projectlocatie projectlocatie = getProjectlocatieRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        projectlocatie.setHeeftProject(projectBack);
        assertThat(projectlocatie.getHeeftProject()).isEqualTo(projectBack);

        projectlocatie.heeftProject(null);
        assertThat(projectlocatie.getHeeftProject()).isNull();
    }

    @Test
    void betreftVerzoekTest() throws Exception {
        Projectlocatie projectlocatie = getProjectlocatieRandomSampleGenerator();
        Verzoek verzoekBack = getVerzoekRandomSampleGenerator();

        projectlocatie.addBetreftVerzoek(verzoekBack);
        assertThat(projectlocatie.getBetreftVerzoeks()).containsOnly(verzoekBack);
        assertThat(verzoekBack.getBetreftProjectlocaties()).containsOnly(projectlocatie);

        projectlocatie.removeBetreftVerzoek(verzoekBack);
        assertThat(projectlocatie.getBetreftVerzoeks()).doesNotContain(verzoekBack);
        assertThat(verzoekBack.getBetreftProjectlocaties()).doesNotContain(projectlocatie);

        projectlocatie.betreftVerzoeks(new HashSet<>(Set.of(verzoekBack)));
        assertThat(projectlocatie.getBetreftVerzoeks()).containsOnly(verzoekBack);
        assertThat(verzoekBack.getBetreftProjectlocaties()).containsOnly(projectlocatie);

        projectlocatie.setBetreftVerzoeks(new HashSet<>());
        assertThat(projectlocatie.getBetreftVerzoeks()).doesNotContain(verzoekBack);
        assertThat(verzoekBack.getBetreftProjectlocaties()).doesNotContain(projectlocatie);
    }
}
