package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.HeffingTestSamples.*;
import static nl.ritense.demo.domain.HeffinggrondslagTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HeffingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Heffing.class);
        Heffing heffing1 = getHeffingSample1();
        Heffing heffing2 = new Heffing();
        assertThat(heffing1).isNotEqualTo(heffing2);

        heffing2.setId(heffing1.getId());
        assertThat(heffing1).isEqualTo(heffing2);

        heffing2 = getHeffingSample2();
        assertThat(heffing1).isNotEqualTo(heffing2);
    }

    @Test
    void heeftgrondslagHeffinggrondslagTest() throws Exception {
        Heffing heffing = getHeffingRandomSampleGenerator();
        Heffinggrondslag heffinggrondslagBack = getHeffinggrondslagRandomSampleGenerator();

        heffing.setHeeftgrondslagHeffinggrondslag(heffinggrondslagBack);
        assertThat(heffing.getHeeftgrondslagHeffinggrondslag()).isEqualTo(heffinggrondslagBack);

        heffing.heeftgrondslagHeffinggrondslag(null);
        assertThat(heffing.getHeeftgrondslagHeffinggrondslag()).isNull();
    }

    @Test
    void heeftZaakTest() throws Exception {
        Heffing heffing = getHeffingRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        heffing.setHeeftZaak(zaakBack);
        assertThat(heffing.getHeeftZaak()).isEqualTo(zaakBack);
        assertThat(zaakBack.getHeeftHeffing()).isEqualTo(heffing);

        heffing.heeftZaak(null);
        assertThat(heffing.getHeeftZaak()).isNull();
        assertThat(zaakBack.getHeeftHeffing()).isNull();
    }
}
