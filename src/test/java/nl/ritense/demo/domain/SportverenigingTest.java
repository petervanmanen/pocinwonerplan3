package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SportTestSamples.*;
import static nl.ritense.demo.domain.SportlocatieTestSamples.*;
import static nl.ritense.demo.domain.SportverenigingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SportverenigingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sportvereniging.class);
        Sportvereniging sportvereniging1 = getSportverenigingSample1();
        Sportvereniging sportvereniging2 = new Sportvereniging();
        assertThat(sportvereniging1).isNotEqualTo(sportvereniging2);

        sportvereniging2.setId(sportvereniging1.getId());
        assertThat(sportvereniging1).isEqualTo(sportvereniging2);

        sportvereniging2 = getSportverenigingSample2();
        assertThat(sportvereniging1).isNotEqualTo(sportvereniging2);
    }

    @Test
    void oefentuitSportTest() throws Exception {
        Sportvereniging sportvereniging = getSportverenigingRandomSampleGenerator();
        Sport sportBack = getSportRandomSampleGenerator();

        sportvereniging.addOefentuitSport(sportBack);
        assertThat(sportvereniging.getOefentuitSports()).containsOnly(sportBack);

        sportvereniging.removeOefentuitSport(sportBack);
        assertThat(sportvereniging.getOefentuitSports()).doesNotContain(sportBack);

        sportvereniging.oefentuitSports(new HashSet<>(Set.of(sportBack)));
        assertThat(sportvereniging.getOefentuitSports()).containsOnly(sportBack);

        sportvereniging.setOefentuitSports(new HashSet<>());
        assertThat(sportvereniging.getOefentuitSports()).doesNotContain(sportBack);
    }

    @Test
    void gebruiktSportlocatieTest() throws Exception {
        Sportvereniging sportvereniging = getSportverenigingRandomSampleGenerator();
        Sportlocatie sportlocatieBack = getSportlocatieRandomSampleGenerator();

        sportvereniging.addGebruiktSportlocatie(sportlocatieBack);
        assertThat(sportvereniging.getGebruiktSportlocaties()).containsOnly(sportlocatieBack);

        sportvereniging.removeGebruiktSportlocatie(sportlocatieBack);
        assertThat(sportvereniging.getGebruiktSportlocaties()).doesNotContain(sportlocatieBack);

        sportvereniging.gebruiktSportlocaties(new HashSet<>(Set.of(sportlocatieBack)));
        assertThat(sportvereniging.getGebruiktSportlocaties()).containsOnly(sportlocatieBack);

        sportvereniging.setGebruiktSportlocaties(new HashSet<>());
        assertThat(sportvereniging.getGebruiktSportlocaties()).doesNotContain(sportlocatieBack);
    }
}
