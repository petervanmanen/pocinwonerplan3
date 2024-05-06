package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ApplicatieTestSamples.*;
import static nl.ritense.demo.domain.BesluitTestSamples.*;
import static nl.ritense.demo.domain.BinnenlocatieTestSamples.*;
import static nl.ritense.demo.domain.DocumentTestSamples.*;
import static nl.ritense.demo.domain.DocumenttypeTestSamples.*;
import static nl.ritense.demo.domain.IdentificatiekenmerkTestSamples.*;
import static nl.ritense.demo.domain.RapportagemomentTestSamples.*;
import static nl.ritense.demo.domain.SubsidieTestSamples.*;
import static nl.ritense.demo.domain.VerkeersbesluitTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Document.class);
        Document document1 = getDocumentSample1();
        Document document2 = new Document();
        assertThat(document1).isNotEqualTo(document2);

        document2.setId(document1.getId());
        assertThat(document1).isEqualTo(document2);

        document2 = getDocumentSample2();
        assertThat(document1).isNotEqualTo(document2);
    }

    @Test
    void heeftkenmerkIdentificatiekenmerkTest() throws Exception {
        Document document = getDocumentRandomSampleGenerator();
        Identificatiekenmerk identificatiekenmerkBack = getIdentificatiekenmerkRandomSampleGenerator();

        document.setHeeftkenmerkIdentificatiekenmerk(identificatiekenmerkBack);
        assertThat(document.getHeeftkenmerkIdentificatiekenmerk()).isEqualTo(identificatiekenmerkBack);

        document.heeftkenmerkIdentificatiekenmerk(null);
        assertThat(document.getHeeftkenmerkIdentificatiekenmerk()).isNull();
    }

    @Test
    void isvanDocumenttypeTest() throws Exception {
        Document document = getDocumentRandomSampleGenerator();
        Documenttype documenttypeBack = getDocumenttypeRandomSampleGenerator();

        document.setIsvanDocumenttype(documenttypeBack);
        assertThat(document.getIsvanDocumenttype()).isEqualTo(documenttypeBack);

        document.isvanDocumenttype(null);
        assertThat(document.getIsvanDocumenttype()).isNull();
    }

    @Test
    void isvastgelegdinVerkeersbesluitTest() throws Exception {
        Document document = getDocumentRandomSampleGenerator();
        Verkeersbesluit verkeersbesluitBack = getVerkeersbesluitRandomSampleGenerator();

        document.setIsvastgelegdinVerkeersbesluit(verkeersbesluitBack);
        assertThat(document.getIsvastgelegdinVerkeersbesluit()).isEqualTo(verkeersbesluitBack);
        assertThat(verkeersbesluitBack.getIsvastgelegdinDocument()).isEqualTo(document);

        document.isvastgelegdinVerkeersbesluit(null);
        assertThat(document.getIsvastgelegdinVerkeersbesluit()).isNull();
        assertThat(verkeersbesluitBack.getIsvastgelegdinDocument()).isNull();
    }

    @Test
    void isvastgelegdinBesluitTest() throws Exception {
        Document document = getDocumentRandomSampleGenerator();
        Besluit besluitBack = getBesluitRandomSampleGenerator();

        document.setIsvastgelegdinBesluit(besluitBack);
        assertThat(document.getIsvastgelegdinBesluit()).isEqualTo(besluitBack);
        assertThat(besluitBack.getIsvastgelegdinDocument()).isEqualTo(document);

        document.isvastgelegdinBesluit(null);
        assertThat(document.getIsvastgelegdinBesluit()).isNull();
        assertThat(besluitBack.getIsvastgelegdinDocument()).isNull();
    }

    @Test
    void inspectierapportBinnenlocatieTest() throws Exception {
        Document document = getDocumentRandomSampleGenerator();
        Binnenlocatie binnenlocatieBack = getBinnenlocatieRandomSampleGenerator();

        document.setInspectierapportBinnenlocatie(binnenlocatieBack);
        assertThat(document.getInspectierapportBinnenlocatie()).isEqualTo(binnenlocatieBack);

        document.inspectierapportBinnenlocatie(null);
        assertThat(document.getInspectierapportBinnenlocatie()).isNull();
    }

    @Test
    void heeftRapportagemomentTest() throws Exception {
        Document document = getDocumentRandomSampleGenerator();
        Rapportagemoment rapportagemomentBack = getRapportagemomentRandomSampleGenerator();

        document.setHeeftRapportagemoment(rapportagemomentBack);
        assertThat(document.getHeeftRapportagemoment()).isEqualTo(rapportagemomentBack);

        document.heeftRapportagemoment(null);
        assertThat(document.getHeeftRapportagemoment()).isNull();
    }

    @Test
    void heeftSubsidieTest() throws Exception {
        Document document = getDocumentRandomSampleGenerator();
        Subsidie subsidieBack = getSubsidieRandomSampleGenerator();

        document.addHeeftSubsidie(subsidieBack);
        assertThat(document.getHeeftSubsidies()).containsOnly(subsidieBack);
        assertThat(subsidieBack.getHeeftDocument()).isEqualTo(document);

        document.removeHeeftSubsidie(subsidieBack);
        assertThat(document.getHeeftSubsidies()).doesNotContain(subsidieBack);
        assertThat(subsidieBack.getHeeftDocument()).isNull();

        document.heeftSubsidies(new HashSet<>(Set.of(subsidieBack)));
        assertThat(document.getHeeftSubsidies()).containsOnly(subsidieBack);
        assertThat(subsidieBack.getHeeftDocument()).isEqualTo(document);

        document.setHeeftSubsidies(new HashSet<>());
        assertThat(document.getHeeftSubsidies()).doesNotContain(subsidieBack);
        assertThat(subsidieBack.getHeeftDocument()).isNull();
    }

    @Test
    void heeftdocumentenApplicatieTest() throws Exception {
        Document document = getDocumentRandomSampleGenerator();
        Applicatie applicatieBack = getApplicatieRandomSampleGenerator();

        document.addHeeftdocumentenApplicatie(applicatieBack);
        assertThat(document.getHeeftdocumentenApplicaties()).containsOnly(applicatieBack);
        assertThat(applicatieBack.getHeeftdocumentenDocuments()).containsOnly(document);

        document.removeHeeftdocumentenApplicatie(applicatieBack);
        assertThat(document.getHeeftdocumentenApplicaties()).doesNotContain(applicatieBack);
        assertThat(applicatieBack.getHeeftdocumentenDocuments()).doesNotContain(document);

        document.heeftdocumentenApplicaties(new HashSet<>(Set.of(applicatieBack)));
        assertThat(document.getHeeftdocumentenApplicaties()).containsOnly(applicatieBack);
        assertThat(applicatieBack.getHeeftdocumentenDocuments()).containsOnly(document);

        document.setHeeftdocumentenApplicaties(new HashSet<>());
        assertThat(document.getHeeftdocumentenApplicaties()).doesNotContain(applicatieBack);
        assertThat(applicatieBack.getHeeftdocumentenDocuments()).doesNotContain(document);
    }

    @Test
    void kanvastgelegdzijnalsBesluitTest() throws Exception {
        Document document = getDocumentRandomSampleGenerator();
        Besluit besluitBack = getBesluitRandomSampleGenerator();

        document.addKanvastgelegdzijnalsBesluit(besluitBack);
        assertThat(document.getKanvastgelegdzijnalsBesluits()).containsOnly(besluitBack);
        assertThat(besluitBack.getKanvastgelegdzijnalsDocuments()).containsOnly(document);

        document.removeKanvastgelegdzijnalsBesluit(besluitBack);
        assertThat(document.getKanvastgelegdzijnalsBesluits()).doesNotContain(besluitBack);
        assertThat(besluitBack.getKanvastgelegdzijnalsDocuments()).doesNotContain(document);

        document.kanvastgelegdzijnalsBesluits(new HashSet<>(Set.of(besluitBack)));
        assertThat(document.getKanvastgelegdzijnalsBesluits()).containsOnly(besluitBack);
        assertThat(besluitBack.getKanvastgelegdzijnalsDocuments()).containsOnly(document);

        document.setKanvastgelegdzijnalsBesluits(new HashSet<>());
        assertThat(document.getKanvastgelegdzijnalsBesluits()).doesNotContain(besluitBack);
        assertThat(besluitBack.getKanvastgelegdzijnalsDocuments()).doesNotContain(document);
    }

    @Test
    void kentZaakTest() throws Exception {
        Document document = getDocumentRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        document.addKentZaak(zaakBack);
        assertThat(document.getKentZaaks()).containsOnly(zaakBack);
        assertThat(zaakBack.getKentDocuments()).containsOnly(document);

        document.removeKentZaak(zaakBack);
        assertThat(document.getKentZaaks()).doesNotContain(zaakBack);
        assertThat(zaakBack.getKentDocuments()).doesNotContain(document);

        document.kentZaaks(new HashSet<>(Set.of(zaakBack)));
        assertThat(document.getKentZaaks()).containsOnly(zaakBack);
        assertThat(zaakBack.getKentDocuments()).containsOnly(document);

        document.setKentZaaks(new HashSet<>());
        assertThat(document.getKentZaaks()).doesNotContain(zaakBack);
        assertThat(zaakBack.getKentDocuments()).doesNotContain(document);
    }
}
