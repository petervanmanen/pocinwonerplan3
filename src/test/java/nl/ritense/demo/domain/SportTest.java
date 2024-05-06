package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SportTestSamples.*;
import static nl.ritense.demo.domain.SportverenigingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SportTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sport.class);
        Sport sport1 = getSportSample1();
        Sport sport2 = new Sport();
        assertThat(sport1).isNotEqualTo(sport2);

        sport2.setId(sport1.getId());
        assertThat(sport1).isEqualTo(sport2);

        sport2 = getSportSample2();
        assertThat(sport1).isNotEqualTo(sport2);
    }

    @Test
    void oefentuitSportverenigingTest() throws Exception {
        Sport sport = getSportRandomSampleGenerator();
        Sportvereniging sportverenigingBack = getSportverenigingRandomSampleGenerator();

        sport.addOefentuitSportvereniging(sportverenigingBack);
        assertThat(sport.getOefentuitSportverenigings()).containsOnly(sportverenigingBack);
        assertThat(sportverenigingBack.getOefentuitSports()).containsOnly(sport);

        sport.removeOefentuitSportvereniging(sportverenigingBack);
        assertThat(sport.getOefentuitSportverenigings()).doesNotContain(sportverenigingBack);
        assertThat(sportverenigingBack.getOefentuitSports()).doesNotContain(sport);

        sport.oefentuitSportverenigings(new HashSet<>(Set.of(sportverenigingBack)));
        assertThat(sport.getOefentuitSportverenigings()).containsOnly(sportverenigingBack);
        assertThat(sportverenigingBack.getOefentuitSports()).containsOnly(sport);

        sport.setOefentuitSportverenigings(new HashSet<>());
        assertThat(sport.getOefentuitSportverenigings()).doesNotContain(sportverenigingBack);
        assertThat(sportverenigingBack.getOefentuitSports()).doesNotContain(sport);
    }
}
