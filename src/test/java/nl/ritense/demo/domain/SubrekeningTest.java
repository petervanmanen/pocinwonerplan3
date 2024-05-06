package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.HoofdrekeningTestSamples.*;
import static nl.ritense.demo.domain.KostenplaatsTestSamples.*;
import static nl.ritense.demo.domain.SubrekeningTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubrekeningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subrekening.class);
        Subrekening subrekening1 = getSubrekeningSample1();
        Subrekening subrekening2 = new Subrekening();
        assertThat(subrekening1).isNotEqualTo(subrekening2);

        subrekening2.setId(subrekening1.getId());
        assertThat(subrekening1).isEqualTo(subrekening2);

        subrekening2 = getSubrekeningSample2();
        assertThat(subrekening1).isNotEqualTo(subrekening2);
    }

    @Test
    void heeftHoofdrekeningTest() throws Exception {
        Subrekening subrekening = getSubrekeningRandomSampleGenerator();
        Hoofdrekening hoofdrekeningBack = getHoofdrekeningRandomSampleGenerator();

        subrekening.setHeeftHoofdrekening(hoofdrekeningBack);
        assertThat(subrekening.getHeeftHoofdrekening()).isEqualTo(hoofdrekeningBack);

        subrekening.heeftHoofdrekening(null);
        assertThat(subrekening.getHeeftHoofdrekening()).isNull();
    }

    @Test
    void heeftKostenplaatsTest() throws Exception {
        Subrekening subrekening = getSubrekeningRandomSampleGenerator();
        Kostenplaats kostenplaatsBack = getKostenplaatsRandomSampleGenerator();

        subrekening.setHeeftKostenplaats(kostenplaatsBack);
        assertThat(subrekening.getHeeftKostenplaats()).isEqualTo(kostenplaatsBack);

        subrekening.heeftKostenplaats(null);
        assertThat(subrekening.getHeeftKostenplaats()).isNull();
    }
}
