package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ContainertypeTestSamples.*;
import static nl.ritense.demo.domain.RitTestSamples.*;
import static nl.ritense.demo.domain.VuilniswagenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VuilniswagenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vuilniswagen.class);
        Vuilniswagen vuilniswagen1 = getVuilniswagenSample1();
        Vuilniswagen vuilniswagen2 = new Vuilniswagen();
        assertThat(vuilniswagen1).isNotEqualTo(vuilniswagen2);

        vuilniswagen2.setId(vuilniswagen1.getId());
        assertThat(vuilniswagen1).isEqualTo(vuilniswagen2);

        vuilniswagen2 = getVuilniswagenSample2();
        assertThat(vuilniswagen1).isNotEqualTo(vuilniswagen2);
    }

    @Test
    void geschiktvoorContainertypeTest() throws Exception {
        Vuilniswagen vuilniswagen = getVuilniswagenRandomSampleGenerator();
        Containertype containertypeBack = getContainertypeRandomSampleGenerator();

        vuilniswagen.addGeschiktvoorContainertype(containertypeBack);
        assertThat(vuilniswagen.getGeschiktvoorContainertypes()).containsOnly(containertypeBack);

        vuilniswagen.removeGeschiktvoorContainertype(containertypeBack);
        assertThat(vuilniswagen.getGeschiktvoorContainertypes()).doesNotContain(containertypeBack);

        vuilniswagen.geschiktvoorContainertypes(new HashSet<>(Set.of(containertypeBack)));
        assertThat(vuilniswagen.getGeschiktvoorContainertypes()).containsOnly(containertypeBack);

        vuilniswagen.setGeschiktvoorContainertypes(new HashSet<>());
        assertThat(vuilniswagen.getGeschiktvoorContainertypes()).doesNotContain(containertypeBack);
    }

    @Test
    void uitgevoerdmetRitTest() throws Exception {
        Vuilniswagen vuilniswagen = getVuilniswagenRandomSampleGenerator();
        Rit ritBack = getRitRandomSampleGenerator();

        vuilniswagen.addUitgevoerdmetRit(ritBack);
        assertThat(vuilniswagen.getUitgevoerdmetRits()).containsOnly(ritBack);
        assertThat(ritBack.getUitgevoerdmetVuilniswagen()).isEqualTo(vuilniswagen);

        vuilniswagen.removeUitgevoerdmetRit(ritBack);
        assertThat(vuilniswagen.getUitgevoerdmetRits()).doesNotContain(ritBack);
        assertThat(ritBack.getUitgevoerdmetVuilniswagen()).isNull();

        vuilniswagen.uitgevoerdmetRits(new HashSet<>(Set.of(ritBack)));
        assertThat(vuilniswagen.getUitgevoerdmetRits()).containsOnly(ritBack);
        assertThat(ritBack.getUitgevoerdmetVuilniswagen()).isEqualTo(vuilniswagen);

        vuilniswagen.setUitgevoerdmetRits(new HashSet<>());
        assertThat(vuilniswagen.getUitgevoerdmetRits()).doesNotContain(ritBack);
        assertThat(ritBack.getUitgevoerdmetVuilniswagen()).isNull();
    }
}
