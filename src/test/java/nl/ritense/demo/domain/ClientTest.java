package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeschikkingTestSamples.*;
import static nl.ritense.demo.domain.ClientTestSamples.*;
import static nl.ritense.demo.domain.ClientbegeleiderTestSamples.*;
import static nl.ritense.demo.domain.DeclaratieregelTestSamples.*;
import static nl.ritense.demo.domain.DoelgroepTestSamples.*;
import static nl.ritense.demo.domain.FraudegegevensTestSamples.*;
import static nl.ritense.demo.domain.HuishoudenTestSamples.*;
import static nl.ritense.demo.domain.InkomensvoorzieningTestSamples.*;
import static nl.ritense.demo.domain.LeveringTestSamples.*;
import static nl.ritense.demo.domain.ParticipatiedossierTestSamples.*;
import static nl.ritense.demo.domain.RegelingTestSamples.*;
import static nl.ritense.demo.domain.RelatieTestSamples.*;
import static nl.ritense.demo.domain.ScoreTestSamples.*;
import static nl.ritense.demo.domain.TaalniveauTestSamples.*;
import static nl.ritense.demo.domain.TegenprestatieTestSamples.*;
import static nl.ritense.demo.domain.TrajectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClientTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Client.class);
        Client client1 = getClientSample1();
        Client client2 = new Client();
        assertThat(client1).isNotEqualTo(client2);

        client2.setId(client1.getId());
        assertThat(client1).isEqualTo(client2);

        client2 = getClientSample2();
        assertThat(client1).isNotEqualTo(client2);
    }

    @Test
    void heeftParticipatiedossierTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Participatiedossier participatiedossierBack = getParticipatiedossierRandomSampleGenerator();

        client.setHeeftParticipatiedossier(participatiedossierBack);
        assertThat(client.getHeeftParticipatiedossier()).isEqualTo(participatiedossierBack);

        client.heeftParticipatiedossier(null);
        assertThat(client.getHeeftParticipatiedossier()).isNull();
    }

    @Test
    void heeftvoorzieningInkomensvoorzieningTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Inkomensvoorziening inkomensvoorzieningBack = getInkomensvoorzieningRandomSampleGenerator();

        client.setHeeftvoorzieningInkomensvoorziening(inkomensvoorzieningBack);
        assertThat(client.getHeeftvoorzieningInkomensvoorziening()).isEqualTo(inkomensvoorzieningBack);

        client.heeftvoorzieningInkomensvoorziening(null);
        assertThat(client.getHeeftvoorzieningInkomensvoorziening()).isNull();
    }

    @Test
    void heeftScoreTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Score scoreBack = getScoreRandomSampleGenerator();

        client.addHeeftScore(scoreBack);
        assertThat(client.getHeeftScores()).containsOnly(scoreBack);
        assertThat(scoreBack.getHeeftClient()).isEqualTo(client);

        client.removeHeeftScore(scoreBack);
        assertThat(client.getHeeftScores()).doesNotContain(scoreBack);
        assertThat(scoreBack.getHeeftClient()).isNull();

        client.heeftScores(new HashSet<>(Set.of(scoreBack)));
        assertThat(client.getHeeftScores()).containsOnly(scoreBack);
        assertThat(scoreBack.getHeeftClient()).isEqualTo(client);

        client.setHeeftScores(new HashSet<>());
        assertThat(client.getHeeftScores()).doesNotContain(scoreBack);
        assertThat(scoreBack.getHeeftClient()).isNull();
    }

    @Test
    void leverttegenprestatieTegenprestatieTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Tegenprestatie tegenprestatieBack = getTegenprestatieRandomSampleGenerator();

        client.addLeverttegenprestatieTegenprestatie(tegenprestatieBack);
        assertThat(client.getLeverttegenprestatieTegenprestaties()).containsOnly(tegenprestatieBack);
        assertThat(tegenprestatieBack.getLeverttegenprestatieClient()).isEqualTo(client);

        client.removeLeverttegenprestatieTegenprestatie(tegenprestatieBack);
        assertThat(client.getLeverttegenprestatieTegenprestaties()).doesNotContain(tegenprestatieBack);
        assertThat(tegenprestatieBack.getLeverttegenprestatieClient()).isNull();

        client.leverttegenprestatieTegenprestaties(new HashSet<>(Set.of(tegenprestatieBack)));
        assertThat(client.getLeverttegenprestatieTegenprestaties()).containsOnly(tegenprestatieBack);
        assertThat(tegenprestatieBack.getLeverttegenprestatieClient()).isEqualTo(client);

        client.setLeverttegenprestatieTegenprestaties(new HashSet<>());
        assertThat(client.getLeverttegenprestatieTegenprestaties()).doesNotContain(tegenprestatieBack);
        assertThat(tegenprestatieBack.getLeverttegenprestatieClient()).isNull();
    }

    @Test
    void heeftparticipatietrajectTrajectTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Traject trajectBack = getTrajectRandomSampleGenerator();

        client.addHeeftparticipatietrajectTraject(trajectBack);
        assertThat(client.getHeeftparticipatietrajectTrajects()).containsOnly(trajectBack);
        assertThat(trajectBack.getHeeftparticipatietrajectClient()).isEqualTo(client);

        client.removeHeeftparticipatietrajectTraject(trajectBack);
        assertThat(client.getHeeftparticipatietrajectTrajects()).doesNotContain(trajectBack);
        assertThat(trajectBack.getHeeftparticipatietrajectClient()).isNull();

        client.heeftparticipatietrajectTrajects(new HashSet<>(Set.of(trajectBack)));
        assertThat(client.getHeeftparticipatietrajectTrajects()).containsOnly(trajectBack);
        assertThat(trajectBack.getHeeftparticipatietrajectClient()).isEqualTo(client);

        client.setHeeftparticipatietrajectTrajects(new HashSet<>());
        assertThat(client.getHeeftparticipatietrajectTrajects()).doesNotContain(trajectBack);
        assertThat(trajectBack.getHeeftparticipatietrajectClient()).isNull();
    }

    @Test
    void heeftfraudegegevensFraudegegevensTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Fraudegegevens fraudegegevensBack = getFraudegegevensRandomSampleGenerator();

        client.addHeeftfraudegegevensFraudegegevens(fraudegegevensBack);
        assertThat(client.getHeeftfraudegegevensFraudegegevens()).containsOnly(fraudegegevensBack);
        assertThat(fraudegegevensBack.getHeeftfraudegegevensClient()).isEqualTo(client);

        client.removeHeeftfraudegegevensFraudegegevens(fraudegegevensBack);
        assertThat(client.getHeeftfraudegegevensFraudegegevens()).doesNotContain(fraudegegevensBack);
        assertThat(fraudegegevensBack.getHeeftfraudegegevensClient()).isNull();

        client.heeftfraudegegevensFraudegegevens(new HashSet<>(Set.of(fraudegegevensBack)));
        assertThat(client.getHeeftfraudegegevensFraudegegevens()).containsOnly(fraudegegevensBack);
        assertThat(fraudegegevensBack.getHeeftfraudegegevensClient()).isEqualTo(client);

        client.setHeeftfraudegegevensFraudegegevens(new HashSet<>());
        assertThat(client.getHeeftfraudegegevensFraudegegevens()).doesNotContain(fraudegegevensBack);
        assertThat(fraudegegevensBack.getHeeftfraudegegevensClient()).isNull();
    }

    @Test
    void heeftregelingRegelingTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Regeling regelingBack = getRegelingRandomSampleGenerator();

        client.addHeeftregelingRegeling(regelingBack);
        assertThat(client.getHeeftregelingRegelings()).containsOnly(regelingBack);
        assertThat(regelingBack.getHeeftregelingClient()).isEqualTo(client);

        client.removeHeeftregelingRegeling(regelingBack);
        assertThat(client.getHeeftregelingRegelings()).doesNotContain(regelingBack);
        assertThat(regelingBack.getHeeftregelingClient()).isNull();

        client.heeftregelingRegelings(new HashSet<>(Set.of(regelingBack)));
        assertThat(client.getHeeftregelingRegelings()).containsOnly(regelingBack);
        assertThat(regelingBack.getHeeftregelingClient()).isEqualTo(client);

        client.setHeeftregelingRegelings(new HashSet<>());
        assertThat(client.getHeeftregelingRegelings()).doesNotContain(regelingBack);
        assertThat(regelingBack.getHeeftregelingClient()).isNull();
    }

    @Test
    void heefttrajectTrajectTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Traject trajectBack = getTrajectRandomSampleGenerator();

        client.addHeefttrajectTraject(trajectBack);
        assertThat(client.getHeefttrajectTrajects()).containsOnly(trajectBack);
        assertThat(trajectBack.getHeefttrajectClient()).isEqualTo(client);

        client.removeHeefttrajectTraject(trajectBack);
        assertThat(client.getHeefttrajectTrajects()).doesNotContain(trajectBack);
        assertThat(trajectBack.getHeefttrajectClient()).isNull();

        client.heefttrajectTrajects(new HashSet<>(Set.of(trajectBack)));
        assertThat(client.getHeefttrajectTrajects()).containsOnly(trajectBack);
        assertThat(trajectBack.getHeefttrajectClient()).isEqualTo(client);

        client.setHeefttrajectTrajects(new HashSet<>());
        assertThat(client.getHeefttrajectTrajects()).doesNotContain(trajectBack);
        assertThat(trajectBack.getHeefttrajectClient()).isNull();
    }

    @Test
    void valtbinnendoelgroepDoelgroepTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Doelgroep doelgroepBack = getDoelgroepRandomSampleGenerator();

        client.setValtbinnendoelgroepDoelgroep(doelgroepBack);
        assertThat(client.getValtbinnendoelgroepDoelgroep()).isEqualTo(doelgroepBack);

        client.valtbinnendoelgroepDoelgroep(null);
        assertThat(client.getValtbinnendoelgroepDoelgroep()).isNull();
    }

    @Test
    void heeftrelatieRelatieTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Relatie relatieBack = getRelatieRandomSampleGenerator();

        client.addHeeftrelatieRelatie(relatieBack);
        assertThat(client.getHeeftrelatieRelaties()).containsOnly(relatieBack);

        client.removeHeeftrelatieRelatie(relatieBack);
        assertThat(client.getHeeftrelatieRelaties()).doesNotContain(relatieBack);

        client.heeftrelatieRelaties(new HashSet<>(Set.of(relatieBack)));
        assertThat(client.getHeeftrelatieRelaties()).containsOnly(relatieBack);

        client.setHeeftrelatieRelaties(new HashSet<>());
        assertThat(client.getHeeftrelatieRelaties()).doesNotContain(relatieBack);
    }

    @Test
    void voorzieningbijstandspartijInkomensvoorzieningTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Inkomensvoorziening inkomensvoorzieningBack = getInkomensvoorzieningRandomSampleGenerator();

        client.addVoorzieningbijstandspartijInkomensvoorziening(inkomensvoorzieningBack);
        assertThat(client.getVoorzieningbijstandspartijInkomensvoorzienings()).containsOnly(inkomensvoorzieningBack);

        client.removeVoorzieningbijstandspartijInkomensvoorziening(inkomensvoorzieningBack);
        assertThat(client.getVoorzieningbijstandspartijInkomensvoorzienings()).doesNotContain(inkomensvoorzieningBack);

        client.voorzieningbijstandspartijInkomensvoorzienings(new HashSet<>(Set.of(inkomensvoorzieningBack)));
        assertThat(client.getVoorzieningbijstandspartijInkomensvoorzienings()).containsOnly(inkomensvoorzieningBack);

        client.setVoorzieningbijstandspartijInkomensvoorzienings(new HashSet<>());
        assertThat(client.getVoorzieningbijstandspartijInkomensvoorzienings()).doesNotContain(inkomensvoorzieningBack);
    }

    @Test
    void maaktonderdeeluitvanHuishoudenTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Huishouden huishoudenBack = getHuishoudenRandomSampleGenerator();

        client.addMaaktonderdeeluitvanHuishouden(huishoudenBack);
        assertThat(client.getMaaktonderdeeluitvanHuishoudens()).containsOnly(huishoudenBack);

        client.removeMaaktonderdeeluitvanHuishouden(huishoudenBack);
        assertThat(client.getMaaktonderdeeluitvanHuishoudens()).doesNotContain(huishoudenBack);

        client.maaktonderdeeluitvanHuishoudens(new HashSet<>(Set.of(huishoudenBack)));
        assertThat(client.getMaaktonderdeeluitvanHuishoudens()).containsOnly(huishoudenBack);

        client.setMaaktonderdeeluitvanHuishoudens(new HashSet<>());
        assertThat(client.getMaaktonderdeeluitvanHuishoudens()).doesNotContain(huishoudenBack);
    }

    @Test
    void heefttaalniveauTaalniveauTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Taalniveau taalniveauBack = getTaalniveauRandomSampleGenerator();

        client.addHeefttaalniveauTaalniveau(taalniveauBack);
        assertThat(client.getHeefttaalniveauTaalniveaus()).containsOnly(taalniveauBack);

        client.removeHeefttaalniveauTaalniveau(taalniveauBack);
        assertThat(client.getHeefttaalniveauTaalniveaus()).doesNotContain(taalniveauBack);

        client.heefttaalniveauTaalniveaus(new HashSet<>(Set.of(taalniveauBack)));
        assertThat(client.getHeefttaalniveauTaalniveaus()).containsOnly(taalniveauBack);

        client.setHeefttaalniveauTaalniveaus(new HashSet<>());
        assertThat(client.getHeefttaalniveauTaalniveaus()).doesNotContain(taalniveauBack);
    }

    @Test
    void emptyBeschikkingTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Beschikking beschikkingBack = getBeschikkingRandomSampleGenerator();

        client.addEmptyBeschikking(beschikkingBack);
        assertThat(client.getEmptyBeschikkings()).containsOnly(beschikkingBack);
        assertThat(beschikkingBack.getEmptyClient()).isEqualTo(client);

        client.removeEmptyBeschikking(beschikkingBack);
        assertThat(client.getEmptyBeschikkings()).doesNotContain(beschikkingBack);
        assertThat(beschikkingBack.getEmptyClient()).isNull();

        client.emptyBeschikkings(new HashSet<>(Set.of(beschikkingBack)));
        assertThat(client.getEmptyBeschikkings()).containsOnly(beschikkingBack);
        assertThat(beschikkingBack.getEmptyClient()).isEqualTo(client);

        client.setEmptyBeschikkings(new HashSet<>());
        assertThat(client.getEmptyBeschikkings()).doesNotContain(beschikkingBack);
        assertThat(beschikkingBack.getEmptyClient()).isNull();
    }

    @Test
    void prestatievoorLeveringTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Levering leveringBack = getLeveringRandomSampleGenerator();

        client.addPrestatievoorLevering(leveringBack);
        assertThat(client.getPrestatievoorLeverings()).containsOnly(leveringBack);
        assertThat(leveringBack.getPrestatievoorClient()).isEqualTo(client);

        client.removePrestatievoorLevering(leveringBack);
        assertThat(client.getPrestatievoorLeverings()).doesNotContain(leveringBack);
        assertThat(leveringBack.getPrestatievoorClient()).isNull();

        client.prestatievoorLeverings(new HashSet<>(Set.of(leveringBack)));
        assertThat(client.getPrestatievoorLeverings()).containsOnly(leveringBack);
        assertThat(leveringBack.getPrestatievoorClient()).isEqualTo(client);

        client.setPrestatievoorLeverings(new HashSet<>());
        assertThat(client.getPrestatievoorLeverings()).doesNotContain(leveringBack);
        assertThat(leveringBack.getPrestatievoorClient()).isNull();
    }

    @Test
    void betreftDeclaratieregelTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Declaratieregel declaratieregelBack = getDeclaratieregelRandomSampleGenerator();

        client.addBetreftDeclaratieregel(declaratieregelBack);
        assertThat(client.getBetreftDeclaratieregels()).containsOnly(declaratieregelBack);
        assertThat(declaratieregelBack.getBetreftClient()).isEqualTo(client);

        client.removeBetreftDeclaratieregel(declaratieregelBack);
        assertThat(client.getBetreftDeclaratieregels()).doesNotContain(declaratieregelBack);
        assertThat(declaratieregelBack.getBetreftClient()).isNull();

        client.betreftDeclaratieregels(new HashSet<>(Set.of(declaratieregelBack)));
        assertThat(client.getBetreftDeclaratieregels()).containsOnly(declaratieregelBack);
        assertThat(declaratieregelBack.getBetreftClient()).isEqualTo(client);

        client.setBetreftDeclaratieregels(new HashSet<>());
        assertThat(client.getBetreftDeclaratieregels()).doesNotContain(declaratieregelBack);
        assertThat(declaratieregelBack.getBetreftClient()).isNull();
    }

    @Test
    void ondersteuntclientClientbegeleiderTest() throws Exception {
        Client client = getClientRandomSampleGenerator();
        Clientbegeleider clientbegeleiderBack = getClientbegeleiderRandomSampleGenerator();

        client.addOndersteuntclientClientbegeleider(clientbegeleiderBack);
        assertThat(client.getOndersteuntclientClientbegeleiders()).containsOnly(clientbegeleiderBack);
        assertThat(clientbegeleiderBack.getOndersteuntclientClients()).containsOnly(client);

        client.removeOndersteuntclientClientbegeleider(clientbegeleiderBack);
        assertThat(client.getOndersteuntclientClientbegeleiders()).doesNotContain(clientbegeleiderBack);
        assertThat(clientbegeleiderBack.getOndersteuntclientClients()).doesNotContain(client);

        client.ondersteuntclientClientbegeleiders(new HashSet<>(Set.of(clientbegeleiderBack)));
        assertThat(client.getOndersteuntclientClientbegeleiders()).containsOnly(clientbegeleiderBack);
        assertThat(clientbegeleiderBack.getOndersteuntclientClients()).containsOnly(client);

        client.setOndersteuntclientClientbegeleiders(new HashSet<>());
        assertThat(client.getOndersteuntclientClientbegeleiders()).doesNotContain(clientbegeleiderBack);
        assertThat(clientbegeleiderBack.getOndersteuntclientClients()).doesNotContain(client);
    }
}
