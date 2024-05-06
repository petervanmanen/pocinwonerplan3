package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.RaadscommissieTestSamples.*;
import static nl.ritense.demo.domain.RaadslidTestSamples.*;
import static nl.ritense.demo.domain.VergaderingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RaadscommissieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Raadscommissie.class);
        Raadscommissie raadscommissie1 = getRaadscommissieSample1();
        Raadscommissie raadscommissie2 = new Raadscommissie();
        assertThat(raadscommissie1).isNotEqualTo(raadscommissie2);

        raadscommissie2.setId(raadscommissie1.getId());
        assertThat(raadscommissie1).isEqualTo(raadscommissie2);

        raadscommissie2 = getRaadscommissieSample2();
        assertThat(raadscommissie1).isNotEqualTo(raadscommissie2);
    }

    @Test
    void heeftVergaderingTest() throws Exception {
        Raadscommissie raadscommissie = getRaadscommissieRandomSampleGenerator();
        Vergadering vergaderingBack = getVergaderingRandomSampleGenerator();

        raadscommissie.addHeeftVergadering(vergaderingBack);
        assertThat(raadscommissie.getHeeftVergaderings()).containsOnly(vergaderingBack);
        assertThat(vergaderingBack.getHeeftRaadscommissie()).isEqualTo(raadscommissie);

        raadscommissie.removeHeeftVergadering(vergaderingBack);
        assertThat(raadscommissie.getHeeftVergaderings()).doesNotContain(vergaderingBack);
        assertThat(vergaderingBack.getHeeftRaadscommissie()).isNull();

        raadscommissie.heeftVergaderings(new HashSet<>(Set.of(vergaderingBack)));
        assertThat(raadscommissie.getHeeftVergaderings()).containsOnly(vergaderingBack);
        assertThat(vergaderingBack.getHeeftRaadscommissie()).isEqualTo(raadscommissie);

        raadscommissie.setHeeftVergaderings(new HashSet<>());
        assertThat(raadscommissie.getHeeftVergaderings()).doesNotContain(vergaderingBack);
        assertThat(vergaderingBack.getHeeftRaadscommissie()).isNull();
    }

    @Test
    void islidvanRaadslidTest() throws Exception {
        Raadscommissie raadscommissie = getRaadscommissieRandomSampleGenerator();
        Raadslid raadslidBack = getRaadslidRandomSampleGenerator();

        raadscommissie.addIslidvanRaadslid(raadslidBack);
        assertThat(raadscommissie.getIslidvanRaadslids()).containsOnly(raadslidBack);
        assertThat(raadslidBack.getIslidvanRaadscommissies()).containsOnly(raadscommissie);

        raadscommissie.removeIslidvanRaadslid(raadslidBack);
        assertThat(raadscommissie.getIslidvanRaadslids()).doesNotContain(raadslidBack);
        assertThat(raadslidBack.getIslidvanRaadscommissies()).doesNotContain(raadscommissie);

        raadscommissie.islidvanRaadslids(new HashSet<>(Set.of(raadslidBack)));
        assertThat(raadscommissie.getIslidvanRaadslids()).containsOnly(raadslidBack);
        assertThat(raadslidBack.getIslidvanRaadscommissies()).containsOnly(raadscommissie);

        raadscommissie.setIslidvanRaadslids(new HashSet<>());
        assertThat(raadscommissie.getIslidvanRaadslids()).doesNotContain(raadslidBack);
        assertThat(raadslidBack.getIslidvanRaadscommissies()).doesNotContain(raadscommissie);
    }
}
