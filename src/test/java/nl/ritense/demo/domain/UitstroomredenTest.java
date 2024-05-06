package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ProjectTestSamples.*;
import static nl.ritense.demo.domain.TrajectTestSamples.*;
import static nl.ritense.demo.domain.UitstroomredenTestSamples.*;
import static nl.ritense.demo.domain.UitstroomredensoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UitstroomredenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uitstroomreden.class);
        Uitstroomreden uitstroomreden1 = getUitstroomredenSample1();
        Uitstroomreden uitstroomreden2 = new Uitstroomreden();
        assertThat(uitstroomreden1).isNotEqualTo(uitstroomreden2);

        uitstroomreden2.setId(uitstroomreden1.getId());
        assertThat(uitstroomreden1).isEqualTo(uitstroomreden2);

        uitstroomreden2 = getUitstroomredenSample2();
        assertThat(uitstroomreden1).isNotEqualTo(uitstroomreden2);
    }

    @Test
    void soortuitstroomredenUitstroomredensoortTest() throws Exception {
        Uitstroomreden uitstroomreden = getUitstroomredenRandomSampleGenerator();
        Uitstroomredensoort uitstroomredensoortBack = getUitstroomredensoortRandomSampleGenerator();

        uitstroomreden.setSoortuitstroomredenUitstroomredensoort(uitstroomredensoortBack);
        assertThat(uitstroomreden.getSoortuitstroomredenUitstroomredensoort()).isEqualTo(uitstroomredensoortBack);

        uitstroomreden.soortuitstroomredenUitstroomredensoort(null);
        assertThat(uitstroomreden.getSoortuitstroomredenUitstroomredensoort()).isNull();
    }

    @Test
    void heeftuitstroomredenProjectTest() throws Exception {
        Uitstroomreden uitstroomreden = getUitstroomredenRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        uitstroomreden.setHeeftuitstroomredenProject(projectBack);
        assertThat(uitstroomreden.getHeeftuitstroomredenProject()).isEqualTo(projectBack);
        assertThat(projectBack.getHeeftuitstroomredenUitstroomreden()).isEqualTo(uitstroomreden);

        uitstroomreden.heeftuitstroomredenProject(null);
        assertThat(uitstroomreden.getHeeftuitstroomredenProject()).isNull();
        assertThat(projectBack.getHeeftuitstroomredenUitstroomreden()).isNull();
    }

    @Test
    void heeftuitstroomredenTrajectTest() throws Exception {
        Uitstroomreden uitstroomreden = getUitstroomredenRandomSampleGenerator();
        Traject trajectBack = getTrajectRandomSampleGenerator();

        uitstroomreden.setHeeftuitstroomredenTraject(trajectBack);
        assertThat(uitstroomreden.getHeeftuitstroomredenTraject()).isEqualTo(trajectBack);
        assertThat(trajectBack.getHeeftuitstroomredenUitstroomreden()).isEqualTo(uitstroomreden);

        uitstroomreden.heeftuitstroomredenTraject(null);
        assertThat(uitstroomreden.getHeeftuitstroomredenTraject()).isNull();
        assertThat(trajectBack.getHeeftuitstroomredenUitstroomreden()).isNull();
    }
}
