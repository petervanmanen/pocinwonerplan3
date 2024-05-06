package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerzuimTestSamples.*;
import static nl.ritense.demo.domain.VerzuimsoortTestSamples.*;
import static nl.ritense.demo.domain.WerknemerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerzuimTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verzuim.class);
        Verzuim verzuim1 = getVerzuimSample1();
        Verzuim verzuim2 = new Verzuim();
        assertThat(verzuim1).isNotEqualTo(verzuim2);

        verzuim2.setId(verzuim1.getId());
        assertThat(verzuim1).isEqualTo(verzuim2);

        verzuim2 = getVerzuimSample2();
        assertThat(verzuim1).isNotEqualTo(verzuim2);
    }

    @Test
    void soortverzuimVerzuimsoortTest() throws Exception {
        Verzuim verzuim = getVerzuimRandomSampleGenerator();
        Verzuimsoort verzuimsoortBack = getVerzuimsoortRandomSampleGenerator();

        verzuim.setSoortverzuimVerzuimsoort(verzuimsoortBack);
        assertThat(verzuim.getSoortverzuimVerzuimsoort()).isEqualTo(verzuimsoortBack);

        verzuim.soortverzuimVerzuimsoort(null);
        assertThat(verzuim.getSoortverzuimVerzuimsoort()).isNull();
    }

    @Test
    void heeftverzuimWerknemerTest() throws Exception {
        Verzuim verzuim = getVerzuimRandomSampleGenerator();
        Werknemer werknemerBack = getWerknemerRandomSampleGenerator();

        verzuim.setHeeftverzuimWerknemer(werknemerBack);
        assertThat(verzuim.getHeeftverzuimWerknemer()).isEqualTo(werknemerBack);

        verzuim.heeftverzuimWerknemer(null);
        assertThat(verzuim.getHeeftverzuimWerknemer()).isNull();
    }
}
