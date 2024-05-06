package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SchoolTestSamples.*;
import static nl.ritense.demo.domain.SportlocatieTestSamples.*;
import static nl.ritense.demo.domain.SportverenigingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SportlocatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sportlocatie.class);
        Sportlocatie sportlocatie1 = getSportlocatieSample1();
        Sportlocatie sportlocatie2 = new Sportlocatie();
        assertThat(sportlocatie1).isNotEqualTo(sportlocatie2);

        sportlocatie2.setId(sportlocatie1.getId());
        assertThat(sportlocatie1).isEqualTo(sportlocatie2);

        sportlocatie2 = getSportlocatieSample2();
        assertThat(sportlocatie1).isNotEqualTo(sportlocatie2);
    }

    @Test
    void gebruiktSchoolTest() throws Exception {
        Sportlocatie sportlocatie = getSportlocatieRandomSampleGenerator();
        School schoolBack = getSchoolRandomSampleGenerator();

        sportlocatie.addGebruiktSchool(schoolBack);
        assertThat(sportlocatie.getGebruiktSchools()).containsOnly(schoolBack);
        assertThat(schoolBack.getGebruiktSportlocaties()).containsOnly(sportlocatie);

        sportlocatie.removeGebruiktSchool(schoolBack);
        assertThat(sportlocatie.getGebruiktSchools()).doesNotContain(schoolBack);
        assertThat(schoolBack.getGebruiktSportlocaties()).doesNotContain(sportlocatie);

        sportlocatie.gebruiktSchools(new HashSet<>(Set.of(schoolBack)));
        assertThat(sportlocatie.getGebruiktSchools()).containsOnly(schoolBack);
        assertThat(schoolBack.getGebruiktSportlocaties()).containsOnly(sportlocatie);

        sportlocatie.setGebruiktSchools(new HashSet<>());
        assertThat(sportlocatie.getGebruiktSchools()).doesNotContain(schoolBack);
        assertThat(schoolBack.getGebruiktSportlocaties()).doesNotContain(sportlocatie);
    }

    @Test
    void gebruiktSportverenigingTest() throws Exception {
        Sportlocatie sportlocatie = getSportlocatieRandomSampleGenerator();
        Sportvereniging sportverenigingBack = getSportverenigingRandomSampleGenerator();

        sportlocatie.addGebruiktSportvereniging(sportverenigingBack);
        assertThat(sportlocatie.getGebruiktSportverenigings()).containsOnly(sportverenigingBack);
        assertThat(sportverenigingBack.getGebruiktSportlocaties()).containsOnly(sportlocatie);

        sportlocatie.removeGebruiktSportvereniging(sportverenigingBack);
        assertThat(sportlocatie.getGebruiktSportverenigings()).doesNotContain(sportverenigingBack);
        assertThat(sportverenigingBack.getGebruiktSportlocaties()).doesNotContain(sportlocatie);

        sportlocatie.gebruiktSportverenigings(new HashSet<>(Set.of(sportverenigingBack)));
        assertThat(sportlocatie.getGebruiktSportverenigings()).containsOnly(sportverenigingBack);
        assertThat(sportverenigingBack.getGebruiktSportlocaties()).containsOnly(sportlocatie);

        sportlocatie.setGebruiktSportverenigings(new HashSet<>());
        assertThat(sportlocatie.getGebruiktSportverenigings()).doesNotContain(sportverenigingBack);
        assertThat(sportverenigingBack.getGebruiktSportlocaties()).doesNotContain(sportlocatie);
    }
}
