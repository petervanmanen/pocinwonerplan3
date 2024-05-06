package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.IncidentTestSamples.*;
import static nl.ritense.demo.domain.MuseumobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IncidentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Incident.class);
        Incident incident1 = getIncidentSample1();
        Incident incident2 = new Incident();
        assertThat(incident1).isNotEqualTo(incident2);

        incident2.setId(incident1.getId());
        assertThat(incident1).isEqualTo(incident2);

        incident2 = getIncidentSample2();
        assertThat(incident1).isNotEqualTo(incident2);
    }

    @Test
    void betreftMuseumobjectTest() throws Exception {
        Incident incident = getIncidentRandomSampleGenerator();
        Museumobject museumobjectBack = getMuseumobjectRandomSampleGenerator();

        incident.addBetreftMuseumobject(museumobjectBack);
        assertThat(incident.getBetreftMuseumobjects()).containsOnly(museumobjectBack);

        incident.removeBetreftMuseumobject(museumobjectBack);
        assertThat(incident.getBetreftMuseumobjects()).doesNotContain(museumobjectBack);

        incident.betreftMuseumobjects(new HashSet<>(Set.of(museumobjectBack)));
        assertThat(incident.getBetreftMuseumobjects()).containsOnly(museumobjectBack);

        incident.setBetreftMuseumobjects(new HashSet<>());
        assertThat(incident.getBetreftMuseumobjects()).doesNotContain(museumobjectBack);
    }
}
