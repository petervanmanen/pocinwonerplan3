package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeschikkingTestSamples.*;
import static nl.ritense.demo.domain.ClientTestSamples.*;
import static nl.ritense.demo.domain.ClientbegeleiderTestSamples.*;
import static nl.ritense.demo.domain.InkomensvoorzieningTestSamples.*;
import static nl.ritense.demo.domain.ParticipatiedossierTestSamples.*;
import static nl.ritense.demo.domain.TeamTestSamples.*;
import static nl.ritense.demo.domain.TrajectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClientbegeleiderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clientbegeleider.class);
        Clientbegeleider clientbegeleider1 = getClientbegeleiderSample1();
        Clientbegeleider clientbegeleider2 = new Clientbegeleider();
        assertThat(clientbegeleider1).isNotEqualTo(clientbegeleider2);

        clientbegeleider2.setId(clientbegeleider1.getId());
        assertThat(clientbegeleider1).isEqualTo(clientbegeleider2);

        clientbegeleider2 = getClientbegeleiderSample2();
        assertThat(clientbegeleider1).isNotEqualTo(clientbegeleider2);
    }

    @Test
    void geeftafBeschikkingTest() throws Exception {
        Clientbegeleider clientbegeleider = getClientbegeleiderRandomSampleGenerator();
        Beschikking beschikkingBack = getBeschikkingRandomSampleGenerator();

        clientbegeleider.addGeeftafBeschikking(beschikkingBack);
        assertThat(clientbegeleider.getGeeftafBeschikkings()).containsOnly(beschikkingBack);
        assertThat(beschikkingBack.getGeeftafClientbegeleider()).isEqualTo(clientbegeleider);

        clientbegeleider.removeGeeftafBeschikking(beschikkingBack);
        assertThat(clientbegeleider.getGeeftafBeschikkings()).doesNotContain(beschikkingBack);
        assertThat(beschikkingBack.getGeeftafClientbegeleider()).isNull();

        clientbegeleider.geeftafBeschikkings(new HashSet<>(Set.of(beschikkingBack)));
        assertThat(clientbegeleider.getGeeftafBeschikkings()).containsOnly(beschikkingBack);
        assertThat(beschikkingBack.getGeeftafClientbegeleider()).isEqualTo(clientbegeleider);

        clientbegeleider.setGeeftafBeschikkings(new HashSet<>());
        assertThat(clientbegeleider.getGeeftafBeschikkings()).doesNotContain(beschikkingBack);
        assertThat(beschikkingBack.getGeeftafClientbegeleider()).isNull();
    }

    @Test
    void emptyInkomensvoorzieningTest() throws Exception {
        Clientbegeleider clientbegeleider = getClientbegeleiderRandomSampleGenerator();
        Inkomensvoorziening inkomensvoorzieningBack = getInkomensvoorzieningRandomSampleGenerator();

        clientbegeleider.addEmptyInkomensvoorziening(inkomensvoorzieningBack);
        assertThat(clientbegeleider.getEmptyInkomensvoorzienings()).containsOnly(inkomensvoorzieningBack);
        assertThat(inkomensvoorzieningBack.getEmptyClientbegeleider()).isEqualTo(clientbegeleider);

        clientbegeleider.removeEmptyInkomensvoorziening(inkomensvoorzieningBack);
        assertThat(clientbegeleider.getEmptyInkomensvoorzienings()).doesNotContain(inkomensvoorzieningBack);
        assertThat(inkomensvoorzieningBack.getEmptyClientbegeleider()).isNull();

        clientbegeleider.emptyInkomensvoorzienings(new HashSet<>(Set.of(inkomensvoorzieningBack)));
        assertThat(clientbegeleider.getEmptyInkomensvoorzienings()).containsOnly(inkomensvoorzieningBack);
        assertThat(inkomensvoorzieningBack.getEmptyClientbegeleider()).isEqualTo(clientbegeleider);

        clientbegeleider.setEmptyInkomensvoorzienings(new HashSet<>());
        assertThat(clientbegeleider.getEmptyInkomensvoorzienings()).doesNotContain(inkomensvoorzieningBack);
        assertThat(inkomensvoorzieningBack.getEmptyClientbegeleider()).isNull();
    }

    @Test
    void maaktonderdeeluitvanTeamTest() throws Exception {
        Clientbegeleider clientbegeleider = getClientbegeleiderRandomSampleGenerator();
        Team teamBack = getTeamRandomSampleGenerator();

        clientbegeleider.setMaaktonderdeeluitvanTeam(teamBack);
        assertThat(clientbegeleider.getMaaktonderdeeluitvanTeam()).isEqualTo(teamBack);

        clientbegeleider.maaktonderdeeluitvanTeam(null);
        assertThat(clientbegeleider.getMaaktonderdeeluitvanTeam()).isNull();
    }

    @Test
    void begeleidtTrajectTest() throws Exception {
        Clientbegeleider clientbegeleider = getClientbegeleiderRandomSampleGenerator();
        Traject trajectBack = getTrajectRandomSampleGenerator();

        clientbegeleider.setBegeleidtTraject(trajectBack);
        assertThat(clientbegeleider.getBegeleidtTraject()).isEqualTo(trajectBack);

        clientbegeleider.begeleidtTraject(null);
        assertThat(clientbegeleider.getBegeleidtTraject()).isNull();
    }

    @Test
    void ondersteuntclientClientTest() throws Exception {
        Clientbegeleider clientbegeleider = getClientbegeleiderRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        clientbegeleider.addOndersteuntclientClient(clientBack);
        assertThat(clientbegeleider.getOndersteuntclientClients()).containsOnly(clientBack);

        clientbegeleider.removeOndersteuntclientClient(clientBack);
        assertThat(clientbegeleider.getOndersteuntclientClients()).doesNotContain(clientBack);

        clientbegeleider.ondersteuntclientClients(new HashSet<>(Set.of(clientBack)));
        assertThat(clientbegeleider.getOndersteuntclientClients()).containsOnly(clientBack);

        clientbegeleider.setOndersteuntclientClients(new HashSet<>());
        assertThat(clientbegeleider.getOndersteuntclientClients()).doesNotContain(clientBack);
    }

    @Test
    void emptyParticipatiedossierTest() throws Exception {
        Clientbegeleider clientbegeleider = getClientbegeleiderRandomSampleGenerator();
        Participatiedossier participatiedossierBack = getParticipatiedossierRandomSampleGenerator();

        clientbegeleider.setEmptyParticipatiedossier(participatiedossierBack);
        assertThat(clientbegeleider.getEmptyParticipatiedossier()).isEqualTo(participatiedossierBack);
        assertThat(participatiedossierBack.getEmptyClientbegeleider()).isEqualTo(clientbegeleider);

        clientbegeleider.emptyParticipatiedossier(null);
        assertThat(clientbegeleider.getEmptyParticipatiedossier()).isNull();
        assertThat(participatiedossierBack.getEmptyClientbegeleider()).isNull();
    }
}
