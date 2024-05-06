package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClientTestSamples.*;
import static nl.ritense.demo.domain.DoelgroepTestSamples.*;
import static nl.ritense.demo.domain.DoelgroepTestSamples.*;
import static nl.ritense.demo.domain.MuseumrelatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DoelgroepTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Doelgroep.class);
        Doelgroep doelgroep1 = getDoelgroepSample1();
        Doelgroep doelgroep2 = new Doelgroep();
        assertThat(doelgroep1).isNotEqualTo(doelgroep2);

        doelgroep2.setId(doelgroep1.getId());
        assertThat(doelgroep1).isEqualTo(doelgroep2);

        doelgroep2 = getDoelgroepSample2();
        assertThat(doelgroep1).isNotEqualTo(doelgroep2);
    }

    @Test
    void bestaatuitDoelgroepTest() throws Exception {
        Doelgroep doelgroep = getDoelgroepRandomSampleGenerator();
        Doelgroep doelgroepBack = getDoelgroepRandomSampleGenerator();

        doelgroep.addBestaatuitDoelgroep(doelgroepBack);
        assertThat(doelgroep.getBestaatuitDoelgroeps()).containsOnly(doelgroepBack);
        assertThat(doelgroepBack.getBestaatuitDoelgroep2()).isEqualTo(doelgroep);

        doelgroep.removeBestaatuitDoelgroep(doelgroepBack);
        assertThat(doelgroep.getBestaatuitDoelgroeps()).doesNotContain(doelgroepBack);
        assertThat(doelgroepBack.getBestaatuitDoelgroep2()).isNull();

        doelgroep.bestaatuitDoelgroeps(new HashSet<>(Set.of(doelgroepBack)));
        assertThat(doelgroep.getBestaatuitDoelgroeps()).containsOnly(doelgroepBack);
        assertThat(doelgroepBack.getBestaatuitDoelgroep2()).isEqualTo(doelgroep);

        doelgroep.setBestaatuitDoelgroeps(new HashSet<>());
        assertThat(doelgroep.getBestaatuitDoelgroeps()).doesNotContain(doelgroepBack);
        assertThat(doelgroepBack.getBestaatuitDoelgroep2()).isNull();
    }

    @Test
    void bestaatuitDoelgroep2Test() throws Exception {
        Doelgroep doelgroep = getDoelgroepRandomSampleGenerator();
        Doelgroep doelgroepBack = getDoelgroepRandomSampleGenerator();

        doelgroep.setBestaatuitDoelgroep2(doelgroepBack);
        assertThat(doelgroep.getBestaatuitDoelgroep2()).isEqualTo(doelgroepBack);

        doelgroep.bestaatuitDoelgroep2(null);
        assertThat(doelgroep.getBestaatuitDoelgroep2()).isNull();
    }

    @Test
    void valtbinnendoelgroepClientTest() throws Exception {
        Doelgroep doelgroep = getDoelgroepRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        doelgroep.addValtbinnendoelgroepClient(clientBack);
        assertThat(doelgroep.getValtbinnendoelgroepClients()).containsOnly(clientBack);
        assertThat(clientBack.getValtbinnendoelgroepDoelgroep()).isEqualTo(doelgroep);

        doelgroep.removeValtbinnendoelgroepClient(clientBack);
        assertThat(doelgroep.getValtbinnendoelgroepClients()).doesNotContain(clientBack);
        assertThat(clientBack.getValtbinnendoelgroepDoelgroep()).isNull();

        doelgroep.valtbinnendoelgroepClients(new HashSet<>(Set.of(clientBack)));
        assertThat(doelgroep.getValtbinnendoelgroepClients()).containsOnly(clientBack);
        assertThat(clientBack.getValtbinnendoelgroepDoelgroep()).isEqualTo(doelgroep);

        doelgroep.setValtbinnendoelgroepClients(new HashSet<>());
        assertThat(doelgroep.getValtbinnendoelgroepClients()).doesNotContain(clientBack);
        assertThat(clientBack.getValtbinnendoelgroepDoelgroep()).isNull();
    }

    @Test
    void valtbinnenMuseumrelatieTest() throws Exception {
        Doelgroep doelgroep = getDoelgroepRandomSampleGenerator();
        Museumrelatie museumrelatieBack = getMuseumrelatieRandomSampleGenerator();

        doelgroep.addValtbinnenMuseumrelatie(museumrelatieBack);
        assertThat(doelgroep.getValtbinnenMuseumrelaties()).containsOnly(museumrelatieBack);
        assertThat(museumrelatieBack.getValtbinnenDoelgroeps()).containsOnly(doelgroep);

        doelgroep.removeValtbinnenMuseumrelatie(museumrelatieBack);
        assertThat(doelgroep.getValtbinnenMuseumrelaties()).doesNotContain(museumrelatieBack);
        assertThat(museumrelatieBack.getValtbinnenDoelgroeps()).doesNotContain(doelgroep);

        doelgroep.valtbinnenMuseumrelaties(new HashSet<>(Set.of(museumrelatieBack)));
        assertThat(doelgroep.getValtbinnenMuseumrelaties()).containsOnly(museumrelatieBack);
        assertThat(museumrelatieBack.getValtbinnenDoelgroeps()).containsOnly(doelgroep);

        doelgroep.setValtbinnenMuseumrelaties(new HashSet<>());
        assertThat(doelgroep.getValtbinnenMuseumrelaties()).doesNotContain(museumrelatieBack);
        assertThat(museumrelatieBack.getValtbinnenDoelgroeps()).doesNotContain(doelgroep);
    }
}
