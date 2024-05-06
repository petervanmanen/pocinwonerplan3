package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.UitnodigingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UitnodigingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uitnodiging.class);
        Uitnodiging uitnodiging1 = getUitnodigingSample1();
        Uitnodiging uitnodiging2 = new Uitnodiging();
        assertThat(uitnodiging1).isNotEqualTo(uitnodiging2);

        uitnodiging2.setId(uitnodiging1.getId());
        assertThat(uitnodiging1).isEqualTo(uitnodiging2);

        uitnodiging2 = getUitnodigingSample2();
        assertThat(uitnodiging1).isNotEqualTo(uitnodiging2);
    }

    @Test
    void gerichtaanLeverancierTest() throws Exception {
        Uitnodiging uitnodiging = getUitnodigingRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        uitnodiging.setGerichtaanLeverancier(leverancierBack);
        assertThat(uitnodiging.getGerichtaanLeverancier()).isEqualTo(leverancierBack);

        uitnodiging.gerichtaanLeverancier(null);
        assertThat(uitnodiging.getGerichtaanLeverancier()).isNull();
    }
}
