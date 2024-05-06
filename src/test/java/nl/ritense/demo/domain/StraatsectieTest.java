package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ParkeervlakTestSamples.*;
import static nl.ritense.demo.domain.StraatsectieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StraatsectieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Straatsectie.class);
        Straatsectie straatsectie1 = getStraatsectieSample1();
        Straatsectie straatsectie2 = new Straatsectie();
        assertThat(straatsectie1).isNotEqualTo(straatsectie2);

        straatsectie2.setId(straatsectie1.getId());
        assertThat(straatsectie1).isEqualTo(straatsectie2);

        straatsectie2 = getStraatsectieSample2();
        assertThat(straatsectie1).isNotEqualTo(straatsectie2);
    }

    @Test
    void bevatParkeervlakTest() throws Exception {
        Straatsectie straatsectie = getStraatsectieRandomSampleGenerator();
        Parkeervlak parkeervlakBack = getParkeervlakRandomSampleGenerator();

        straatsectie.addBevatParkeervlak(parkeervlakBack);
        assertThat(straatsectie.getBevatParkeervlaks()).containsOnly(parkeervlakBack);
        assertThat(parkeervlakBack.getBevatStraatsectie()).isEqualTo(straatsectie);

        straatsectie.removeBevatParkeervlak(parkeervlakBack);
        assertThat(straatsectie.getBevatParkeervlaks()).doesNotContain(parkeervlakBack);
        assertThat(parkeervlakBack.getBevatStraatsectie()).isNull();

        straatsectie.bevatParkeervlaks(new HashSet<>(Set.of(parkeervlakBack)));
        assertThat(straatsectie.getBevatParkeervlaks()).containsOnly(parkeervlakBack);
        assertThat(parkeervlakBack.getBevatStraatsectie()).isEqualTo(straatsectie);

        straatsectie.setBevatParkeervlaks(new HashSet<>());
        assertThat(straatsectie.getBevatParkeervlaks()).doesNotContain(parkeervlakBack);
        assertThat(parkeervlakBack.getBevatStraatsectie()).isNull();
    }
}
