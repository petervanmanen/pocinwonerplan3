package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DocumentTestSamples.*;
import static nl.ritense.demo.domain.VerkeersbesluitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerkeersbesluitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verkeersbesluit.class);
        Verkeersbesluit verkeersbesluit1 = getVerkeersbesluitSample1();
        Verkeersbesluit verkeersbesluit2 = new Verkeersbesluit();
        assertThat(verkeersbesluit1).isNotEqualTo(verkeersbesluit2);

        verkeersbesluit2.setId(verkeersbesluit1.getId());
        assertThat(verkeersbesluit1).isEqualTo(verkeersbesluit2);

        verkeersbesluit2 = getVerkeersbesluitSample2();
        assertThat(verkeersbesluit1).isNotEqualTo(verkeersbesluit2);
    }

    @Test
    void isvastgelegdinDocumentTest() throws Exception {
        Verkeersbesluit verkeersbesluit = getVerkeersbesluitRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        verkeersbesluit.setIsvastgelegdinDocument(documentBack);
        assertThat(verkeersbesluit.getIsvastgelegdinDocument()).isEqualTo(documentBack);

        verkeersbesluit.isvastgelegdinDocument(null);
        assertThat(verkeersbesluit.getIsvastgelegdinDocument()).isNull();
    }
}
