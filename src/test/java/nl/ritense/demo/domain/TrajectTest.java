package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClientTestSamples.*;
import static nl.ritense.demo.domain.ClientbegeleiderTestSamples.*;
import static nl.ritense.demo.domain.ProjectTestSamples.*;
import static nl.ritense.demo.domain.ResultaatTestSamples.*;
import static nl.ritense.demo.domain.TrajectTestSamples.*;
import static nl.ritense.demo.domain.TrajectsoortTestSamples.*;
import static nl.ritense.demo.domain.UitstroomredenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TrajectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Traject.class);
        Traject traject1 = getTrajectSample1();
        Traject traject2 = new Traject();
        assertThat(traject1).isNotEqualTo(traject2);

        traject2.setId(traject1.getId());
        assertThat(traject1).isEqualTo(traject2);

        traject2 = getTrajectSample2();
        assertThat(traject1).isNotEqualTo(traject2);
    }

    @Test
    void heeftresultaatResultaatTest() throws Exception {
        Traject traject = getTrajectRandomSampleGenerator();
        Resultaat resultaatBack = getResultaatRandomSampleGenerator();

        traject.setHeeftresultaatResultaat(resultaatBack);
        assertThat(traject.getHeeftresultaatResultaat()).isEqualTo(resultaatBack);

        traject.heeftresultaatResultaat(null);
        assertThat(traject.getHeeftresultaatResultaat()).isNull();
    }

    @Test
    void heeftuitstroomredenUitstroomredenTest() throws Exception {
        Traject traject = getTrajectRandomSampleGenerator();
        Uitstroomreden uitstroomredenBack = getUitstroomredenRandomSampleGenerator();

        traject.setHeeftuitstroomredenUitstroomreden(uitstroomredenBack);
        assertThat(traject.getHeeftuitstroomredenUitstroomreden()).isEqualTo(uitstroomredenBack);

        traject.heeftuitstroomredenUitstroomreden(null);
        assertThat(traject.getHeeftuitstroomredenUitstroomreden()).isNull();
    }

    @Test
    void heeftprojectProjectTest() throws Exception {
        Traject traject = getTrajectRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        traject.addHeeftprojectProject(projectBack);
        assertThat(traject.getHeeftprojectProjects()).containsOnly(projectBack);
        assertThat(projectBack.getHeeftprojectTraject()).isEqualTo(traject);

        traject.removeHeeftprojectProject(projectBack);
        assertThat(traject.getHeeftprojectProjects()).doesNotContain(projectBack);
        assertThat(projectBack.getHeeftprojectTraject()).isNull();

        traject.heeftprojectProjects(new HashSet<>(Set.of(projectBack)));
        assertThat(traject.getHeeftprojectProjects()).containsOnly(projectBack);
        assertThat(projectBack.getHeeftprojectTraject()).isEqualTo(traject);

        traject.setHeeftprojectProjects(new HashSet<>());
        assertThat(traject.getHeeftprojectProjects()).doesNotContain(projectBack);
        assertThat(projectBack.getHeeftprojectTraject()).isNull();
    }

    @Test
    void istrajectsoortTrajectsoortTest() throws Exception {
        Traject traject = getTrajectRandomSampleGenerator();
        Trajectsoort trajectsoortBack = getTrajectsoortRandomSampleGenerator();

        traject.setIstrajectsoortTrajectsoort(trajectsoortBack);
        assertThat(traject.getIstrajectsoortTrajectsoort()).isEqualTo(trajectsoortBack);

        traject.istrajectsoortTrajectsoort(null);
        assertThat(traject.getIstrajectsoortTrajectsoort()).isNull();
    }

    @Test
    void heeftparticipatietrajectClientTest() throws Exception {
        Traject traject = getTrajectRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        traject.setHeeftparticipatietrajectClient(clientBack);
        assertThat(traject.getHeeftparticipatietrajectClient()).isEqualTo(clientBack);

        traject.heeftparticipatietrajectClient(null);
        assertThat(traject.getHeeftparticipatietrajectClient()).isNull();
    }

    @Test
    void heefttrajectClientTest() throws Exception {
        Traject traject = getTrajectRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        traject.setHeefttrajectClient(clientBack);
        assertThat(traject.getHeefttrajectClient()).isEqualTo(clientBack);

        traject.heefttrajectClient(null);
        assertThat(traject.getHeefttrajectClient()).isNull();
    }

    @Test
    void begeleidtClientbegeleiderTest() throws Exception {
        Traject traject = getTrajectRandomSampleGenerator();
        Clientbegeleider clientbegeleiderBack = getClientbegeleiderRandomSampleGenerator();

        traject.addBegeleidtClientbegeleider(clientbegeleiderBack);
        assertThat(traject.getBegeleidtClientbegeleiders()).containsOnly(clientbegeleiderBack);
        assertThat(clientbegeleiderBack.getBegeleidtTraject()).isEqualTo(traject);

        traject.removeBegeleidtClientbegeleider(clientbegeleiderBack);
        assertThat(traject.getBegeleidtClientbegeleiders()).doesNotContain(clientbegeleiderBack);
        assertThat(clientbegeleiderBack.getBegeleidtTraject()).isNull();

        traject.begeleidtClientbegeleiders(new HashSet<>(Set.of(clientbegeleiderBack)));
        assertThat(traject.getBegeleidtClientbegeleiders()).containsOnly(clientbegeleiderBack);
        assertThat(clientbegeleiderBack.getBegeleidtTraject()).isEqualTo(traject);

        traject.setBegeleidtClientbegeleiders(new HashSet<>());
        assertThat(traject.getBegeleidtClientbegeleiders()).doesNotContain(clientbegeleiderBack);
        assertThat(clientbegeleiderBack.getBegeleidtTraject()).isNull();
    }
}
