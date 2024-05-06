package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClientTestSamples.*;
import static nl.ritense.demo.domain.HuishoudenTestSamples.*;
import static nl.ritense.demo.domain.RelatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HuishoudenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Huishouden.class);
        Huishouden huishouden1 = getHuishoudenSample1();
        Huishouden huishouden2 = new Huishouden();
        assertThat(huishouden1).isNotEqualTo(huishouden2);

        huishouden2.setId(huishouden1.getId());
        assertThat(huishouden1).isEqualTo(huishouden2);

        huishouden2 = getHuishoudenSample2();
        assertThat(huishouden1).isNotEqualTo(huishouden2);
    }

    @Test
    void maaktonderdeeluitvanClientTest() throws Exception {
        Huishouden huishouden = getHuishoudenRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        huishouden.addMaaktonderdeeluitvanClient(clientBack);
        assertThat(huishouden.getMaaktonderdeeluitvanClients()).containsOnly(clientBack);
        assertThat(clientBack.getMaaktonderdeeluitvanHuishoudens()).containsOnly(huishouden);

        huishouden.removeMaaktonderdeeluitvanClient(clientBack);
        assertThat(huishouden.getMaaktonderdeeluitvanClients()).doesNotContain(clientBack);
        assertThat(clientBack.getMaaktonderdeeluitvanHuishoudens()).doesNotContain(huishouden);

        huishouden.maaktonderdeeluitvanClients(new HashSet<>(Set.of(clientBack)));
        assertThat(huishouden.getMaaktonderdeeluitvanClients()).containsOnly(clientBack);
        assertThat(clientBack.getMaaktonderdeeluitvanHuishoudens()).containsOnly(huishouden);

        huishouden.setMaaktonderdeeluitvanClients(new HashSet<>());
        assertThat(huishouden.getMaaktonderdeeluitvanClients()).doesNotContain(clientBack);
        assertThat(clientBack.getMaaktonderdeeluitvanHuishoudens()).doesNotContain(huishouden);
    }

    @Test
    void maaktonderdeelvanRelatieTest() throws Exception {
        Huishouden huishouden = getHuishoudenRandomSampleGenerator();
        Relatie relatieBack = getRelatieRandomSampleGenerator();

        huishouden.addMaaktonderdeelvanRelatie(relatieBack);
        assertThat(huishouden.getMaaktonderdeelvanRelaties()).containsOnly(relatieBack);
        assertThat(relatieBack.getMaaktonderdeelvanHuishoudens()).containsOnly(huishouden);

        huishouden.removeMaaktonderdeelvanRelatie(relatieBack);
        assertThat(huishouden.getMaaktonderdeelvanRelaties()).doesNotContain(relatieBack);
        assertThat(relatieBack.getMaaktonderdeelvanHuishoudens()).doesNotContain(huishouden);

        huishouden.maaktonderdeelvanRelaties(new HashSet<>(Set.of(relatieBack)));
        assertThat(huishouden.getMaaktonderdeelvanRelaties()).containsOnly(relatieBack);
        assertThat(relatieBack.getMaaktonderdeelvanHuishoudens()).containsOnly(huishouden);

        huishouden.setMaaktonderdeelvanRelaties(new HashSet<>());
        assertThat(huishouden.getMaaktonderdeelvanRelaties()).doesNotContain(relatieBack);
        assertThat(relatieBack.getMaaktonderdeelvanHuishoudens()).doesNotContain(huishouden);
    }
}
