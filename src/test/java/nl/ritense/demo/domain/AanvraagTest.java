package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanvraagTestSamples.*;
import static nl.ritense.demo.domain.ArchiefstukTestSamples.*;
import static nl.ritense.demo.domain.BezoekerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AanvraagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aanvraag.class);
        Aanvraag aanvraag1 = getAanvraagSample1();
        Aanvraag aanvraag2 = new Aanvraag();
        assertThat(aanvraag1).isNotEqualTo(aanvraag2);

        aanvraag2.setId(aanvraag1.getId());
        assertThat(aanvraag1).isEqualTo(aanvraag2);

        aanvraag2 = getAanvraagSample2();
        assertThat(aanvraag1).isNotEqualTo(aanvraag2);
    }

    @Test
    void voorArchiefstukTest() throws Exception {
        Aanvraag aanvraag = getAanvraagRandomSampleGenerator();
        Archiefstuk archiefstukBack = getArchiefstukRandomSampleGenerator();

        aanvraag.addVoorArchiefstuk(archiefstukBack);
        assertThat(aanvraag.getVoorArchiefstuks()).containsOnly(archiefstukBack);

        aanvraag.removeVoorArchiefstuk(archiefstukBack);
        assertThat(aanvraag.getVoorArchiefstuks()).doesNotContain(archiefstukBack);

        aanvraag.voorArchiefstuks(new HashSet<>(Set.of(archiefstukBack)));
        assertThat(aanvraag.getVoorArchiefstuks()).containsOnly(archiefstukBack);

        aanvraag.setVoorArchiefstuks(new HashSet<>());
        assertThat(aanvraag.getVoorArchiefstuks()).doesNotContain(archiefstukBack);
    }

    @Test
    void doetBezoekerTest() throws Exception {
        Aanvraag aanvraag = getAanvraagRandomSampleGenerator();
        Bezoeker bezoekerBack = getBezoekerRandomSampleGenerator();

        aanvraag.setDoetBezoeker(bezoekerBack);
        assertThat(aanvraag.getDoetBezoeker()).isEqualTo(bezoekerBack);

        aanvraag.doetBezoeker(null);
        assertThat(aanvraag.getDoetBezoeker()).isNull();
    }
}
