package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerlofTestSamples.*;
import static nl.ritense.demo.domain.VerlofsoortTestSamples.*;
import static nl.ritense.demo.domain.WerknemerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerlofTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verlof.class);
        Verlof verlof1 = getVerlofSample1();
        Verlof verlof2 = new Verlof();
        assertThat(verlof1).isNotEqualTo(verlof2);

        verlof2.setId(verlof1.getId());
        assertThat(verlof1).isEqualTo(verlof2);

        verlof2 = getVerlofSample2();
        assertThat(verlof1).isNotEqualTo(verlof2);
    }

    @Test
    void soortverlofVerlofsoortTest() throws Exception {
        Verlof verlof = getVerlofRandomSampleGenerator();
        Verlofsoort verlofsoortBack = getVerlofsoortRandomSampleGenerator();

        verlof.setSoortverlofVerlofsoort(verlofsoortBack);
        assertThat(verlof.getSoortverlofVerlofsoort()).isEqualTo(verlofsoortBack);

        verlof.soortverlofVerlofsoort(null);
        assertThat(verlof.getSoortverlofVerlofsoort()).isNull();
    }

    @Test
    void heeftverlofWerknemerTest() throws Exception {
        Verlof verlof = getVerlofRandomSampleGenerator();
        Werknemer werknemerBack = getWerknemerRandomSampleGenerator();

        verlof.setHeeftverlofWerknemer(werknemerBack);
        assertThat(verlof.getHeeftverlofWerknemer()).isEqualTo(werknemerBack);

        verlof.heeftverlofWerknemer(null);
        assertThat(verlof.getHeeftverlofWerknemer()).isNull();
    }
}
