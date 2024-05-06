package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.HoofdrekeningTestSamples.*;
import static nl.ritense.demo.domain.KostenplaatsTestSamples.*;
import static nl.ritense.demo.domain.WerkorderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WerkorderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Werkorder.class);
        Werkorder werkorder1 = getWerkorderSample1();
        Werkorder werkorder2 = new Werkorder();
        assertThat(werkorder1).isNotEqualTo(werkorder2);

        werkorder2.setId(werkorder1.getId());
        assertThat(werkorder1).isEqualTo(werkorder2);

        werkorder2 = getWerkorderSample2();
        assertThat(werkorder1).isNotEqualTo(werkorder2);
    }

    @Test
    void heeftHoofdrekeningTest() throws Exception {
        Werkorder werkorder = getWerkorderRandomSampleGenerator();
        Hoofdrekening hoofdrekeningBack = getHoofdrekeningRandomSampleGenerator();

        werkorder.setHeeftHoofdrekening(hoofdrekeningBack);
        assertThat(werkorder.getHeeftHoofdrekening()).isEqualTo(hoofdrekeningBack);

        werkorder.heeftHoofdrekening(null);
        assertThat(werkorder.getHeeftHoofdrekening()).isNull();
    }

    @Test
    void heeftKostenplaatsTest() throws Exception {
        Werkorder werkorder = getWerkorderRandomSampleGenerator();
        Kostenplaats kostenplaatsBack = getKostenplaatsRandomSampleGenerator();

        werkorder.setHeeftKostenplaats(kostenplaatsBack);
        assertThat(werkorder.getHeeftKostenplaats()).isEqualTo(kostenplaatsBack);

        werkorder.heeftKostenplaats(null);
        assertThat(werkorder.getHeeftKostenplaats()).isNull();
    }
}
