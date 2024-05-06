package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AgendapuntTestSamples.*;
import static nl.ritense.demo.domain.RaadsstukTestSamples.*;
import static nl.ritense.demo.domain.StemmingTestSamples.*;
import static nl.ritense.demo.domain.VergaderingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AgendapuntTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Agendapunt.class);
        Agendapunt agendapunt1 = getAgendapuntSample1();
        Agendapunt agendapunt2 = new Agendapunt();
        assertThat(agendapunt1).isNotEqualTo(agendapunt2);

        agendapunt2.setId(agendapunt1.getId());
        assertThat(agendapunt1).isEqualTo(agendapunt2);

        agendapunt2 = getAgendapuntSample2();
        assertThat(agendapunt1).isNotEqualTo(agendapunt2);
    }

    @Test
    void heeftVergaderingTest() throws Exception {
        Agendapunt agendapunt = getAgendapuntRandomSampleGenerator();
        Vergadering vergaderingBack = getVergaderingRandomSampleGenerator();

        agendapunt.setHeeftVergadering(vergaderingBack);
        assertThat(agendapunt.getHeeftVergadering()).isEqualTo(vergaderingBack);

        agendapunt.heeftVergadering(null);
        assertThat(agendapunt.getHeeftVergadering()).isNull();
    }

    @Test
    void hoortbijStemmingTest() throws Exception {
        Agendapunt agendapunt = getAgendapuntRandomSampleGenerator();
        Stemming stemmingBack = getStemmingRandomSampleGenerator();

        agendapunt.addHoortbijStemming(stemmingBack);
        assertThat(agendapunt.getHoortbijStemmings()).containsOnly(stemmingBack);
        assertThat(stemmingBack.getHoortbijAgendapunt()).isEqualTo(agendapunt);

        agendapunt.removeHoortbijStemming(stemmingBack);
        assertThat(agendapunt.getHoortbijStemmings()).doesNotContain(stemmingBack);
        assertThat(stemmingBack.getHoortbijAgendapunt()).isNull();

        agendapunt.hoortbijStemmings(new HashSet<>(Set.of(stemmingBack)));
        assertThat(agendapunt.getHoortbijStemmings()).containsOnly(stemmingBack);
        assertThat(stemmingBack.getHoortbijAgendapunt()).isEqualTo(agendapunt);

        agendapunt.setHoortbijStemmings(new HashSet<>());
        assertThat(agendapunt.getHoortbijStemmings()).doesNotContain(stemmingBack);
        assertThat(stemmingBack.getHoortbijAgendapunt()).isNull();
    }

    @Test
    void behandeltRaadsstukTest() throws Exception {
        Agendapunt agendapunt = getAgendapuntRandomSampleGenerator();
        Raadsstuk raadsstukBack = getRaadsstukRandomSampleGenerator();

        agendapunt.addBehandeltRaadsstuk(raadsstukBack);
        assertThat(agendapunt.getBehandeltRaadsstuks()).containsOnly(raadsstukBack);
        assertThat(raadsstukBack.getBehandeltAgendapunts()).containsOnly(agendapunt);

        agendapunt.removeBehandeltRaadsstuk(raadsstukBack);
        assertThat(agendapunt.getBehandeltRaadsstuks()).doesNotContain(raadsstukBack);
        assertThat(raadsstukBack.getBehandeltAgendapunts()).doesNotContain(agendapunt);

        agendapunt.behandeltRaadsstuks(new HashSet<>(Set.of(raadsstukBack)));
        assertThat(agendapunt.getBehandeltRaadsstuks()).containsOnly(raadsstukBack);
        assertThat(raadsstukBack.getBehandeltAgendapunts()).containsOnly(agendapunt);

        agendapunt.setBehandeltRaadsstuks(new HashSet<>());
        assertThat(agendapunt.getBehandeltRaadsstuks()).doesNotContain(raadsstukBack);
        assertThat(raadsstukBack.getBehandeltAgendapunts()).doesNotContain(agendapunt);
    }
}
