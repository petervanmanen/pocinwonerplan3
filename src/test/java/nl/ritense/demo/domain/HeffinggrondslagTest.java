package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.HeffingTestSamples.*;
import static nl.ritense.demo.domain.HeffinggrondslagTestSamples.*;
import static nl.ritense.demo.domain.HeffingsverordeningTestSamples.*;
import static nl.ritense.demo.domain.ZaaktypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HeffinggrondslagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Heffinggrondslag.class);
        Heffinggrondslag heffinggrondslag1 = getHeffinggrondslagSample1();
        Heffinggrondslag heffinggrondslag2 = new Heffinggrondslag();
        assertThat(heffinggrondslag1).isNotEqualTo(heffinggrondslag2);

        heffinggrondslag2.setId(heffinggrondslag1.getId());
        assertThat(heffinggrondslag1).isEqualTo(heffinggrondslag2);

        heffinggrondslag2 = getHeffinggrondslagSample2();
        assertThat(heffinggrondslag1).isNotEqualTo(heffinggrondslag2);
    }

    @Test
    void vermeldinHeffingsverordeningTest() throws Exception {
        Heffinggrondslag heffinggrondslag = getHeffinggrondslagRandomSampleGenerator();
        Heffingsverordening heffingsverordeningBack = getHeffingsverordeningRandomSampleGenerator();

        heffinggrondslag.setVermeldinHeffingsverordening(heffingsverordeningBack);
        assertThat(heffinggrondslag.getVermeldinHeffingsverordening()).isEqualTo(heffingsverordeningBack);

        heffinggrondslag.vermeldinHeffingsverordening(null);
        assertThat(heffinggrondslag.getVermeldinHeffingsverordening()).isNull();
    }

    @Test
    void heeftZaaktypeTest() throws Exception {
        Heffinggrondslag heffinggrondslag = getHeffinggrondslagRandomSampleGenerator();
        Zaaktype zaaktypeBack = getZaaktypeRandomSampleGenerator();

        heffinggrondslag.setHeeftZaaktype(zaaktypeBack);
        assertThat(heffinggrondslag.getHeeftZaaktype()).isEqualTo(zaaktypeBack);

        heffinggrondslag.heeftZaaktype(null);
        assertThat(heffinggrondslag.getHeeftZaaktype()).isNull();
    }

    @Test
    void heeftgrondslagHeffingTest() throws Exception {
        Heffinggrondslag heffinggrondslag = getHeffinggrondslagRandomSampleGenerator();
        Heffing heffingBack = getHeffingRandomSampleGenerator();

        heffinggrondslag.addHeeftgrondslagHeffing(heffingBack);
        assertThat(heffinggrondslag.getHeeftgrondslagHeffings()).containsOnly(heffingBack);
        assertThat(heffingBack.getHeeftgrondslagHeffinggrondslag()).isEqualTo(heffinggrondslag);

        heffinggrondslag.removeHeeftgrondslagHeffing(heffingBack);
        assertThat(heffinggrondslag.getHeeftgrondslagHeffings()).doesNotContain(heffingBack);
        assertThat(heffingBack.getHeeftgrondslagHeffinggrondslag()).isNull();

        heffinggrondslag.heeftgrondslagHeffings(new HashSet<>(Set.of(heffingBack)));
        assertThat(heffinggrondslag.getHeeftgrondslagHeffings()).containsOnly(heffingBack);
        assertThat(heffingBack.getHeeftgrondslagHeffinggrondslag()).isEqualTo(heffinggrondslag);

        heffinggrondslag.setHeeftgrondslagHeffings(new HashSet<>());
        assertThat(heffinggrondslag.getHeeftgrondslagHeffings()).doesNotContain(heffingBack);
        assertThat(heffingBack.getHeeftgrondslagHeffinggrondslag()).isNull();
    }
}
