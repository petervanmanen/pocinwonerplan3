package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeheerobjectTestSamples.*;
import static nl.ritense.demo.domain.MeldingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BeheerobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beheerobject.class);
        Beheerobject beheerobject1 = getBeheerobjectSample1();
        Beheerobject beheerobject2 = new Beheerobject();
        assertThat(beheerobject1).isNotEqualTo(beheerobject2);

        beheerobject2.setId(beheerobject1.getId());
        assertThat(beheerobject1).isEqualTo(beheerobject2);

        beheerobject2 = getBeheerobjectSample2();
        assertThat(beheerobject1).isNotEqualTo(beheerobject2);
    }

    @Test
    void betreftMeldingTest() throws Exception {
        Beheerobject beheerobject = getBeheerobjectRandomSampleGenerator();
        Melding meldingBack = getMeldingRandomSampleGenerator();

        beheerobject.addBetreftMelding(meldingBack);
        assertThat(beheerobject.getBetreftMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getBetreftBeheerobjects()).containsOnly(beheerobject);

        beheerobject.removeBetreftMelding(meldingBack);
        assertThat(beheerobject.getBetreftMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getBetreftBeheerobjects()).doesNotContain(beheerobject);

        beheerobject.betreftMeldings(new HashSet<>(Set.of(meldingBack)));
        assertThat(beheerobject.getBetreftMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getBetreftBeheerobjects()).containsOnly(beheerobject);

        beheerobject.setBetreftMeldings(new HashSet<>());
        assertThat(beheerobject.getBetreftMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getBetreftBeheerobjects()).doesNotContain(beheerobject);
    }
}
