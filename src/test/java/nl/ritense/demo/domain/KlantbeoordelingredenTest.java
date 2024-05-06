package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KlantbeoordelingTestSamples.*;
import static nl.ritense.demo.domain.KlantbeoordelingredenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KlantbeoordelingredenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Klantbeoordelingreden.class);
        Klantbeoordelingreden klantbeoordelingreden1 = getKlantbeoordelingredenSample1();
        Klantbeoordelingreden klantbeoordelingreden2 = new Klantbeoordelingreden();
        assertThat(klantbeoordelingreden1).isNotEqualTo(klantbeoordelingreden2);

        klantbeoordelingreden2.setId(klantbeoordelingreden1.getId());
        assertThat(klantbeoordelingreden1).isEqualTo(klantbeoordelingreden2);

        klantbeoordelingreden2 = getKlantbeoordelingredenSample2();
        assertThat(klantbeoordelingreden1).isNotEqualTo(klantbeoordelingreden2);
    }

    @Test
    void heeftKlantbeoordelingTest() throws Exception {
        Klantbeoordelingreden klantbeoordelingreden = getKlantbeoordelingredenRandomSampleGenerator();
        Klantbeoordeling klantbeoordelingBack = getKlantbeoordelingRandomSampleGenerator();

        klantbeoordelingreden.setHeeftKlantbeoordeling(klantbeoordelingBack);
        assertThat(klantbeoordelingreden.getHeeftKlantbeoordeling()).isEqualTo(klantbeoordelingBack);

        klantbeoordelingreden.heeftKlantbeoordeling(null);
        assertThat(klantbeoordelingreden.getHeeftKlantbeoordeling()).isNull();
    }
}
