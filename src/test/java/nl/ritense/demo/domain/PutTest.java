package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LocatieTestSamples.*;
import static nl.ritense.demo.domain.ProjectTestSamples.*;
import static nl.ritense.demo.domain.PutTestSamples.*;
import static nl.ritense.demo.domain.VlakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PutTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Put.class);
        Put put1 = getPutSample1();
        Put put2 = new Put();
        assertThat(put1).isNotEqualTo(put2);

        put2.setId(put1.getId());
        assertThat(put1).isEqualTo(put2);

        put2 = getPutSample2();
        assertThat(put1).isNotEqualTo(put2);
    }

    @Test
    void heeftVlakTest() throws Exception {
        Put put = getPutRandomSampleGenerator();
        Vlak vlakBack = getVlakRandomSampleGenerator();

        put.addHeeftVlak(vlakBack);
        assertThat(put.getHeeftVlaks()).containsOnly(vlakBack);
        assertThat(vlakBack.getHeeftPut()).isEqualTo(put);

        put.removeHeeftVlak(vlakBack);
        assertThat(put.getHeeftVlaks()).doesNotContain(vlakBack);
        assertThat(vlakBack.getHeeftPut()).isNull();

        put.heeftVlaks(new HashSet<>(Set.of(vlakBack)));
        assertThat(put.getHeeftVlaks()).containsOnly(vlakBack);
        assertThat(vlakBack.getHeeftPut()).isEqualTo(put);

        put.setHeeftVlaks(new HashSet<>());
        assertThat(put.getHeeftVlaks()).doesNotContain(vlakBack);
        assertThat(vlakBack.getHeeftPut()).isNull();
    }

    @Test
    void heeftlocatieLocatieTest() throws Exception {
        Put put = getPutRandomSampleGenerator();
        Locatie locatieBack = getLocatieRandomSampleGenerator();

        put.addHeeftlocatieLocatie(locatieBack);
        assertThat(put.getHeeftlocatieLocaties()).containsOnly(locatieBack);

        put.removeHeeftlocatieLocatie(locatieBack);
        assertThat(put.getHeeftlocatieLocaties()).doesNotContain(locatieBack);

        put.heeftlocatieLocaties(new HashSet<>(Set.of(locatieBack)));
        assertThat(put.getHeeftlocatieLocaties()).containsOnly(locatieBack);

        put.setHeeftlocatieLocaties(new HashSet<>());
        assertThat(put.getHeeftlocatieLocaties()).doesNotContain(locatieBack);
    }

    @Test
    void heeftProjectTest() throws Exception {
        Put put = getPutRandomSampleGenerator();
        Project projectBack = getProjectRandomSampleGenerator();

        put.setHeeftProject(projectBack);
        assertThat(put.getHeeftProject()).isEqualTo(projectBack);

        put.heeftProject(null);
        assertThat(put.getHeeftProject()).isNull();
    }
}
