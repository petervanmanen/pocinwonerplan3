package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BruikleenTestSamples.*;
import static nl.ritense.demo.domain.MuseumobjectTestSamples.*;
import static nl.ritense.demo.domain.RondleidingTestSamples.*;
import static nl.ritense.demo.domain.SamenstellerTestSamples.*;
import static nl.ritense.demo.domain.TentoonstellingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TentoonstellingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tentoonstelling.class);
        Tentoonstelling tentoonstelling1 = getTentoonstellingSample1();
        Tentoonstelling tentoonstelling2 = new Tentoonstelling();
        assertThat(tentoonstelling1).isNotEqualTo(tentoonstelling2);

        tentoonstelling2.setId(tentoonstelling1.getId());
        assertThat(tentoonstelling1).isEqualTo(tentoonstelling2);

        tentoonstelling2 = getTentoonstellingSample2();
        assertThat(tentoonstelling1).isNotEqualTo(tentoonstelling2);
    }

    @Test
    void voorRondleidingTest() throws Exception {
        Tentoonstelling tentoonstelling = getTentoonstellingRandomSampleGenerator();
        Rondleiding rondleidingBack = getRondleidingRandomSampleGenerator();

        tentoonstelling.addVoorRondleiding(rondleidingBack);
        assertThat(tentoonstelling.getVoorRondleidings()).containsOnly(rondleidingBack);
        assertThat(rondleidingBack.getVoorTentoonstelling()).isEqualTo(tentoonstelling);

        tentoonstelling.removeVoorRondleiding(rondleidingBack);
        assertThat(tentoonstelling.getVoorRondleidings()).doesNotContain(rondleidingBack);
        assertThat(rondleidingBack.getVoorTentoonstelling()).isNull();

        tentoonstelling.voorRondleidings(new HashSet<>(Set.of(rondleidingBack)));
        assertThat(tentoonstelling.getVoorRondleidings()).containsOnly(rondleidingBack);
        assertThat(rondleidingBack.getVoorTentoonstelling()).isEqualTo(tentoonstelling);

        tentoonstelling.setVoorRondleidings(new HashSet<>());
        assertThat(tentoonstelling.getVoorRondleidings()).doesNotContain(rondleidingBack);
        assertThat(rondleidingBack.getVoorTentoonstelling()).isNull();
    }

    @Test
    void isbedoeldvoorBruikleenTest() throws Exception {
        Tentoonstelling tentoonstelling = getTentoonstellingRandomSampleGenerator();
        Bruikleen bruikleenBack = getBruikleenRandomSampleGenerator();

        tentoonstelling.addIsbedoeldvoorBruikleen(bruikleenBack);
        assertThat(tentoonstelling.getIsbedoeldvoorBruikleens()).containsOnly(bruikleenBack);
        assertThat(bruikleenBack.getIsbedoeldvoorTentoonstellings()).containsOnly(tentoonstelling);

        tentoonstelling.removeIsbedoeldvoorBruikleen(bruikleenBack);
        assertThat(tentoonstelling.getIsbedoeldvoorBruikleens()).doesNotContain(bruikleenBack);
        assertThat(bruikleenBack.getIsbedoeldvoorTentoonstellings()).doesNotContain(tentoonstelling);

        tentoonstelling.isbedoeldvoorBruikleens(new HashSet<>(Set.of(bruikleenBack)));
        assertThat(tentoonstelling.getIsbedoeldvoorBruikleens()).containsOnly(bruikleenBack);
        assertThat(bruikleenBack.getIsbedoeldvoorTentoonstellings()).containsOnly(tentoonstelling);

        tentoonstelling.setIsbedoeldvoorBruikleens(new HashSet<>());
        assertThat(tentoonstelling.getIsbedoeldvoorBruikleens()).doesNotContain(bruikleenBack);
        assertThat(bruikleenBack.getIsbedoeldvoorTentoonstellings()).doesNotContain(tentoonstelling);
    }

    @Test
    void onderdeelMuseumobjectTest() throws Exception {
        Tentoonstelling tentoonstelling = getTentoonstellingRandomSampleGenerator();
        Museumobject museumobjectBack = getMuseumobjectRandomSampleGenerator();

        tentoonstelling.addOnderdeelMuseumobject(museumobjectBack);
        assertThat(tentoonstelling.getOnderdeelMuseumobjects()).containsOnly(museumobjectBack);
        assertThat(museumobjectBack.getOnderdeelTentoonstellings()).containsOnly(tentoonstelling);

        tentoonstelling.removeOnderdeelMuseumobject(museumobjectBack);
        assertThat(tentoonstelling.getOnderdeelMuseumobjects()).doesNotContain(museumobjectBack);
        assertThat(museumobjectBack.getOnderdeelTentoonstellings()).doesNotContain(tentoonstelling);

        tentoonstelling.onderdeelMuseumobjects(new HashSet<>(Set.of(museumobjectBack)));
        assertThat(tentoonstelling.getOnderdeelMuseumobjects()).containsOnly(museumobjectBack);
        assertThat(museumobjectBack.getOnderdeelTentoonstellings()).containsOnly(tentoonstelling);

        tentoonstelling.setOnderdeelMuseumobjects(new HashSet<>());
        assertThat(tentoonstelling.getOnderdeelMuseumobjects()).doesNotContain(museumobjectBack);
        assertThat(museumobjectBack.getOnderdeelTentoonstellings()).doesNotContain(tentoonstelling);
    }

    @Test
    void steltsamenSamenstellerTest() throws Exception {
        Tentoonstelling tentoonstelling = getTentoonstellingRandomSampleGenerator();
        Samensteller samenstellerBack = getSamenstellerRandomSampleGenerator();

        tentoonstelling.addSteltsamenSamensteller(samenstellerBack);
        assertThat(tentoonstelling.getSteltsamenSamenstellers()).containsOnly(samenstellerBack);
        assertThat(samenstellerBack.getSteltsamenTentoonstellings()).containsOnly(tentoonstelling);

        tentoonstelling.removeSteltsamenSamensteller(samenstellerBack);
        assertThat(tentoonstelling.getSteltsamenSamenstellers()).doesNotContain(samenstellerBack);
        assertThat(samenstellerBack.getSteltsamenTentoonstellings()).doesNotContain(tentoonstelling);

        tentoonstelling.steltsamenSamenstellers(new HashSet<>(Set.of(samenstellerBack)));
        assertThat(tentoonstelling.getSteltsamenSamenstellers()).containsOnly(samenstellerBack);
        assertThat(samenstellerBack.getSteltsamenTentoonstellings()).containsOnly(tentoonstelling);

        tentoonstelling.setSteltsamenSamenstellers(new HashSet<>());
        assertThat(tentoonstelling.getSteltsamenSamenstellers()).doesNotContain(samenstellerBack);
        assertThat(samenstellerBack.getSteltsamenTentoonstellings()).doesNotContain(tentoonstelling);
    }
}
