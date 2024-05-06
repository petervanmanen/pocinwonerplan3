package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClientTestSamples.*;
import static nl.ritense.demo.domain.HuishoudenTestSamples.*;
import static nl.ritense.demo.domain.RelatieTestSamples.*;
import static nl.ritense.demo.domain.RelatiesoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RelatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Relatie.class);
        Relatie relatie1 = getRelatieSample1();
        Relatie relatie2 = new Relatie();
        assertThat(relatie1).isNotEqualTo(relatie2);

        relatie2.setId(relatie1.getId());
        assertThat(relatie1).isEqualTo(relatie2);

        relatie2 = getRelatieSample2();
        assertThat(relatie1).isNotEqualTo(relatie2);
    }

    @Test
    void issoortRelatiesoortTest() throws Exception {
        Relatie relatie = getRelatieRandomSampleGenerator();
        Relatiesoort relatiesoortBack = getRelatiesoortRandomSampleGenerator();

        relatie.setIssoortRelatiesoort(relatiesoortBack);
        assertThat(relatie.getIssoortRelatiesoort()).isEqualTo(relatiesoortBack);

        relatie.issoortRelatiesoort(null);
        assertThat(relatie.getIssoortRelatiesoort()).isNull();
    }

    @Test
    void maaktonderdeelvanHuishoudenTest() throws Exception {
        Relatie relatie = getRelatieRandomSampleGenerator();
        Huishouden huishoudenBack = getHuishoudenRandomSampleGenerator();

        relatie.addMaaktonderdeelvanHuishouden(huishoudenBack);
        assertThat(relatie.getMaaktonderdeelvanHuishoudens()).containsOnly(huishoudenBack);

        relatie.removeMaaktonderdeelvanHuishouden(huishoudenBack);
        assertThat(relatie.getMaaktonderdeelvanHuishoudens()).doesNotContain(huishoudenBack);

        relatie.maaktonderdeelvanHuishoudens(new HashSet<>(Set.of(huishoudenBack)));
        assertThat(relatie.getMaaktonderdeelvanHuishoudens()).containsOnly(huishoudenBack);

        relatie.setMaaktonderdeelvanHuishoudens(new HashSet<>());
        assertThat(relatie.getMaaktonderdeelvanHuishoudens()).doesNotContain(huishoudenBack);
    }

    @Test
    void heeftrelatieClientTest() throws Exception {
        Relatie relatie = getRelatieRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        relatie.addHeeftrelatieClient(clientBack);
        assertThat(relatie.getHeeftrelatieClients()).containsOnly(clientBack);
        assertThat(clientBack.getHeeftrelatieRelaties()).containsOnly(relatie);

        relatie.removeHeeftrelatieClient(clientBack);
        assertThat(relatie.getHeeftrelatieClients()).doesNotContain(clientBack);
        assertThat(clientBack.getHeeftrelatieRelaties()).doesNotContain(relatie);

        relatie.heeftrelatieClients(new HashSet<>(Set.of(clientBack)));
        assertThat(relatie.getHeeftrelatieClients()).containsOnly(clientBack);
        assertThat(clientBack.getHeeftrelatieRelaties()).containsOnly(relatie);

        relatie.setHeeftrelatieClients(new HashSet<>());
        assertThat(relatie.getHeeftrelatieClients()).doesNotContain(clientBack);
        assertThat(clientBack.getHeeftrelatieRelaties()).doesNotContain(relatie);
    }
}
