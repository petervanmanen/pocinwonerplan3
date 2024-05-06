package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ContainerTestSamples.*;
import static nl.ritense.demo.domain.LocatieTestSamples.*;
import static nl.ritense.demo.domain.OphaalmomentTestSamples.*;
import static nl.ritense.demo.domain.RitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OphaalmomentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ophaalmoment.class);
        Ophaalmoment ophaalmoment1 = getOphaalmomentSample1();
        Ophaalmoment ophaalmoment2 = new Ophaalmoment();
        assertThat(ophaalmoment1).isNotEqualTo(ophaalmoment2);

        ophaalmoment2.setId(ophaalmoment1.getId());
        assertThat(ophaalmoment1).isEqualTo(ophaalmoment2);

        ophaalmoment2 = getOphaalmomentSample2();
        assertThat(ophaalmoment1).isNotEqualTo(ophaalmoment2);
    }

    @Test
    void gelostContainerTest() throws Exception {
        Ophaalmoment ophaalmoment = getOphaalmomentRandomSampleGenerator();
        Container containerBack = getContainerRandomSampleGenerator();

        ophaalmoment.setGelostContainer(containerBack);
        assertThat(ophaalmoment.getGelostContainer()).isEqualTo(containerBack);

        ophaalmoment.gelostContainer(null);
        assertThat(ophaalmoment.getGelostContainer()).isNull();
    }

    @Test
    void gestoptopLocatieTest() throws Exception {
        Ophaalmoment ophaalmoment = getOphaalmomentRandomSampleGenerator();
        Locatie locatieBack = getLocatieRandomSampleGenerator();

        ophaalmoment.setGestoptopLocatie(locatieBack);
        assertThat(ophaalmoment.getGestoptopLocatie()).isEqualTo(locatieBack);

        ophaalmoment.gestoptopLocatie(null);
        assertThat(ophaalmoment.getGestoptopLocatie()).isNull();
    }

    @Test
    void heeftRitTest() throws Exception {
        Ophaalmoment ophaalmoment = getOphaalmomentRandomSampleGenerator();
        Rit ritBack = getRitRandomSampleGenerator();

        ophaalmoment.setHeeftRit(ritBack);
        assertThat(ophaalmoment.getHeeftRit()).isEqualTo(ritBack);

        ophaalmoment.heeftRit(null);
        assertThat(ophaalmoment.getHeeftRit()).isNull();
    }
}
