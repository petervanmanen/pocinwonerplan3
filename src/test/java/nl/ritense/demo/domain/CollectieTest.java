package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.CollectieTestSamples.*;
import static nl.ritense.demo.domain.MuseumobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CollectieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Collectie.class);
        Collectie collectie1 = getCollectieSample1();
        Collectie collectie2 = new Collectie();
        assertThat(collectie1).isNotEqualTo(collectie2);

        collectie2.setId(collectie1.getId());
        assertThat(collectie1).isEqualTo(collectie2);

        collectie2 = getCollectieSample2();
        assertThat(collectie1).isNotEqualTo(collectie2);
    }

    @Test
    void bevatMuseumobjectTest() throws Exception {
        Collectie collectie = getCollectieRandomSampleGenerator();
        Museumobject museumobjectBack = getMuseumobjectRandomSampleGenerator();

        collectie.addBevatMuseumobject(museumobjectBack);
        assertThat(collectie.getBevatMuseumobjects()).containsOnly(museumobjectBack);

        collectie.removeBevatMuseumobject(museumobjectBack);
        assertThat(collectie.getBevatMuseumobjects()).doesNotContain(museumobjectBack);

        collectie.bevatMuseumobjects(new HashSet<>(Set.of(museumobjectBack)));
        assertThat(collectie.getBevatMuseumobjects()).containsOnly(museumobjectBack);

        collectie.setBevatMuseumobjects(new HashSet<>());
        assertThat(collectie.getBevatMuseumobjects()).doesNotContain(museumobjectBack);
    }
}
