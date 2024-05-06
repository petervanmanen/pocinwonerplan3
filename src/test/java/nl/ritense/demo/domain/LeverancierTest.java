package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ApplicatieTestSamples.*;
import static nl.ritense.demo.domain.CategorieTestSamples.*;
import static nl.ritense.demo.domain.ContractTestSamples.*;
import static nl.ritense.demo.domain.DeclaratieTestSamples.*;
import static nl.ritense.demo.domain.FactuurTestSamples.*;
import static nl.ritense.demo.domain.InkooporderTestSamples.*;
import static nl.ritense.demo.domain.InschrijvingTestSamples.*;
import static nl.ritense.demo.domain.KandidaatTestSamples.*;
import static nl.ritense.demo.domain.KwalificatieTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.LeveringTestSamples.*;
import static nl.ritense.demo.domain.MedewerkerTestSamples.*;
import static nl.ritense.demo.domain.MeldingTestSamples.*;
import static nl.ritense.demo.domain.OfferteTestSamples.*;
import static nl.ritense.demo.domain.OfferteaanvraagTestSamples.*;
import static nl.ritense.demo.domain.ProductTestSamples.*;
import static nl.ritense.demo.domain.ServerTestSamples.*;
import static nl.ritense.demo.domain.TariefTestSamples.*;
import static nl.ritense.demo.domain.ToewijzingTestSamples.*;
import static nl.ritense.demo.domain.UitnodigingTestSamples.*;
import static nl.ritense.demo.domain.WerkbonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeverancierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leverancier.class);
        Leverancier leverancier1 = getLeverancierSample1();
        Leverancier leverancier2 = new Leverancier();
        assertThat(leverancier1).isNotEqualTo(leverancier2);

        leverancier2.setId(leverancier1.getId());
        assertThat(leverancier1).isEqualTo(leverancier2);

        leverancier2 = getLeverancierSample2();
        assertThat(leverancier1).isNotEqualTo(leverancier2);
    }

    @Test
    void heeftContractTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Contract contractBack = getContractRandomSampleGenerator();

        leverancier.addHeeftContract(contractBack);
        assertThat(leverancier.getHeeftContracts()).containsOnly(contractBack);
        assertThat(contractBack.getHeeftLeverancier()).isEqualTo(leverancier);

        leverancier.removeHeeftContract(contractBack);
        assertThat(leverancier.getHeeftContracts()).doesNotContain(contractBack);
        assertThat(contractBack.getHeeftLeverancier()).isNull();

        leverancier.heeftContracts(new HashSet<>(Set.of(contractBack)));
        assertThat(leverancier.getHeeftContracts()).containsOnly(contractBack);
        assertThat(contractBack.getHeeftLeverancier()).isEqualTo(leverancier);

        leverancier.setHeeftContracts(new HashSet<>());
        assertThat(leverancier.getHeeftContracts()).doesNotContain(contractBack);
        assertThat(contractBack.getHeeftLeverancier()).isNull();
    }

    @Test
    void leverdeprestatieLeveringTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Levering leveringBack = getLeveringRandomSampleGenerator();

        leverancier.addLeverdeprestatieLevering(leveringBack);
        assertThat(leverancier.getLeverdeprestatieLeverings()).containsOnly(leveringBack);
        assertThat(leveringBack.getLeverdeprestatieLeverancier()).isEqualTo(leverancier);

        leverancier.removeLeverdeprestatieLevering(leveringBack);
        assertThat(leverancier.getLeverdeprestatieLeverings()).doesNotContain(leveringBack);
        assertThat(leveringBack.getLeverdeprestatieLeverancier()).isNull();

        leverancier.leverdeprestatieLeverings(new HashSet<>(Set.of(leveringBack)));
        assertThat(leverancier.getLeverdeprestatieLeverings()).containsOnly(leveringBack);
        assertThat(leveringBack.getLeverdeprestatieLeverancier()).isEqualTo(leverancier);

        leverancier.setLeverdeprestatieLeverings(new HashSet<>());
        assertThat(leverancier.getLeverdeprestatieLeverings()).doesNotContain(leveringBack);
        assertThat(leveringBack.getLeverdeprestatieLeverancier()).isNull();
    }

    @Test
    void voertwerkuitconformWerkbonTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Werkbon werkbonBack = getWerkbonRandomSampleGenerator();

        leverancier.addVoertwerkuitconformWerkbon(werkbonBack);
        assertThat(leverancier.getVoertwerkuitconformWerkbons()).containsOnly(werkbonBack);
        assertThat(werkbonBack.getVoertwerkuitconformLeverancier()).isEqualTo(leverancier);

        leverancier.removeVoertwerkuitconformWerkbon(werkbonBack);
        assertThat(leverancier.getVoertwerkuitconformWerkbons()).doesNotContain(werkbonBack);
        assertThat(werkbonBack.getVoertwerkuitconformLeverancier()).isNull();

        leverancier.voertwerkuitconformWerkbons(new HashSet<>(Set.of(werkbonBack)));
        assertThat(leverancier.getVoertwerkuitconformWerkbons()).containsOnly(werkbonBack);
        assertThat(werkbonBack.getVoertwerkuitconformLeverancier()).isEqualTo(leverancier);

        leverancier.setVoertwerkuitconformWerkbons(new HashSet<>());
        assertThat(leverancier.getVoertwerkuitconformWerkbons()).doesNotContain(werkbonBack);
        assertThat(werkbonBack.getVoertwerkuitconformLeverancier()).isNull();
    }

    @Test
    void contractantContractTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Contract contractBack = getContractRandomSampleGenerator();

        leverancier.addContractantContract(contractBack);
        assertThat(leverancier.getContractantContracts()).containsOnly(contractBack);
        assertThat(contractBack.getContractantLeverancier()).isEqualTo(leverancier);

        leverancier.removeContractantContract(contractBack);
        assertThat(leverancier.getContractantContracts()).doesNotContain(contractBack);
        assertThat(contractBack.getContractantLeverancier()).isNull();

        leverancier.contractantContracts(new HashSet<>(Set.of(contractBack)));
        assertThat(leverancier.getContractantContracts()).containsOnly(contractBack);
        assertThat(contractBack.getContractantLeverancier()).isEqualTo(leverancier);

        leverancier.setContractantContracts(new HashSet<>());
        assertThat(leverancier.getContractantContracts()).doesNotContain(contractBack);
        assertThat(contractBack.getContractantLeverancier()).isNull();
    }

    @Test
    void heeftInschrijvingTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Inschrijving inschrijvingBack = getInschrijvingRandomSampleGenerator();

        leverancier.addHeeftInschrijving(inschrijvingBack);
        assertThat(leverancier.getHeeftInschrijvings()).containsOnly(inschrijvingBack);
        assertThat(inschrijvingBack.getHeeftLeverancier()).isEqualTo(leverancier);

        leverancier.removeHeeftInschrijving(inschrijvingBack);
        assertThat(leverancier.getHeeftInschrijvings()).doesNotContain(inschrijvingBack);
        assertThat(inschrijvingBack.getHeeftLeverancier()).isNull();

        leverancier.heeftInschrijvings(new HashSet<>(Set.of(inschrijvingBack)));
        assertThat(leverancier.getHeeftInschrijvings()).containsOnly(inschrijvingBack);
        assertThat(inschrijvingBack.getHeeftLeverancier()).isEqualTo(leverancier);

        leverancier.setHeeftInschrijvings(new HashSet<>());
        assertThat(leverancier.getHeeftInschrijvings()).doesNotContain(inschrijvingBack);
        assertThat(inschrijvingBack.getHeeftLeverancier()).isNull();
    }

    @Test
    void biedtaanKandidaatTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Kandidaat kandidaatBack = getKandidaatRandomSampleGenerator();

        leverancier.addBiedtaanKandidaat(kandidaatBack);
        assertThat(leverancier.getBiedtaanKandidaats()).containsOnly(kandidaatBack);
        assertThat(kandidaatBack.getBiedtaanLeverancier()).isEqualTo(leverancier);

        leverancier.removeBiedtaanKandidaat(kandidaatBack);
        assertThat(leverancier.getBiedtaanKandidaats()).doesNotContain(kandidaatBack);
        assertThat(kandidaatBack.getBiedtaanLeverancier()).isNull();

        leverancier.biedtaanKandidaats(new HashSet<>(Set.of(kandidaatBack)));
        assertThat(leverancier.getBiedtaanKandidaats()).containsOnly(kandidaatBack);
        assertThat(kandidaatBack.getBiedtaanLeverancier()).isEqualTo(leverancier);

        leverancier.setBiedtaanKandidaats(new HashSet<>());
        assertThat(leverancier.getBiedtaanKandidaats()).doesNotContain(kandidaatBack);
        assertThat(kandidaatBack.getBiedtaanLeverancier()).isNull();
    }

    @Test
    void heeftKwalificatieTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Kwalificatie kwalificatieBack = getKwalificatieRandomSampleGenerator();

        leverancier.addHeeftKwalificatie(kwalificatieBack);
        assertThat(leverancier.getHeeftKwalificaties()).containsOnly(kwalificatieBack);
        assertThat(kwalificatieBack.getHeeftLeverancier()).isEqualTo(leverancier);

        leverancier.removeHeeftKwalificatie(kwalificatieBack);
        assertThat(leverancier.getHeeftKwalificaties()).doesNotContain(kwalificatieBack);
        assertThat(kwalificatieBack.getHeeftLeverancier()).isNull();

        leverancier.heeftKwalificaties(new HashSet<>(Set.of(kwalificatieBack)));
        assertThat(leverancier.getHeeftKwalificaties()).containsOnly(kwalificatieBack);
        assertThat(kwalificatieBack.getHeeftLeverancier()).isEqualTo(leverancier);

        leverancier.setHeeftKwalificaties(new HashSet<>());
        assertThat(leverancier.getHeeftKwalificaties()).doesNotContain(kwalificatieBack);
        assertThat(kwalificatieBack.getHeeftLeverancier()).isNull();
    }

    @Test
    void gekwalificeerdCategorieTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Categorie categorieBack = getCategorieRandomSampleGenerator();

        leverancier.addGekwalificeerdCategorie(categorieBack);
        assertThat(leverancier.getGekwalificeerdCategories()).containsOnly(categorieBack);

        leverancier.removeGekwalificeerdCategorie(categorieBack);
        assertThat(leverancier.getGekwalificeerdCategories()).doesNotContain(categorieBack);

        leverancier.gekwalificeerdCategories(new HashSet<>(Set.of(categorieBack)));
        assertThat(leverancier.getGekwalificeerdCategories()).containsOnly(categorieBack);

        leverancier.setGekwalificeerdCategories(new HashSet<>());
        assertThat(leverancier.getGekwalificeerdCategories()).doesNotContain(categorieBack);
    }

    @Test
    void leverancierProductTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        leverancier.addLeverancierProduct(productBack);
        assertThat(leverancier.getLeverancierProducts()).containsOnly(productBack);
        assertThat(productBack.getLeverancierLeverancier()).isEqualTo(leverancier);

        leverancier.removeLeverancierProduct(productBack);
        assertThat(leverancier.getLeverancierProducts()).doesNotContain(productBack);
        assertThat(productBack.getLeverancierLeverancier()).isNull();

        leverancier.leverancierProducts(new HashSet<>(Set.of(productBack)));
        assertThat(leverancier.getLeverancierProducts()).containsOnly(productBack);
        assertThat(productBack.getLeverancierLeverancier()).isEqualTo(leverancier);

        leverancier.setLeverancierProducts(new HashSet<>());
        assertThat(leverancier.getLeverancierProducts()).doesNotContain(productBack);
        assertThat(productBack.getLeverancierLeverancier()).isNull();
    }

    @Test
    void ingedienddoorDeclaratieTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Declaratie declaratieBack = getDeclaratieRandomSampleGenerator();

        leverancier.addIngedienddoorDeclaratie(declaratieBack);
        assertThat(leverancier.getIngedienddoorDeclaraties()).containsOnly(declaratieBack);
        assertThat(declaratieBack.getIngedienddoorLeverancier()).isEqualTo(leverancier);

        leverancier.removeIngedienddoorDeclaratie(declaratieBack);
        assertThat(leverancier.getIngedienddoorDeclaraties()).doesNotContain(declaratieBack);
        assertThat(declaratieBack.getIngedienddoorLeverancier()).isNull();

        leverancier.ingedienddoorDeclaraties(new HashSet<>(Set.of(declaratieBack)));
        assertThat(leverancier.getIngedienddoorDeclaraties()).containsOnly(declaratieBack);
        assertThat(declaratieBack.getIngedienddoorLeverancier()).isEqualTo(leverancier);

        leverancier.setIngedienddoorDeclaraties(new HashSet<>());
        assertThat(leverancier.getIngedienddoorDeclaraties()).doesNotContain(declaratieBack);
        assertThat(declaratieBack.getIngedienddoorLeverancier()).isNull();
    }

    @Test
    void levertvoorzieningToewijzingTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Toewijzing toewijzingBack = getToewijzingRandomSampleGenerator();

        leverancier.addLevertvoorzieningToewijzing(toewijzingBack);
        assertThat(leverancier.getLevertvoorzieningToewijzings()).containsOnly(toewijzingBack);
        assertThat(toewijzingBack.getLevertvoorzieningLeverancier()).isEqualTo(leverancier);

        leverancier.removeLevertvoorzieningToewijzing(toewijzingBack);
        assertThat(leverancier.getLevertvoorzieningToewijzings()).doesNotContain(toewijzingBack);
        assertThat(toewijzingBack.getLevertvoorzieningLeverancier()).isNull();

        leverancier.levertvoorzieningToewijzings(new HashSet<>(Set.of(toewijzingBack)));
        assertThat(leverancier.getLevertvoorzieningToewijzings()).containsOnly(toewijzingBack);
        assertThat(toewijzingBack.getLevertvoorzieningLeverancier()).isEqualTo(leverancier);

        leverancier.setLevertvoorzieningToewijzings(new HashSet<>());
        assertThat(leverancier.getLevertvoorzieningToewijzings()).doesNotContain(toewijzingBack);
        assertThat(toewijzingBack.getLevertvoorzieningLeverancier()).isNull();
    }

    @Test
    void heeftTariefTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Tarief tariefBack = getTariefRandomSampleGenerator();

        leverancier.addHeeftTarief(tariefBack);
        assertThat(leverancier.getHeeftTariefs()).containsOnly(tariefBack);
        assertThat(tariefBack.getHeeftLeverancier()).isEqualTo(leverancier);

        leverancier.removeHeeftTarief(tariefBack);
        assertThat(leverancier.getHeeftTariefs()).doesNotContain(tariefBack);
        assertThat(tariefBack.getHeeftLeverancier()).isNull();

        leverancier.heeftTariefs(new HashSet<>(Set.of(tariefBack)));
        assertThat(leverancier.getHeeftTariefs()).containsOnly(tariefBack);
        assertThat(tariefBack.getHeeftLeverancier()).isEqualTo(leverancier);

        leverancier.setHeeftTariefs(new HashSet<>());
        assertThat(leverancier.getHeeftTariefs()).doesNotContain(tariefBack);
        assertThat(tariefBack.getHeeftLeverancier()).isNull();
    }

    @Test
    void uitvoerderMeldingTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Melding meldingBack = getMeldingRandomSampleGenerator();

        leverancier.addUitvoerderMelding(meldingBack);
        assertThat(leverancier.getUitvoerderMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getUitvoerderLeverancier()).isEqualTo(leverancier);

        leverancier.removeUitvoerderMelding(meldingBack);
        assertThat(leverancier.getUitvoerderMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getUitvoerderLeverancier()).isNull();

        leverancier.uitvoerderMeldings(new HashSet<>(Set.of(meldingBack)));
        assertThat(leverancier.getUitvoerderMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getUitvoerderLeverancier()).isEqualTo(leverancier);

        leverancier.setUitvoerderMeldings(new HashSet<>());
        assertThat(leverancier.getUitvoerderMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getUitvoerderLeverancier()).isNull();
    }

    @Test
    void heeftleverancierApplicatieTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Applicatie applicatieBack = getApplicatieRandomSampleGenerator();

        leverancier.addHeeftleverancierApplicatie(applicatieBack);
        assertThat(leverancier.getHeeftleverancierApplicaties()).containsOnly(applicatieBack);
        assertThat(applicatieBack.getHeeftleverancierLeverancier()).isEqualTo(leverancier);

        leverancier.removeHeeftleverancierApplicatie(applicatieBack);
        assertThat(leverancier.getHeeftleverancierApplicaties()).doesNotContain(applicatieBack);
        assertThat(applicatieBack.getHeeftleverancierLeverancier()).isNull();

        leverancier.heeftleverancierApplicaties(new HashSet<>(Set.of(applicatieBack)));
        assertThat(leverancier.getHeeftleverancierApplicaties()).containsOnly(applicatieBack);
        assertThat(applicatieBack.getHeeftleverancierLeverancier()).isEqualTo(leverancier);

        leverancier.setHeeftleverancierApplicaties(new HashSet<>());
        assertThat(leverancier.getHeeftleverancierApplicaties()).doesNotContain(applicatieBack);
        assertThat(applicatieBack.getHeeftleverancierLeverancier()).isNull();
    }

    @Test
    void heeftleverancierServerTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Server serverBack = getServerRandomSampleGenerator();

        leverancier.addHeeftleverancierServer(serverBack);
        assertThat(leverancier.getHeeftleverancierServers()).containsOnly(serverBack);
        assertThat(serverBack.getHeeftleverancierLeverancier()).isEqualTo(leverancier);

        leverancier.removeHeeftleverancierServer(serverBack);
        assertThat(leverancier.getHeeftleverancierServers()).doesNotContain(serverBack);
        assertThat(serverBack.getHeeftleverancierLeverancier()).isNull();

        leverancier.heeftleverancierServers(new HashSet<>(Set.of(serverBack)));
        assertThat(leverancier.getHeeftleverancierServers()).containsOnly(serverBack);
        assertThat(serverBack.getHeeftleverancierLeverancier()).isEqualTo(leverancier);

        leverancier.setHeeftleverancierServers(new HashSet<>());
        assertThat(leverancier.getHeeftleverancierServers()).doesNotContain(serverBack);
        assertThat(serverBack.getHeeftleverancierLeverancier()).isNull();
    }

    @Test
    void crediteurFactuurTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Factuur factuurBack = getFactuurRandomSampleGenerator();

        leverancier.addCrediteurFactuur(factuurBack);
        assertThat(leverancier.getCrediteurFactuurs()).containsOnly(factuurBack);
        assertThat(factuurBack.getCrediteurLeverancier()).isEqualTo(leverancier);

        leverancier.removeCrediteurFactuur(factuurBack);
        assertThat(leverancier.getCrediteurFactuurs()).doesNotContain(factuurBack);
        assertThat(factuurBack.getCrediteurLeverancier()).isNull();

        leverancier.crediteurFactuurs(new HashSet<>(Set.of(factuurBack)));
        assertThat(leverancier.getCrediteurFactuurs()).containsOnly(factuurBack);
        assertThat(factuurBack.getCrediteurLeverancier()).isEqualTo(leverancier);

        leverancier.setCrediteurFactuurs(new HashSet<>());
        assertThat(leverancier.getCrediteurFactuurs()).doesNotContain(factuurBack);
        assertThat(factuurBack.getCrediteurLeverancier()).isNull();
    }

    @Test
    void verplichtingaanInkooporderTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Inkooporder inkooporderBack = getInkooporderRandomSampleGenerator();

        leverancier.addVerplichtingaanInkooporder(inkooporderBack);
        assertThat(leverancier.getVerplichtingaanInkooporders()).containsOnly(inkooporderBack);
        assertThat(inkooporderBack.getVerplichtingaanLeverancier()).isEqualTo(leverancier);

        leverancier.removeVerplichtingaanInkooporder(inkooporderBack);
        assertThat(leverancier.getVerplichtingaanInkooporders()).doesNotContain(inkooporderBack);
        assertThat(inkooporderBack.getVerplichtingaanLeverancier()).isNull();

        leverancier.verplichtingaanInkooporders(new HashSet<>(Set.of(inkooporderBack)));
        assertThat(leverancier.getVerplichtingaanInkooporders()).containsOnly(inkooporderBack);
        assertThat(inkooporderBack.getVerplichtingaanLeverancier()).isEqualTo(leverancier);

        leverancier.setVerplichtingaanInkooporders(new HashSet<>());
        assertThat(leverancier.getVerplichtingaanInkooporders()).doesNotContain(inkooporderBack);
        assertThat(inkooporderBack.getVerplichtingaanLeverancier()).isNull();
    }

    @Test
    void gerichtaanUitnodigingTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Uitnodiging uitnodigingBack = getUitnodigingRandomSampleGenerator();

        leverancier.addGerichtaanUitnodiging(uitnodigingBack);
        assertThat(leverancier.getGerichtaanUitnodigings()).containsOnly(uitnodigingBack);
        assertThat(uitnodigingBack.getGerichtaanLeverancier()).isEqualTo(leverancier);

        leverancier.removeGerichtaanUitnodiging(uitnodigingBack);
        assertThat(leverancier.getGerichtaanUitnodigings()).doesNotContain(uitnodigingBack);
        assertThat(uitnodigingBack.getGerichtaanLeverancier()).isNull();

        leverancier.gerichtaanUitnodigings(new HashSet<>(Set.of(uitnodigingBack)));
        assertThat(leverancier.getGerichtaanUitnodigings()).containsOnly(uitnodigingBack);
        assertThat(uitnodigingBack.getGerichtaanLeverancier()).isEqualTo(leverancier);

        leverancier.setGerichtaanUitnodigings(new HashSet<>());
        assertThat(leverancier.getGerichtaanUitnodigings()).doesNotContain(uitnodigingBack);
        assertThat(uitnodigingBack.getGerichtaanLeverancier()).isNull();
    }

    @Test
    void geleverdviaMedewerkerTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        leverancier.addGeleverdviaMedewerker(medewerkerBack);
        assertThat(leverancier.getGeleverdviaMedewerkers()).containsOnly(medewerkerBack);
        assertThat(medewerkerBack.getGeleverdviaLeverancier()).isEqualTo(leverancier);

        leverancier.removeGeleverdviaMedewerker(medewerkerBack);
        assertThat(leverancier.getGeleverdviaMedewerkers()).doesNotContain(medewerkerBack);
        assertThat(medewerkerBack.getGeleverdviaLeverancier()).isNull();

        leverancier.geleverdviaMedewerkers(new HashSet<>(Set.of(medewerkerBack)));
        assertThat(leverancier.getGeleverdviaMedewerkers()).containsOnly(medewerkerBack);
        assertThat(medewerkerBack.getGeleverdviaLeverancier()).isEqualTo(leverancier);

        leverancier.setGeleverdviaMedewerkers(new HashSet<>());
        assertThat(leverancier.getGeleverdviaMedewerkers()).doesNotContain(medewerkerBack);
        assertThat(medewerkerBack.getGeleverdviaLeverancier()).isNull();
    }

    @Test
    void gerichtaanOfferteaanvraagTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Offerteaanvraag offerteaanvraagBack = getOfferteaanvraagRandomSampleGenerator();

        leverancier.addGerichtaanOfferteaanvraag(offerteaanvraagBack);
        assertThat(leverancier.getGerichtaanOfferteaanvraags()).containsOnly(offerteaanvraagBack);
        assertThat(offerteaanvraagBack.getGerichtaanLeverancier()).isEqualTo(leverancier);

        leverancier.removeGerichtaanOfferteaanvraag(offerteaanvraagBack);
        assertThat(leverancier.getGerichtaanOfferteaanvraags()).doesNotContain(offerteaanvraagBack);
        assertThat(offerteaanvraagBack.getGerichtaanLeverancier()).isNull();

        leverancier.gerichtaanOfferteaanvraags(new HashSet<>(Set.of(offerteaanvraagBack)));
        assertThat(leverancier.getGerichtaanOfferteaanvraags()).containsOnly(offerteaanvraagBack);
        assertThat(offerteaanvraagBack.getGerichtaanLeverancier()).isEqualTo(leverancier);

        leverancier.setGerichtaanOfferteaanvraags(new HashSet<>());
        assertThat(leverancier.getGerichtaanOfferteaanvraags()).doesNotContain(offerteaanvraagBack);
        assertThat(offerteaanvraagBack.getGerichtaanLeverancier()).isNull();
    }

    @Test
    void ingedienddoorOfferteTest() throws Exception {
        Leverancier leverancier = getLeverancierRandomSampleGenerator();
        Offerte offerteBack = getOfferteRandomSampleGenerator();

        leverancier.addIngedienddoorOfferte(offerteBack);
        assertThat(leverancier.getIngedienddoorOffertes()).containsOnly(offerteBack);
        assertThat(offerteBack.getIngedienddoorLeverancier()).isEqualTo(leverancier);

        leverancier.removeIngedienddoorOfferte(offerteBack);
        assertThat(leverancier.getIngedienddoorOffertes()).doesNotContain(offerteBack);
        assertThat(offerteBack.getIngedienddoorLeverancier()).isNull();

        leverancier.ingedienddoorOffertes(new HashSet<>(Set.of(offerteBack)));
        assertThat(leverancier.getIngedienddoorOffertes()).containsOnly(offerteBack);
        assertThat(offerteBack.getIngedienddoorLeverancier()).isEqualTo(leverancier);

        leverancier.setIngedienddoorOffertes(new HashSet<>());
        assertThat(leverancier.getIngedienddoorOffertes()).doesNotContain(offerteBack);
        assertThat(offerteBack.getIngedienddoorLeverancier()).isNull();
    }
}
