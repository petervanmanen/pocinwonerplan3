package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ActiviteitTestSamples.*;
import static nl.ritense.demo.domain.ActiviteitsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActiviteitsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Activiteitsoort.class);
        Activiteitsoort activiteitsoort1 = getActiviteitsoortSample1();
        Activiteitsoort activiteitsoort2 = new Activiteitsoort();
        assertThat(activiteitsoort1).isNotEqualTo(activiteitsoort2);

        activiteitsoort2.setId(activiteitsoort1.getId());
        assertThat(activiteitsoort1).isEqualTo(activiteitsoort2);

        activiteitsoort2 = getActiviteitsoortSample2();
        assertThat(activiteitsoort1).isNotEqualTo(activiteitsoort2);
    }

    @Test
    void vansoortActiviteitTest() throws Exception {
        Activiteitsoort activiteitsoort = getActiviteitsoortRandomSampleGenerator();
        Activiteit activiteitBack = getActiviteitRandomSampleGenerator();

        activiteitsoort.addVansoortActiviteit(activiteitBack);
        assertThat(activiteitsoort.getVansoortActiviteits()).containsOnly(activiteitBack);
        assertThat(activiteitBack.getVansoortActiviteitsoort()).isEqualTo(activiteitsoort);

        activiteitsoort.removeVansoortActiviteit(activiteitBack);
        assertThat(activiteitsoort.getVansoortActiviteits()).doesNotContain(activiteitBack);
        assertThat(activiteitBack.getVansoortActiviteitsoort()).isNull();

        activiteitsoort.vansoortActiviteits(new HashSet<>(Set.of(activiteitBack)));
        assertThat(activiteitsoort.getVansoortActiviteits()).containsOnly(activiteitBack);
        assertThat(activiteitBack.getVansoortActiviteitsoort()).isEqualTo(activiteitsoort);

        activiteitsoort.setVansoortActiviteits(new HashSet<>());
        assertThat(activiteitsoort.getVansoortActiviteits()).doesNotContain(activiteitBack);
        assertThat(activiteitBack.getVansoortActiviteitsoort()).isNull();
    }
}
