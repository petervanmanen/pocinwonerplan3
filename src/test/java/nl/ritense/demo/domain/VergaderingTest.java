package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AgendapuntTestSamples.*;
import static nl.ritense.demo.domain.RaadscommissieTestSamples.*;
import static nl.ritense.demo.domain.RaadsstukTestSamples.*;
import static nl.ritense.demo.domain.VergaderingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VergaderingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vergadering.class);
        Vergadering vergadering1 = getVergaderingSample1();
        Vergadering vergadering2 = new Vergadering();
        assertThat(vergadering1).isNotEqualTo(vergadering2);

        vergadering2.setId(vergadering1.getId());
        assertThat(vergadering1).isEqualTo(vergadering2);

        vergadering2 = getVergaderingSample2();
        assertThat(vergadering1).isNotEqualTo(vergadering2);
    }

    @Test
    void heeftverslagRaadsstukTest() throws Exception {
        Vergadering vergadering = getVergaderingRandomSampleGenerator();
        Raadsstuk raadsstukBack = getRaadsstukRandomSampleGenerator();

        vergadering.setHeeftverslagRaadsstuk(raadsstukBack);
        assertThat(vergadering.getHeeftverslagRaadsstuk()).isEqualTo(raadsstukBack);

        vergadering.heeftverslagRaadsstuk(null);
        assertThat(vergadering.getHeeftverslagRaadsstuk()).isNull();
    }

    @Test
    void heeftAgendapuntTest() throws Exception {
        Vergadering vergadering = getVergaderingRandomSampleGenerator();
        Agendapunt agendapuntBack = getAgendapuntRandomSampleGenerator();

        vergadering.addHeeftAgendapunt(agendapuntBack);
        assertThat(vergadering.getHeeftAgendapunts()).containsOnly(agendapuntBack);
        assertThat(agendapuntBack.getHeeftVergadering()).isEqualTo(vergadering);

        vergadering.removeHeeftAgendapunt(agendapuntBack);
        assertThat(vergadering.getHeeftAgendapunts()).doesNotContain(agendapuntBack);
        assertThat(agendapuntBack.getHeeftVergadering()).isNull();

        vergadering.heeftAgendapunts(new HashSet<>(Set.of(agendapuntBack)));
        assertThat(vergadering.getHeeftAgendapunts()).containsOnly(agendapuntBack);
        assertThat(agendapuntBack.getHeeftVergadering()).isEqualTo(vergadering);

        vergadering.setHeeftAgendapunts(new HashSet<>());
        assertThat(vergadering.getHeeftAgendapunts()).doesNotContain(agendapuntBack);
        assertThat(agendapuntBack.getHeeftVergadering()).isNull();
    }

    @Test
    void heeftRaadscommissieTest() throws Exception {
        Vergadering vergadering = getVergaderingRandomSampleGenerator();
        Raadscommissie raadscommissieBack = getRaadscommissieRandomSampleGenerator();

        vergadering.setHeeftRaadscommissie(raadscommissieBack);
        assertThat(vergadering.getHeeftRaadscommissie()).isEqualTo(raadscommissieBack);

        vergadering.heeftRaadscommissie(null);
        assertThat(vergadering.getHeeftRaadscommissie()).isNull();
    }

    @Test
    void wordtbehandeldinRaadsstukTest() throws Exception {
        Vergadering vergadering = getVergaderingRandomSampleGenerator();
        Raadsstuk raadsstukBack = getRaadsstukRandomSampleGenerator();

        vergadering.addWordtbehandeldinRaadsstuk(raadsstukBack);
        assertThat(vergadering.getWordtbehandeldinRaadsstuks()).containsOnly(raadsstukBack);
        assertThat(raadsstukBack.getWordtbehandeldinVergaderings()).containsOnly(vergadering);

        vergadering.removeWordtbehandeldinRaadsstuk(raadsstukBack);
        assertThat(vergadering.getWordtbehandeldinRaadsstuks()).doesNotContain(raadsstukBack);
        assertThat(raadsstukBack.getWordtbehandeldinVergaderings()).doesNotContain(vergadering);

        vergadering.wordtbehandeldinRaadsstuks(new HashSet<>(Set.of(raadsstukBack)));
        assertThat(vergadering.getWordtbehandeldinRaadsstuks()).containsOnly(raadsstukBack);
        assertThat(raadsstukBack.getWordtbehandeldinVergaderings()).containsOnly(vergadering);

        vergadering.setWordtbehandeldinRaadsstuks(new HashSet<>());
        assertThat(vergadering.getWordtbehandeldinRaadsstuks()).doesNotContain(raadsstukBack);
        assertThat(raadsstukBack.getWordtbehandeldinVergaderings()).doesNotContain(vergadering);
    }
}
