package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AgendapuntTestSamples.*;
import static nl.ritense.demo.domain.RaadsstukTestSamples.*;
import static nl.ritense.demo.domain.StemmingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StemmingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stemming.class);
        Stemming stemming1 = getStemmingSample1();
        Stemming stemming2 = new Stemming();
        assertThat(stemming1).isNotEqualTo(stemming2);

        stemming2.setId(stemming1.getId());
        assertThat(stemming1).isEqualTo(stemming2);

        stemming2 = getStemmingSample2();
        assertThat(stemming1).isNotEqualTo(stemming2);
    }

    @Test
    void betreftRaadsstukTest() throws Exception {
        Stemming stemming = getStemmingRandomSampleGenerator();
        Raadsstuk raadsstukBack = getRaadsstukRandomSampleGenerator();

        stemming.setBetreftRaadsstuk(raadsstukBack);
        assertThat(stemming.getBetreftRaadsstuk()).isEqualTo(raadsstukBack);

        stemming.betreftRaadsstuk(null);
        assertThat(stemming.getBetreftRaadsstuk()).isNull();
    }

    @Test
    void hoortbijAgendapuntTest() throws Exception {
        Stemming stemming = getStemmingRandomSampleGenerator();
        Agendapunt agendapuntBack = getAgendapuntRandomSampleGenerator();

        stemming.setHoortbijAgendapunt(agendapuntBack);
        assertThat(stemming.getHoortbijAgendapunt()).isEqualTo(agendapuntBack);

        stemming.hoortbijAgendapunt(null);
        assertThat(stemming.getHoortbijAgendapunt()).isNull();
    }
}
