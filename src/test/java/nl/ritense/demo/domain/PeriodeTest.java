package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ArchiefTestSamples.*;
import static nl.ritense.demo.domain.ArchiefstukTestSamples.*;
import static nl.ritense.demo.domain.HoofdstukTestSamples.*;
import static nl.ritense.demo.domain.PeriodeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PeriodeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Periode.class);
        Periode periode1 = getPeriodeSample1();
        Periode periode2 = new Periode();
        assertThat(periode1).isNotEqualTo(periode2);

        periode2.setId(periode1.getId());
        assertThat(periode1).isEqualTo(periode2);

        periode2 = getPeriodeSample2();
        assertThat(periode1).isNotEqualTo(periode2);
    }

    @Test
    void stamtuitArchiefTest() throws Exception {
        Periode periode = getPeriodeRandomSampleGenerator();
        Archief archiefBack = getArchiefRandomSampleGenerator();

        periode.addStamtuitArchief(archiefBack);
        assertThat(periode.getStamtuitArchiefs()).containsOnly(archiefBack);
        assertThat(archiefBack.getStamtuitPeriodes()).containsOnly(periode);

        periode.removeStamtuitArchief(archiefBack);
        assertThat(periode.getStamtuitArchiefs()).doesNotContain(archiefBack);
        assertThat(archiefBack.getStamtuitPeriodes()).doesNotContain(periode);

        periode.stamtuitArchiefs(new HashSet<>(Set.of(archiefBack)));
        assertThat(periode.getStamtuitArchiefs()).containsOnly(archiefBack);
        assertThat(archiefBack.getStamtuitPeriodes()).containsOnly(periode);

        periode.setStamtuitArchiefs(new HashSet<>());
        assertThat(periode.getStamtuitArchiefs()).doesNotContain(archiefBack);
        assertThat(archiefBack.getStamtuitPeriodes()).doesNotContain(periode);
    }

    @Test
    void stamtuitArchiefstukTest() throws Exception {
        Periode periode = getPeriodeRandomSampleGenerator();
        Archiefstuk archiefstukBack = getArchiefstukRandomSampleGenerator();

        periode.addStamtuitArchiefstuk(archiefstukBack);
        assertThat(periode.getStamtuitArchiefstuks()).containsOnly(archiefstukBack);
        assertThat(archiefstukBack.getStamtuitPeriodes()).containsOnly(periode);

        periode.removeStamtuitArchiefstuk(archiefstukBack);
        assertThat(periode.getStamtuitArchiefstuks()).doesNotContain(archiefstukBack);
        assertThat(archiefstukBack.getStamtuitPeriodes()).doesNotContain(periode);

        periode.stamtuitArchiefstuks(new HashSet<>(Set.of(archiefstukBack)));
        assertThat(periode.getStamtuitArchiefstuks()).containsOnly(archiefstukBack);
        assertThat(archiefstukBack.getStamtuitPeriodes()).containsOnly(periode);

        periode.setStamtuitArchiefstuks(new HashSet<>());
        assertThat(periode.getStamtuitArchiefstuks()).doesNotContain(archiefstukBack);
        assertThat(archiefstukBack.getStamtuitPeriodes()).doesNotContain(periode);
    }

    @Test
    void binnenHoofdstukTest() throws Exception {
        Periode periode = getPeriodeRandomSampleGenerator();
        Hoofdstuk hoofdstukBack = getHoofdstukRandomSampleGenerator();

        periode.addBinnenHoofdstuk(hoofdstukBack);
        assertThat(periode.getBinnenHoofdstuks()).containsOnly(hoofdstukBack);
        assertThat(hoofdstukBack.getBinnenPeriodes()).containsOnly(periode);

        periode.removeBinnenHoofdstuk(hoofdstukBack);
        assertThat(periode.getBinnenHoofdstuks()).doesNotContain(hoofdstukBack);
        assertThat(hoofdstukBack.getBinnenPeriodes()).doesNotContain(periode);

        periode.binnenHoofdstuks(new HashSet<>(Set.of(hoofdstukBack)));
        assertThat(periode.getBinnenHoofdstuks()).containsOnly(hoofdstukBack);
        assertThat(hoofdstukBack.getBinnenPeriodes()).containsOnly(periode);

        periode.setBinnenHoofdstuks(new HashSet<>());
        assertThat(periode.getBinnenHoofdstuks()).doesNotContain(hoofdstukBack);
        assertThat(hoofdstukBack.getBinnenPeriodes()).doesNotContain(periode);
    }
}
