package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OmgevingsdocumentTestSamples.*;
import static nl.ritense.demo.domain.RegeltekstTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OmgevingsdocumentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Omgevingsdocument.class);
        Omgevingsdocument omgevingsdocument1 = getOmgevingsdocumentSample1();
        Omgevingsdocument omgevingsdocument2 = new Omgevingsdocument();
        assertThat(omgevingsdocument1).isNotEqualTo(omgevingsdocument2);

        omgevingsdocument2.setId(omgevingsdocument1.getId());
        assertThat(omgevingsdocument1).isEqualTo(omgevingsdocument2);

        omgevingsdocument2 = getOmgevingsdocumentSample2();
        assertThat(omgevingsdocument1).isNotEqualTo(omgevingsdocument2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Omgevingsdocument omgevingsdocument = new Omgevingsdocument();
        assertThat(omgevingsdocument.hashCode()).isZero();

        Omgevingsdocument omgevingsdocument1 = getOmgevingsdocumentSample1();
        omgevingsdocument.setId(omgevingsdocument1.getId());
        assertThat(omgevingsdocument).hasSameHashCodeAs(omgevingsdocument1);
    }

    @Test
    void bevatRegeltekstTest() throws Exception {
        Omgevingsdocument omgevingsdocument = getOmgevingsdocumentRandomSampleGenerator();
        Regeltekst regeltekstBack = getRegeltekstRandomSampleGenerator();

        omgevingsdocument.addBevatRegeltekst(regeltekstBack);
        assertThat(omgevingsdocument.getBevatRegelteksts()).containsOnly(regeltekstBack);
        assertThat(regeltekstBack.getBevatOmgevingsdocument()).isEqualTo(omgevingsdocument);

        omgevingsdocument.removeBevatRegeltekst(regeltekstBack);
        assertThat(omgevingsdocument.getBevatRegelteksts()).doesNotContain(regeltekstBack);
        assertThat(regeltekstBack.getBevatOmgevingsdocument()).isNull();

        omgevingsdocument.bevatRegelteksts(new HashSet<>(Set.of(regeltekstBack)));
        assertThat(omgevingsdocument.getBevatRegelteksts()).containsOnly(regeltekstBack);
        assertThat(regeltekstBack.getBevatOmgevingsdocument()).isEqualTo(omgevingsdocument);

        omgevingsdocument.setBevatRegelteksts(new HashSet<>());
        assertThat(omgevingsdocument.getBevatRegelteksts()).doesNotContain(regeltekstBack);
        assertThat(regeltekstBack.getBevatOmgevingsdocument()).isNull();
    }
}
