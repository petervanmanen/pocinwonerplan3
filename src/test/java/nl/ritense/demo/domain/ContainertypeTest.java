package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ContainerTestSamples.*;
import static nl.ritense.demo.domain.ContainertypeTestSamples.*;
import static nl.ritense.demo.domain.MeldingTestSamples.*;
import static nl.ritense.demo.domain.VuilniswagenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContainertypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Containertype.class);
        Containertype containertype1 = getContainertypeSample1();
        Containertype containertype2 = new Containertype();
        assertThat(containertype1).isNotEqualTo(containertype2);

        containertype2.setId(containertype1.getId());
        assertThat(containertype1).isEqualTo(containertype2);

        containertype2 = getContainertypeSample2();
        assertThat(containertype1).isNotEqualTo(containertype2);
    }

    @Test
    void soortContainerTest() throws Exception {
        Containertype containertype = getContainertypeRandomSampleGenerator();
        Container containerBack = getContainerRandomSampleGenerator();

        containertype.addSoortContainer(containerBack);
        assertThat(containertype.getSoortContainers()).containsOnly(containerBack);
        assertThat(containerBack.getSoortContainertype()).isEqualTo(containertype);

        containertype.removeSoortContainer(containerBack);
        assertThat(containertype.getSoortContainers()).doesNotContain(containerBack);
        assertThat(containerBack.getSoortContainertype()).isNull();

        containertype.soortContainers(new HashSet<>(Set.of(containerBack)));
        assertThat(containertype.getSoortContainers()).containsOnly(containerBack);
        assertThat(containerBack.getSoortContainertype()).isEqualTo(containertype);

        containertype.setSoortContainers(new HashSet<>());
        assertThat(containertype.getSoortContainers()).doesNotContain(containerBack);
        assertThat(containerBack.getSoortContainertype()).isNull();
    }

    @Test
    void betreftMeldingTest() throws Exception {
        Containertype containertype = getContainertypeRandomSampleGenerator();
        Melding meldingBack = getMeldingRandomSampleGenerator();

        containertype.addBetreftMelding(meldingBack);
        assertThat(containertype.getBetreftMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getBetreftContainertype()).isEqualTo(containertype);

        containertype.removeBetreftMelding(meldingBack);
        assertThat(containertype.getBetreftMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getBetreftContainertype()).isNull();

        containertype.betreftMeldings(new HashSet<>(Set.of(meldingBack)));
        assertThat(containertype.getBetreftMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getBetreftContainertype()).isEqualTo(containertype);

        containertype.setBetreftMeldings(new HashSet<>());
        assertThat(containertype.getBetreftMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getBetreftContainertype()).isNull();
    }

    @Test
    void geschiktvoorVuilniswagenTest() throws Exception {
        Containertype containertype = getContainertypeRandomSampleGenerator();
        Vuilniswagen vuilniswagenBack = getVuilniswagenRandomSampleGenerator();

        containertype.addGeschiktvoorVuilniswagen(vuilniswagenBack);
        assertThat(containertype.getGeschiktvoorVuilniswagens()).containsOnly(vuilniswagenBack);
        assertThat(vuilniswagenBack.getGeschiktvoorContainertypes()).containsOnly(containertype);

        containertype.removeGeschiktvoorVuilniswagen(vuilniswagenBack);
        assertThat(containertype.getGeschiktvoorVuilniswagens()).doesNotContain(vuilniswagenBack);
        assertThat(vuilniswagenBack.getGeschiktvoorContainertypes()).doesNotContain(containertype);

        containertype.geschiktvoorVuilniswagens(new HashSet<>(Set.of(vuilniswagenBack)));
        assertThat(containertype.getGeschiktvoorVuilniswagens()).containsOnly(vuilniswagenBack);
        assertThat(vuilniswagenBack.getGeschiktvoorContainertypes()).containsOnly(containertype);

        containertype.setGeschiktvoorVuilniswagens(new HashSet<>());
        assertThat(containertype.getGeschiktvoorVuilniswagens()).doesNotContain(vuilniswagenBack);
        assertThat(vuilniswagenBack.getGeschiktvoorContainertypes()).doesNotContain(containertype);
    }
}
