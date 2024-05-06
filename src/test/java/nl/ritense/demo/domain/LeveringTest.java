package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeschikkingTestSamples.*;
import static nl.ritense.demo.domain.ClientTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.LeveringTestSamples.*;
import static nl.ritense.demo.domain.ToewijzingTestSamples.*;
import static nl.ritense.demo.domain.VoorzieningTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeveringTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Levering.class);
        Levering levering1 = getLeveringSample1();
        Levering levering2 = new Levering();
        assertThat(levering1).isNotEqualTo(levering2);

        levering2.setId(levering1.getId());
        assertThat(levering1).isEqualTo(levering2);

        levering2 = getLeveringSample2();
        assertThat(levering1).isNotEqualTo(levering2);
    }

    @Test
    void geleverdeprestatieBeschikkingTest() throws Exception {
        Levering levering = getLeveringRandomSampleGenerator();
        Beschikking beschikkingBack = getBeschikkingRandomSampleGenerator();

        levering.setGeleverdeprestatieBeschikking(beschikkingBack);
        assertThat(levering.getGeleverdeprestatieBeschikking()).isEqualTo(beschikkingBack);

        levering.geleverdeprestatieBeschikking(null);
        assertThat(levering.getGeleverdeprestatieBeschikking()).isNull();
    }

    @Test
    void prestatievoorClientTest() throws Exception {
        Levering levering = getLeveringRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        levering.setPrestatievoorClient(clientBack);
        assertThat(levering.getPrestatievoorClient()).isEqualTo(clientBack);

        levering.prestatievoorClient(null);
        assertThat(levering.getPrestatievoorClient()).isNull();
    }

    @Test
    void geleverdezorgToewijzingTest() throws Exception {
        Levering levering = getLeveringRandomSampleGenerator();
        Toewijzing toewijzingBack = getToewijzingRandomSampleGenerator();

        levering.setGeleverdezorgToewijzing(toewijzingBack);
        assertThat(levering.getGeleverdezorgToewijzing()).isEqualTo(toewijzingBack);

        levering.geleverdezorgToewijzing(null);
        assertThat(levering.getGeleverdezorgToewijzing()).isNull();
    }

    @Test
    void voorzieningVoorzieningTest() throws Exception {
        Levering levering = getLeveringRandomSampleGenerator();
        Voorziening voorzieningBack = getVoorzieningRandomSampleGenerator();

        levering.setVoorzieningVoorziening(voorzieningBack);
        assertThat(levering.getVoorzieningVoorziening()).isEqualTo(voorzieningBack);

        levering.voorzieningVoorziening(null);
        assertThat(levering.getVoorzieningVoorziening()).isNull();
    }

    @Test
    void leverdeprestatieLeverancierTest() throws Exception {
        Levering levering = getLeveringRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        levering.setLeverdeprestatieLeverancier(leverancierBack);
        assertThat(levering.getLeverdeprestatieLeverancier()).isEqualTo(leverancierBack);

        levering.leverdeprestatieLeverancier(null);
        assertThat(levering.getLeverdeprestatieLeverancier()).isNull();
    }
}
