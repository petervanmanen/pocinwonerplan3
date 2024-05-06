package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ContainerTestSamples.*;
import static nl.ritense.demo.domain.ContainertypeTestSamples.*;
import static nl.ritense.demo.domain.FractieTestSamples.*;
import static nl.ritense.demo.domain.LocatieTestSamples.*;
import static nl.ritense.demo.domain.OphaalmomentTestSamples.*;
import static nl.ritense.demo.domain.VulgraadmetingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContainerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Container.class);
        Container container1 = getContainerSample1();
        Container container2 = new Container();
        assertThat(container1).isNotEqualTo(container2);

        container2.setId(container1.getId());
        assertThat(container1).isEqualTo(container2);

        container2 = getContainerSample2();
        assertThat(container1).isNotEqualTo(container2);
    }

    @Test
    void heeftVulgraadmetingTest() throws Exception {
        Container container = getContainerRandomSampleGenerator();
        Vulgraadmeting vulgraadmetingBack = getVulgraadmetingRandomSampleGenerator();

        container.addHeeftVulgraadmeting(vulgraadmetingBack);
        assertThat(container.getHeeftVulgraadmetings()).containsOnly(vulgraadmetingBack);
        assertThat(vulgraadmetingBack.getHeeftContainer()).isEqualTo(container);

        container.removeHeeftVulgraadmeting(vulgraadmetingBack);
        assertThat(container.getHeeftVulgraadmetings()).doesNotContain(vulgraadmetingBack);
        assertThat(vulgraadmetingBack.getHeeftContainer()).isNull();

        container.heeftVulgraadmetings(new HashSet<>(Set.of(vulgraadmetingBack)));
        assertThat(container.getHeeftVulgraadmetings()).containsOnly(vulgraadmetingBack);
        assertThat(vulgraadmetingBack.getHeeftContainer()).isEqualTo(container);

        container.setHeeftVulgraadmetings(new HashSet<>());
        assertThat(container.getHeeftVulgraadmetings()).doesNotContain(vulgraadmetingBack);
        assertThat(vulgraadmetingBack.getHeeftContainer()).isNull();
    }

    @Test
    void geschiktvoorFractieTest() throws Exception {
        Container container = getContainerRandomSampleGenerator();
        Fractie fractieBack = getFractieRandomSampleGenerator();

        container.setGeschiktvoorFractie(fractieBack);
        assertThat(container.getGeschiktvoorFractie()).isEqualTo(fractieBack);

        container.geschiktvoorFractie(null);
        assertThat(container.getGeschiktvoorFractie()).isNull();
    }

    @Test
    void soortContainertypeTest() throws Exception {
        Container container = getContainerRandomSampleGenerator();
        Containertype containertypeBack = getContainertypeRandomSampleGenerator();

        container.setSoortContainertype(containertypeBack);
        assertThat(container.getSoortContainertype()).isEqualTo(containertypeBack);

        container.soortContainertype(null);
        assertThat(container.getSoortContainertype()).isNull();
    }

    @Test
    void heeftLocatieTest() throws Exception {
        Container container = getContainerRandomSampleGenerator();
        Locatie locatieBack = getLocatieRandomSampleGenerator();

        container.setHeeftLocatie(locatieBack);
        assertThat(container.getHeeftLocatie()).isEqualTo(locatieBack);

        container.heeftLocatie(null);
        assertThat(container.getHeeftLocatie()).isNull();
    }

    @Test
    void gelostOphaalmomentTest() throws Exception {
        Container container = getContainerRandomSampleGenerator();
        Ophaalmoment ophaalmomentBack = getOphaalmomentRandomSampleGenerator();

        container.addGelostOphaalmoment(ophaalmomentBack);
        assertThat(container.getGelostOphaalmoments()).containsOnly(ophaalmomentBack);
        assertThat(ophaalmomentBack.getGelostContainer()).isEqualTo(container);

        container.removeGelostOphaalmoment(ophaalmomentBack);
        assertThat(container.getGelostOphaalmoments()).doesNotContain(ophaalmomentBack);
        assertThat(ophaalmomentBack.getGelostContainer()).isNull();

        container.gelostOphaalmoments(new HashSet<>(Set.of(ophaalmomentBack)));
        assertThat(container.getGelostOphaalmoments()).containsOnly(ophaalmomentBack);
        assertThat(ophaalmomentBack.getGelostContainer()).isEqualTo(container);

        container.setGelostOphaalmoments(new HashSet<>());
        assertThat(container.getGelostOphaalmoments()).doesNotContain(ophaalmomentBack);
        assertThat(ophaalmomentBack.getGelostContainer()).isNull();
    }
}
