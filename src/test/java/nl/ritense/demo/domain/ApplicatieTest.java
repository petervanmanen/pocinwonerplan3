package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ApplicatieTestSamples.*;
import static nl.ritense.demo.domain.BatchTestSamples.*;
import static nl.ritense.demo.domain.DocumentTestSamples.*;
import static nl.ritense.demo.domain.GegevenTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.MedewerkerTestSamples.*;
import static nl.ritense.demo.domain.NotitieTestSamples.*;
import static nl.ritense.demo.domain.VersieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ApplicatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Applicatie.class);
        Applicatie applicatie1 = getApplicatieSample1();
        Applicatie applicatie2 = new Applicatie();
        assertThat(applicatie1).isNotEqualTo(applicatie2);

        applicatie2.setId(applicatie1.getId());
        assertThat(applicatie1).isEqualTo(applicatie2);

        applicatie2 = getApplicatieSample2();
        assertThat(applicatie1).isNotEqualTo(applicatie2);
    }

    @Test
    void heeftversiesVersieTest() throws Exception {
        Applicatie applicatie = getApplicatieRandomSampleGenerator();
        Versie versieBack = getVersieRandomSampleGenerator();

        applicatie.addHeeftversiesVersie(versieBack);
        assertThat(applicatie.getHeeftversiesVersies()).containsOnly(versieBack);
        assertThat(versieBack.getHeeftversiesApplicatie()).isEqualTo(applicatie);

        applicatie.removeHeeftversiesVersie(versieBack);
        assertThat(applicatie.getHeeftversiesVersies()).doesNotContain(versieBack);
        assertThat(versieBack.getHeeftversiesApplicatie()).isNull();

        applicatie.heeftversiesVersies(new HashSet<>(Set.of(versieBack)));
        assertThat(applicatie.getHeeftversiesVersies()).containsOnly(versieBack);
        assertThat(versieBack.getHeeftversiesApplicatie()).isEqualTo(applicatie);

        applicatie.setHeeftversiesVersies(new HashSet<>());
        assertThat(applicatie.getHeeftversiesVersies()).doesNotContain(versieBack);
        assertThat(versieBack.getHeeftversiesApplicatie()).isNull();
    }

    @Test
    void bevatGegevenTest() throws Exception {
        Applicatie applicatie = getApplicatieRandomSampleGenerator();
        Gegeven gegevenBack = getGegevenRandomSampleGenerator();

        applicatie.addBevatGegeven(gegevenBack);
        assertThat(applicatie.getBevatGegevens()).containsOnly(gegevenBack);
        assertThat(gegevenBack.getBevatApplicatie()).isEqualTo(applicatie);

        applicatie.removeBevatGegeven(gegevenBack);
        assertThat(applicatie.getBevatGegevens()).doesNotContain(gegevenBack);
        assertThat(gegevenBack.getBevatApplicatie()).isNull();

        applicatie.bevatGegevens(new HashSet<>(Set.of(gegevenBack)));
        assertThat(applicatie.getBevatGegevens()).containsOnly(gegevenBack);
        assertThat(gegevenBack.getBevatApplicatie()).isEqualTo(applicatie);

        applicatie.setBevatGegevens(new HashSet<>());
        assertThat(applicatie.getBevatGegevens()).doesNotContain(gegevenBack);
        assertThat(gegevenBack.getBevatApplicatie()).isNull();
    }

    @Test
    void heeftherkomstBatchTest() throws Exception {
        Applicatie applicatie = getApplicatieRandomSampleGenerator();
        Batch batchBack = getBatchRandomSampleGenerator();

        applicatie.addHeeftherkomstBatch(batchBack);
        assertThat(applicatie.getHeeftherkomstBatches()).containsOnly(batchBack);
        assertThat(batchBack.getHeeftherkomstApplicatie()).isEqualTo(applicatie);

        applicatie.removeHeeftherkomstBatch(batchBack);
        assertThat(applicatie.getHeeftherkomstBatches()).doesNotContain(batchBack);
        assertThat(batchBack.getHeeftherkomstApplicatie()).isNull();

        applicatie.heeftherkomstBatches(new HashSet<>(Set.of(batchBack)));
        assertThat(applicatie.getHeeftherkomstBatches()).containsOnly(batchBack);
        assertThat(batchBack.getHeeftherkomstApplicatie()).isEqualTo(applicatie);

        applicatie.setHeeftherkomstBatches(new HashSet<>());
        assertThat(applicatie.getHeeftherkomstBatches()).doesNotContain(batchBack);
        assertThat(batchBack.getHeeftherkomstApplicatie()).isNull();
    }

    @Test
    void heeftnotitiesNotitieTest() throws Exception {
        Applicatie applicatie = getApplicatieRandomSampleGenerator();
        Notitie notitieBack = getNotitieRandomSampleGenerator();

        applicatie.addHeeftnotitiesNotitie(notitieBack);
        assertThat(applicatie.getHeeftnotitiesNotities()).containsOnly(notitieBack);
        assertThat(notitieBack.getHeeftnotitiesApplicatie()).isEqualTo(applicatie);

        applicatie.removeHeeftnotitiesNotitie(notitieBack);
        assertThat(applicatie.getHeeftnotitiesNotities()).doesNotContain(notitieBack);
        assertThat(notitieBack.getHeeftnotitiesApplicatie()).isNull();

        applicatie.heeftnotitiesNotities(new HashSet<>(Set.of(notitieBack)));
        assertThat(applicatie.getHeeftnotitiesNotities()).containsOnly(notitieBack);
        assertThat(notitieBack.getHeeftnotitiesApplicatie()).isEqualTo(applicatie);

        applicatie.setHeeftnotitiesNotities(new HashSet<>());
        assertThat(applicatie.getHeeftnotitiesNotities()).doesNotContain(notitieBack);
        assertThat(notitieBack.getHeeftnotitiesApplicatie()).isNull();
    }

    @Test
    void heeftleverancierLeverancierTest() throws Exception {
        Applicatie applicatie = getApplicatieRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        applicatie.setHeeftleverancierLeverancier(leverancierBack);
        assertThat(applicatie.getHeeftleverancierLeverancier()).isEqualTo(leverancierBack);

        applicatie.heeftleverancierLeverancier(null);
        assertThat(applicatie.getHeeftleverancierLeverancier()).isNull();
    }

    @Test
    void heeftdocumentenDocumentTest() throws Exception {
        Applicatie applicatie = getApplicatieRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        applicatie.addHeeftdocumentenDocument(documentBack);
        assertThat(applicatie.getHeeftdocumentenDocuments()).containsOnly(documentBack);

        applicatie.removeHeeftdocumentenDocument(documentBack);
        assertThat(applicatie.getHeeftdocumentenDocuments()).doesNotContain(documentBack);

        applicatie.heeftdocumentenDocuments(new HashSet<>(Set.of(documentBack)));
        assertThat(applicatie.getHeeftdocumentenDocuments()).containsOnly(documentBack);

        applicatie.setHeeftdocumentenDocuments(new HashSet<>());
        assertThat(applicatie.getHeeftdocumentenDocuments()).doesNotContain(documentBack);
    }

    @Test
    void rollenMedewerkerTest() throws Exception {
        Applicatie applicatie = getApplicatieRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        applicatie.addRollenMedewerker(medewerkerBack);
        assertThat(applicatie.getRollenMedewerkers()).containsOnly(medewerkerBack);

        applicatie.removeRollenMedewerker(medewerkerBack);
        assertThat(applicatie.getRollenMedewerkers()).doesNotContain(medewerkerBack);

        applicatie.rollenMedewerkers(new HashSet<>(Set.of(medewerkerBack)));
        assertThat(applicatie.getRollenMedewerkers()).containsOnly(medewerkerBack);

        applicatie.setRollenMedewerkers(new HashSet<>());
        assertThat(applicatie.getRollenMedewerkers()).doesNotContain(medewerkerBack);
    }
}
