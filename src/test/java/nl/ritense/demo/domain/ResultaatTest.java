package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ProjectTestSamples.*;
import static nl.ritense.demo.domain.ResultaatTestSamples.*;
import static nl.ritense.demo.domain.ResultaatsoortTestSamples.*;
import static nl.ritense.demo.domain.TrajectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResultaatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Resultaat.class);
        Resultaat resultaat1 = getResultaatSample1();
        Resultaat resultaat2 = new Resultaat();
        assertThat(resultaat1).isNotEqualTo(resultaat2);

        resultaat2.setId(resultaat1.getId());
        assertThat(resultaat1).isEqualTo(resultaat2);

        resultaat2 = getResultaatSample2();
        assertThat(resultaat1).isNotEqualTo(resultaat2);
    }

    @Test
    void soortresultaatResultaatsoortTest() throws Exception {
        Resultaat resultaat = getResultaatRandomSampleGenerator();
        Resultaatsoort resultaatsoortBack = getResultaatsoortRandomSampleGenerator();

        resultaat.setSoortresultaatResultaatsoort(resultaatsoortBack);
        assertThat(resultaat.getSoortresultaatResultaatsoort()).isEqualTo(resultaatsoortBack);

        resultaat.soortresultaatResultaatsoort(null);
        assertThat(resultaat.getSoortresultaatResultaatsoort()).isNull();
    }

    @Test
    void heeftresultaatProjectTest() throws Exception {
        Resultaat resultaat = getResultaatRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        resultaat.setHeeftresultaatProject(projectBack);
        assertThat(resultaat.getHeeftresultaatProject()).isEqualTo(projectBack);
        assertThat(projectBack.getHeeftresultaatResultaat()).isEqualTo(resultaat);

        resultaat.heeftresultaatProject(null);
        assertThat(resultaat.getHeeftresultaatProject()).isNull();
        assertThat(projectBack.getHeeftresultaatResultaat()).isNull();
    }

    @Test
    void heeftresultaatTrajectTest() throws Exception {
        Resultaat resultaat = getResultaatRandomSampleGenerator();
        Traject trajectBack = getTrajectRandomSampleGenerator();

        resultaat.setHeeftresultaatTraject(trajectBack);
        assertThat(resultaat.getHeeftresultaatTraject()).isEqualTo(trajectBack);
        assertThat(trajectBack.getHeeftresultaatResultaat()).isEqualTo(resultaat);

        resultaat.heeftresultaatTraject(null);
        assertThat(resultaat.getHeeftresultaatTraject()).isNull();
        assertThat(trajectBack.getHeeftresultaatResultaat()).isNull();
    }
}
