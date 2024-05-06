package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BesluitTestSamples.*;
import static nl.ritense.demo.domain.BesluittypeTestSamples.*;
import static nl.ritense.demo.domain.DocumentTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BesluitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Besluit.class);
        Besluit besluit1 = getBesluitSample1();
        Besluit besluit2 = new Besluit();
        assertThat(besluit1).isNotEqualTo(besluit2);

        besluit2.setId(besluit1.getId());
        assertThat(besluit1).isEqualTo(besluit2);

        besluit2 = getBesluitSample2();
        assertThat(besluit1).isNotEqualTo(besluit2);
    }

    @Test
    void isvastgelegdinDocumentTest() throws Exception {
        Besluit besluit = getBesluitRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        besluit.setIsvastgelegdinDocument(documentBack);
        assertThat(besluit.getIsvastgelegdinDocument()).isEqualTo(documentBack);

        besluit.isvastgelegdinDocument(null);
        assertThat(besluit.getIsvastgelegdinDocument()).isNull();
    }

    @Test
    void isuitkomstvanZaakTest() throws Exception {
        Besluit besluit = getBesluitRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        besluit.setIsuitkomstvanZaak(zaakBack);
        assertThat(besluit.getIsuitkomstvanZaak()).isEqualTo(zaakBack);

        besluit.isuitkomstvanZaak(null);
        assertThat(besluit.getIsuitkomstvanZaak()).isNull();
    }

    @Test
    void isvanBesluittypeTest() throws Exception {
        Besluit besluit = getBesluitRandomSampleGenerator();
        Besluittype besluittypeBack = getBesluittypeRandomSampleGenerator();

        besluit.setIsvanBesluittype(besluittypeBack);
        assertThat(besluit.getIsvanBesluittype()).isEqualTo(besluittypeBack);

        besluit.isvanBesluittype(null);
        assertThat(besluit.getIsvanBesluittype()).isNull();
    }

    @Test
    void kanvastgelegdzijnalsDocumentTest() throws Exception {
        Besluit besluit = getBesluitRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        besluit.addKanvastgelegdzijnalsDocument(documentBack);
        assertThat(besluit.getKanvastgelegdzijnalsDocuments()).containsOnly(documentBack);

        besluit.removeKanvastgelegdzijnalsDocument(documentBack);
        assertThat(besluit.getKanvastgelegdzijnalsDocuments()).doesNotContain(documentBack);

        besluit.kanvastgelegdzijnalsDocuments(new HashSet<>(Set.of(documentBack)));
        assertThat(besluit.getKanvastgelegdzijnalsDocuments()).containsOnly(documentBack);

        besluit.setKanvastgelegdzijnalsDocuments(new HashSet<>());
        assertThat(besluit.getKanvastgelegdzijnalsDocuments()).doesNotContain(documentBack);
    }
}
