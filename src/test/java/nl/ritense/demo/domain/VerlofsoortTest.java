package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerlofTestSamples.*;
import static nl.ritense.demo.domain.VerlofsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerlofsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verlofsoort.class);
        Verlofsoort verlofsoort1 = getVerlofsoortSample1();
        Verlofsoort verlofsoort2 = new Verlofsoort();
        assertThat(verlofsoort1).isNotEqualTo(verlofsoort2);

        verlofsoort2.setId(verlofsoort1.getId());
        assertThat(verlofsoort1).isEqualTo(verlofsoort2);

        verlofsoort2 = getVerlofsoortSample2();
        assertThat(verlofsoort1).isNotEqualTo(verlofsoort2);
    }

    @Test
    void soortverlofVerlofTest() throws Exception {
        Verlofsoort verlofsoort = getVerlofsoortRandomSampleGenerator();
        Verlof verlofBack = getVerlofRandomSampleGenerator();

        verlofsoort.addSoortverlofVerlof(verlofBack);
        assertThat(verlofsoort.getSoortverlofVerlofs()).containsOnly(verlofBack);
        assertThat(verlofBack.getSoortverlofVerlofsoort()).isEqualTo(verlofsoort);

        verlofsoort.removeSoortverlofVerlof(verlofBack);
        assertThat(verlofsoort.getSoortverlofVerlofs()).doesNotContain(verlofBack);
        assertThat(verlofBack.getSoortverlofVerlofsoort()).isNull();

        verlofsoort.soortverlofVerlofs(new HashSet<>(Set.of(verlofBack)));
        assertThat(verlofsoort.getSoortverlofVerlofs()).containsOnly(verlofBack);
        assertThat(verlofBack.getSoortverlofVerlofsoort()).isEqualTo(verlofsoort);

        verlofsoort.setSoortverlofVerlofs(new HashSet<>());
        assertThat(verlofsoort.getSoortverlofVerlofs()).doesNotContain(verlofBack);
        assertThat(verlofBack.getSoortverlofVerlofsoort()).isNull();
    }
}
