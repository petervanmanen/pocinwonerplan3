package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.HeffinggrondslagTestSamples.*;
import static nl.ritense.demo.domain.HeffingsverordeningTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HeffingsverordeningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Heffingsverordening.class);
        Heffingsverordening heffingsverordening1 = getHeffingsverordeningSample1();
        Heffingsverordening heffingsverordening2 = new Heffingsverordening();
        assertThat(heffingsverordening1).isNotEqualTo(heffingsverordening2);

        heffingsverordening2.setId(heffingsverordening1.getId());
        assertThat(heffingsverordening1).isEqualTo(heffingsverordening2);

        heffingsverordening2 = getHeffingsverordeningSample2();
        assertThat(heffingsverordening1).isNotEqualTo(heffingsverordening2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Heffingsverordening heffingsverordening = new Heffingsverordening();
        assertThat(heffingsverordening.hashCode()).isZero();

        Heffingsverordening heffingsverordening1 = getHeffingsverordeningSample1();
        heffingsverordening.setId(heffingsverordening1.getId());
        assertThat(heffingsverordening).hasSameHashCodeAs(heffingsverordening1);
    }

    @Test
    void vermeldinHeffinggrondslagTest() throws Exception {
        Heffingsverordening heffingsverordening = getHeffingsverordeningRandomSampleGenerator();
        Heffinggrondslag heffinggrondslagBack = getHeffinggrondslagRandomSampleGenerator();

        heffingsverordening.addVermeldinHeffinggrondslag(heffinggrondslagBack);
        assertThat(heffingsverordening.getVermeldinHeffinggrondslags()).containsOnly(heffinggrondslagBack);
        assertThat(heffinggrondslagBack.getVermeldinHeffingsverordening()).isEqualTo(heffingsverordening);

        heffingsverordening.removeVermeldinHeffinggrondslag(heffinggrondslagBack);
        assertThat(heffingsverordening.getVermeldinHeffinggrondslags()).doesNotContain(heffinggrondslagBack);
        assertThat(heffinggrondslagBack.getVermeldinHeffingsverordening()).isNull();

        heffingsverordening.vermeldinHeffinggrondslags(new HashSet<>(Set.of(heffinggrondslagBack)));
        assertThat(heffingsverordening.getVermeldinHeffinggrondslags()).containsOnly(heffinggrondslagBack);
        assertThat(heffinggrondslagBack.getVermeldinHeffingsverordening()).isEqualTo(heffingsverordening);

        heffingsverordening.setVermeldinHeffinggrondslags(new HashSet<>());
        assertThat(heffingsverordening.getVermeldinHeffinggrondslags()).doesNotContain(heffinggrondslagBack);
        assertThat(heffinggrondslagBack.getVermeldinHeffingsverordening()).isNull();
    }
}
