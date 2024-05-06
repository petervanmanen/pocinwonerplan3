package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BruikleenTestSamples.*;
import static nl.ritense.demo.domain.LenerTestSamples.*;
import static nl.ritense.demo.domain.MuseumobjectTestSamples.*;
import static nl.ritense.demo.domain.TentoonstellingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BruikleenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bruikleen.class);
        Bruikleen bruikleen1 = getBruikleenSample1();
        Bruikleen bruikleen2 = new Bruikleen();
        assertThat(bruikleen1).isNotEqualTo(bruikleen2);

        bruikleen2.setId(bruikleen1.getId());
        assertThat(bruikleen1).isEqualTo(bruikleen2);

        bruikleen2 = getBruikleenSample2();
        assertThat(bruikleen1).isNotEqualTo(bruikleen2);
    }

    @Test
    void isbedoeldvoorTentoonstellingTest() throws Exception {
        Bruikleen bruikleen = getBruikleenRandomSampleGenerator();
        Tentoonstelling tentoonstellingBack = getTentoonstellingRandomSampleGenerator();

        bruikleen.addIsbedoeldvoorTentoonstelling(tentoonstellingBack);
        assertThat(bruikleen.getIsbedoeldvoorTentoonstellings()).containsOnly(tentoonstellingBack);

        bruikleen.removeIsbedoeldvoorTentoonstelling(tentoonstellingBack);
        assertThat(bruikleen.getIsbedoeldvoorTentoonstellings()).doesNotContain(tentoonstellingBack);

        bruikleen.isbedoeldvoorTentoonstellings(new HashSet<>(Set.of(tentoonstellingBack)));
        assertThat(bruikleen.getIsbedoeldvoorTentoonstellings()).containsOnly(tentoonstellingBack);

        bruikleen.setIsbedoeldvoorTentoonstellings(new HashSet<>());
        assertThat(bruikleen.getIsbedoeldvoorTentoonstellings()).doesNotContain(tentoonstellingBack);
    }

    @Test
    void betreftMuseumobjectTest() throws Exception {
        Bruikleen bruikleen = getBruikleenRandomSampleGenerator();
        Museumobject museumobjectBack = getMuseumobjectRandomSampleGenerator();

        bruikleen.addBetreftMuseumobject(museumobjectBack);
        assertThat(bruikleen.getBetreftMuseumobjects()).containsOnly(museumobjectBack);
        assertThat(museumobjectBack.getBetreftBruikleen()).isEqualTo(bruikleen);

        bruikleen.removeBetreftMuseumobject(museumobjectBack);
        assertThat(bruikleen.getBetreftMuseumobjects()).doesNotContain(museumobjectBack);
        assertThat(museumobjectBack.getBetreftBruikleen()).isNull();

        bruikleen.betreftMuseumobjects(new HashSet<>(Set.of(museumobjectBack)));
        assertThat(bruikleen.getBetreftMuseumobjects()).containsOnly(museumobjectBack);
        assertThat(museumobjectBack.getBetreftBruikleen()).isEqualTo(bruikleen);

        bruikleen.setBetreftMuseumobjects(new HashSet<>());
        assertThat(bruikleen.getBetreftMuseumobjects()).doesNotContain(museumobjectBack);
        assertThat(museumobjectBack.getBetreftBruikleen()).isNull();
    }

    @Test
    void isLenerTest() throws Exception {
        Bruikleen bruikleen = getBruikleenRandomSampleGenerator();
        Lener lenerBack = getLenerRandomSampleGenerator();

        bruikleen.addIsLener(lenerBack);
        assertThat(bruikleen.getIsLeners()).containsOnly(lenerBack);
        assertThat(lenerBack.getIsBruikleens()).containsOnly(bruikleen);

        bruikleen.removeIsLener(lenerBack);
        assertThat(bruikleen.getIsLeners()).doesNotContain(lenerBack);
        assertThat(lenerBack.getIsBruikleens()).doesNotContain(bruikleen);

        bruikleen.isLeners(new HashSet<>(Set.of(lenerBack)));
        assertThat(bruikleen.getIsLeners()).containsOnly(lenerBack);
        assertThat(lenerBack.getIsBruikleens()).containsOnly(bruikleen);

        bruikleen.setIsLeners(new HashSet<>());
        assertThat(bruikleen.getIsLeners()).doesNotContain(lenerBack);
        assertThat(lenerBack.getIsBruikleens()).doesNotContain(bruikleen);
    }
}
