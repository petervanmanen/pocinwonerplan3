package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeoordelingTestSamples.*;
import static nl.ritense.demo.domain.WerknemerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BeoordelingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beoordeling.class);
        Beoordeling beoordeling1 = getBeoordelingSample1();
        Beoordeling beoordeling2 = new Beoordeling();
        assertThat(beoordeling1).isNotEqualTo(beoordeling2);

        beoordeling2.setId(beoordeling1.getId());
        assertThat(beoordeling1).isEqualTo(beoordeling2);

        beoordeling2 = getBeoordelingSample2();
        assertThat(beoordeling1).isNotEqualTo(beoordeling2);
    }

    @Test
    void beoordeeltdoorWerknemerTest() throws Exception {
        Beoordeling beoordeling = getBeoordelingRandomSampleGenerator();
        Werknemer werknemerBack = getWerknemerRandomSampleGenerator();

        beoordeling.setBeoordeeltdoorWerknemer(werknemerBack);
        assertThat(beoordeling.getBeoordeeltdoorWerknemer()).isEqualTo(werknemerBack);

        beoordeling.beoordeeltdoorWerknemer(null);
        assertThat(beoordeling.getBeoordeeltdoorWerknemer()).isNull();
    }

    @Test
    void beoordelingvanWerknemerTest() throws Exception {
        Beoordeling beoordeling = getBeoordelingRandomSampleGenerator();
        Werknemer werknemerBack = getWerknemerRandomSampleGenerator();

        beoordeling.setBeoordelingvanWerknemer(werknemerBack);
        assertThat(beoordeling.getBeoordelingvanWerknemer()).isEqualTo(werknemerBack);

        beoordeling.beoordelingvanWerknemer(null);
        assertThat(beoordeling.getBeoordelingvanWerknemer()).isNull();
    }
}
