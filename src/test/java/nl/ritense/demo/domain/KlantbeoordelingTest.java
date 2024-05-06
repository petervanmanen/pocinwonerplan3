package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BetrokkeneTestSamples.*;
import static nl.ritense.demo.domain.KlantbeoordelingTestSamples.*;
import static nl.ritense.demo.domain.KlantbeoordelingredenTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KlantbeoordelingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Klantbeoordeling.class);
        Klantbeoordeling klantbeoordeling1 = getKlantbeoordelingSample1();
        Klantbeoordeling klantbeoordeling2 = new Klantbeoordeling();
        assertThat(klantbeoordeling1).isNotEqualTo(klantbeoordeling2);

        klantbeoordeling2.setId(klantbeoordeling1.getId());
        assertThat(klantbeoordeling1).isEqualTo(klantbeoordeling2);

        klantbeoordeling2 = getKlantbeoordelingSample2();
        assertThat(klantbeoordeling1).isNotEqualTo(klantbeoordeling2);
    }

    @Test
    void heeftKlantbeoordelingredenTest() throws Exception {
        Klantbeoordeling klantbeoordeling = getKlantbeoordelingRandomSampleGenerator();
        Klantbeoordelingreden klantbeoordelingredenBack = getKlantbeoordelingredenRandomSampleGenerator();

        klantbeoordeling.addHeeftKlantbeoordelingreden(klantbeoordelingredenBack);
        assertThat(klantbeoordeling.getHeeftKlantbeoordelingredens()).containsOnly(klantbeoordelingredenBack);
        assertThat(klantbeoordelingredenBack.getHeeftKlantbeoordeling()).isEqualTo(klantbeoordeling);

        klantbeoordeling.removeHeeftKlantbeoordelingreden(klantbeoordelingredenBack);
        assertThat(klantbeoordeling.getHeeftKlantbeoordelingredens()).doesNotContain(klantbeoordelingredenBack);
        assertThat(klantbeoordelingredenBack.getHeeftKlantbeoordeling()).isNull();

        klantbeoordeling.heeftKlantbeoordelingredens(new HashSet<>(Set.of(klantbeoordelingredenBack)));
        assertThat(klantbeoordeling.getHeeftKlantbeoordelingredens()).containsOnly(klantbeoordelingredenBack);
        assertThat(klantbeoordelingredenBack.getHeeftKlantbeoordeling()).isEqualTo(klantbeoordeling);

        klantbeoordeling.setHeeftKlantbeoordelingredens(new HashSet<>());
        assertThat(klantbeoordeling.getHeeftKlantbeoordelingredens()).doesNotContain(klantbeoordelingredenBack);
        assertThat(klantbeoordelingredenBack.getHeeftKlantbeoordeling()).isNull();
    }

    @Test
    void heeftZaakTest() throws Exception {
        Klantbeoordeling klantbeoordeling = getKlantbeoordelingRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        klantbeoordeling.setHeeftZaak(zaakBack);
        assertThat(klantbeoordeling.getHeeftZaak()).isEqualTo(zaakBack);
        assertThat(zaakBack.getHeeftKlantbeoordeling()).isEqualTo(klantbeoordeling);

        klantbeoordeling.heeftZaak(null);
        assertThat(klantbeoordeling.getHeeftZaak()).isNull();
        assertThat(zaakBack.getHeeftKlantbeoordeling()).isNull();
    }

    @Test
    void doetBetrokkeneTest() throws Exception {
        Klantbeoordeling klantbeoordeling = getKlantbeoordelingRandomSampleGenerator();
        Betrokkene betrokkeneBack = getBetrokkeneRandomSampleGenerator();

        klantbeoordeling.setDoetBetrokkene(betrokkeneBack);
        assertThat(klantbeoordeling.getDoetBetrokkene()).isEqualTo(betrokkeneBack);

        klantbeoordeling.doetBetrokkene(null);
        assertThat(klantbeoordeling.getDoetBetrokkene()).isNull();
    }
}
