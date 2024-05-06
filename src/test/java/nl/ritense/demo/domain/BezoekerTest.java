package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AanvraagTestSamples.*;
import static nl.ritense.demo.domain.BezoekerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BezoekerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bezoeker.class);
        Bezoeker bezoeker1 = getBezoekerSample1();
        Bezoeker bezoeker2 = new Bezoeker();
        assertThat(bezoeker1).isNotEqualTo(bezoeker2);

        bezoeker2.setId(bezoeker1.getId());
        assertThat(bezoeker1).isEqualTo(bezoeker2);

        bezoeker2 = getBezoekerSample2();
        assertThat(bezoeker1).isNotEqualTo(bezoeker2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Bezoeker bezoeker = new Bezoeker();
        assertThat(bezoeker.hashCode()).isZero();

        Bezoeker bezoeker1 = getBezoekerSample1();
        bezoeker.setId(bezoeker1.getId());
        assertThat(bezoeker).hasSameHashCodeAs(bezoeker1);
    }

    @Test
    void doetAanvraagTest() throws Exception {
        Bezoeker bezoeker = getBezoekerRandomSampleGenerator();
        Aanvraag aanvraagBack = getAanvraagRandomSampleGenerator();

        bezoeker.addDoetAanvraag(aanvraagBack);
        assertThat(bezoeker.getDoetAanvraags()).containsOnly(aanvraagBack);
        assertThat(aanvraagBack.getDoetBezoeker()).isEqualTo(bezoeker);

        bezoeker.removeDoetAanvraag(aanvraagBack);
        assertThat(bezoeker.getDoetAanvraags()).doesNotContain(aanvraagBack);
        assertThat(aanvraagBack.getDoetBezoeker()).isNull();

        bezoeker.doetAanvraags(new HashSet<>(Set.of(aanvraagBack)));
        assertThat(bezoeker.getDoetAanvraags()).containsOnly(aanvraagBack);
        assertThat(aanvraagBack.getDoetBezoeker()).isEqualTo(bezoeker);

        bezoeker.setDoetAanvraags(new HashSet<>());
        assertThat(bezoeker.getDoetAanvraags()).doesNotContain(aanvraagBack);
        assertThat(aanvraagBack.getDoetBezoeker()).isNull();
    }
}
