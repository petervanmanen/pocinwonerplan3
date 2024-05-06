package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ProgrammaTestSamples.*;
import static nl.ritense.demo.domain.ProgrammasoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProgrammasoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Programmasoort.class);
        Programmasoort programmasoort1 = getProgrammasoortSample1();
        Programmasoort programmasoort2 = new Programmasoort();
        assertThat(programmasoort1).isNotEqualTo(programmasoort2);

        programmasoort2.setId(programmasoort1.getId());
        assertThat(programmasoort1).isEqualTo(programmasoort2);

        programmasoort2 = getProgrammasoortSample2();
        assertThat(programmasoort1).isNotEqualTo(programmasoort2);
    }

    @Test
    void voorProgrammaTest() throws Exception {
        Programmasoort programmasoort = getProgrammasoortRandomSampleGenerator();
        Programma programmaBack = getProgrammaRandomSampleGenerator();

        programmasoort.addVoorProgramma(programmaBack);
        assertThat(programmasoort.getVoorProgrammas()).containsOnly(programmaBack);
        assertThat(programmaBack.getVoorProgrammasoorts()).containsOnly(programmasoort);

        programmasoort.removeVoorProgramma(programmaBack);
        assertThat(programmasoort.getVoorProgrammas()).doesNotContain(programmaBack);
        assertThat(programmaBack.getVoorProgrammasoorts()).doesNotContain(programmasoort);

        programmasoort.voorProgrammas(new HashSet<>(Set.of(programmaBack)));
        assertThat(programmasoort.getVoorProgrammas()).containsOnly(programmaBack);
        assertThat(programmaBack.getVoorProgrammasoorts()).containsOnly(programmasoort);

        programmasoort.setVoorProgrammas(new HashSet<>());
        assertThat(programmasoort.getVoorProgrammas()).doesNotContain(programmaBack);
        assertThat(programmaBack.getVoorProgrammasoorts()).doesNotContain(programmasoort);
    }
}
