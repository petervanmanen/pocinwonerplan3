package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BelanghebbendeTestSamples.*;
import static nl.ritense.demo.domain.BruikleenTestSamples.*;
import static nl.ritense.demo.domain.CollectieTestSamples.*;
import static nl.ritense.demo.domain.IncidentTestSamples.*;
import static nl.ritense.demo.domain.MuseumobjectTestSamples.*;
import static nl.ritense.demo.domain.StandplaatsTestSamples.*;
import static nl.ritense.demo.domain.TentoonstellingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MuseumobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Museumobject.class);
        Museumobject museumobject1 = getMuseumobjectSample1();
        Museumobject museumobject2 = new Museumobject();
        assertThat(museumobject1).isNotEqualTo(museumobject2);

        museumobject2.setId(museumobject1.getId());
        assertThat(museumobject1).isEqualTo(museumobject2);

        museumobject2 = getMuseumobjectSample2();
        assertThat(museumobject1).isNotEqualTo(museumobject2);
    }

    @Test
    void betreftBruikleenTest() throws Exception {
        Museumobject museumobject = getMuseumobjectRandomSampleGenerator();
        Bruikleen bruikleenBack = getBruikleenRandomSampleGenerator();

        museumobject.setBetreftBruikleen(bruikleenBack);
        assertThat(museumobject.getBetreftBruikleen()).isEqualTo(bruikleenBack);

        museumobject.betreftBruikleen(null);
        assertThat(museumobject.getBetreftBruikleen()).isNull();
    }

    @Test
    void locatieStandplaatsTest() throws Exception {
        Museumobject museumobject = getMuseumobjectRandomSampleGenerator();
        Standplaats standplaatsBack = getStandplaatsRandomSampleGenerator();

        museumobject.setLocatieStandplaats(standplaatsBack);
        assertThat(museumobject.getLocatieStandplaats()).isEqualTo(standplaatsBack);

        museumobject.locatieStandplaats(null);
        assertThat(museumobject.getLocatieStandplaats()).isNull();
    }

    @Test
    void heeftBelanghebbendeTest() throws Exception {
        Museumobject museumobject = getMuseumobjectRandomSampleGenerator();
        Belanghebbende belanghebbendeBack = getBelanghebbendeRandomSampleGenerator();

        museumobject.addHeeftBelanghebbende(belanghebbendeBack);
        assertThat(museumobject.getHeeftBelanghebbendes()).containsOnly(belanghebbendeBack);

        museumobject.removeHeeftBelanghebbende(belanghebbendeBack);
        assertThat(museumobject.getHeeftBelanghebbendes()).doesNotContain(belanghebbendeBack);

        museumobject.heeftBelanghebbendes(new HashSet<>(Set.of(belanghebbendeBack)));
        assertThat(museumobject.getHeeftBelanghebbendes()).containsOnly(belanghebbendeBack);

        museumobject.setHeeftBelanghebbendes(new HashSet<>());
        assertThat(museumobject.getHeeftBelanghebbendes()).doesNotContain(belanghebbendeBack);
    }

    @Test
    void onderdeelTentoonstellingTest() throws Exception {
        Museumobject museumobject = getMuseumobjectRandomSampleGenerator();
        Tentoonstelling tentoonstellingBack = getTentoonstellingRandomSampleGenerator();

        museumobject.addOnderdeelTentoonstelling(tentoonstellingBack);
        assertThat(museumobject.getOnderdeelTentoonstellings()).containsOnly(tentoonstellingBack);

        museumobject.removeOnderdeelTentoonstelling(tentoonstellingBack);
        assertThat(museumobject.getOnderdeelTentoonstellings()).doesNotContain(tentoonstellingBack);

        museumobject.onderdeelTentoonstellings(new HashSet<>(Set.of(tentoonstellingBack)));
        assertThat(museumobject.getOnderdeelTentoonstellings()).containsOnly(tentoonstellingBack);

        museumobject.setOnderdeelTentoonstellings(new HashSet<>());
        assertThat(museumobject.getOnderdeelTentoonstellings()).doesNotContain(tentoonstellingBack);
    }

    @Test
    void bevatCollectieTest() throws Exception {
        Museumobject museumobject = getMuseumobjectRandomSampleGenerator();
        Collectie collectieBack = getCollectieRandomSampleGenerator();

        museumobject.addBevatCollectie(collectieBack);
        assertThat(museumobject.getBevatCollecties()).containsOnly(collectieBack);
        assertThat(collectieBack.getBevatMuseumobjects()).containsOnly(museumobject);

        museumobject.removeBevatCollectie(collectieBack);
        assertThat(museumobject.getBevatCollecties()).doesNotContain(collectieBack);
        assertThat(collectieBack.getBevatMuseumobjects()).doesNotContain(museumobject);

        museumobject.bevatCollecties(new HashSet<>(Set.of(collectieBack)));
        assertThat(museumobject.getBevatCollecties()).containsOnly(collectieBack);
        assertThat(collectieBack.getBevatMuseumobjects()).containsOnly(museumobject);

        museumobject.setBevatCollecties(new HashSet<>());
        assertThat(museumobject.getBevatCollecties()).doesNotContain(collectieBack);
        assertThat(collectieBack.getBevatMuseumobjects()).doesNotContain(museumobject);
    }

    @Test
    void betreftIncidentTest() throws Exception {
        Museumobject museumobject = getMuseumobjectRandomSampleGenerator();
        Incident incidentBack = getIncidentRandomSampleGenerator();

        museumobject.addBetreftIncident(incidentBack);
        assertThat(museumobject.getBetreftIncidents()).containsOnly(incidentBack);
        assertThat(incidentBack.getBetreftMuseumobjects()).containsOnly(museumobject);

        museumobject.removeBetreftIncident(incidentBack);
        assertThat(museumobject.getBetreftIncidents()).doesNotContain(incidentBack);
        assertThat(incidentBack.getBetreftMuseumobjects()).doesNotContain(museumobject);

        museumobject.betreftIncidents(new HashSet<>(Set.of(incidentBack)));
        assertThat(museumobject.getBetreftIncidents()).containsOnly(incidentBack);
        assertThat(incidentBack.getBetreftMuseumobjects()).containsOnly(museumobject);

        museumobject.setBetreftIncidents(new HashSet<>());
        assertThat(museumobject.getBetreftIncidents()).doesNotContain(incidentBack);
        assertThat(incidentBack.getBetreftMuseumobjects()).doesNotContain(museumobject);
    }
}
