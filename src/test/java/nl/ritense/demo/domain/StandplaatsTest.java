package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MuseumobjectTestSamples.*;
import static nl.ritense.demo.domain.StandplaatsTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StandplaatsTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Standplaats.class);
        Standplaats standplaats1 = getStandplaatsSample1();
        Standplaats standplaats2 = new Standplaats();
        assertThat(standplaats1).isNotEqualTo(standplaats2);

        standplaats2.setId(standplaats1.getId());
        assertThat(standplaats1).isEqualTo(standplaats2);

        standplaats2 = getStandplaatsSample2();
        assertThat(standplaats1).isNotEqualTo(standplaats2);
    }

    @Test
    void locatieMuseumobjectTest() throws Exception {
        Standplaats standplaats = getStandplaatsRandomSampleGenerator();
        Museumobject museumobjectBack = getMuseumobjectRandomSampleGenerator();

        standplaats.addLocatieMuseumobject(museumobjectBack);
        assertThat(standplaats.getLocatieMuseumobjects()).containsOnly(museumobjectBack);
        assertThat(museumobjectBack.getLocatieStandplaats()).isEqualTo(standplaats);

        standplaats.removeLocatieMuseumobject(museumobjectBack);
        assertThat(standplaats.getLocatieMuseumobjects()).doesNotContain(museumobjectBack);
        assertThat(museumobjectBack.getLocatieStandplaats()).isNull();

        standplaats.locatieMuseumobjects(new HashSet<>(Set.of(museumobjectBack)));
        assertThat(standplaats.getLocatieMuseumobjects()).containsOnly(museumobjectBack);
        assertThat(museumobjectBack.getLocatieStandplaats()).isEqualTo(standplaats);

        standplaats.setLocatieMuseumobjects(new HashSet<>());
        assertThat(standplaats.getLocatieMuseumobjects()).doesNotContain(museumobjectBack);
        assertThat(museumobjectBack.getLocatieStandplaats()).isNull();
    }
}
