package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LijnTestSamples.*;
import static nl.ritense.demo.domain.LijnengroepTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LijnengroepTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lijnengroep.class);
        Lijnengroep lijnengroep1 = getLijnengroepSample1();
        Lijnengroep lijnengroep2 = new Lijnengroep();
        assertThat(lijnengroep1).isNotEqualTo(lijnengroep2);

        lijnengroep2.setId(lijnengroep1.getId());
        assertThat(lijnengroep1).isEqualTo(lijnengroep2);

        lijnengroep2 = getLijnengroepSample2();
        assertThat(lijnengroep1).isNotEqualTo(lijnengroep2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Lijnengroep lijnengroep = new Lijnengroep();
        assertThat(lijnengroep.hashCode()).isZero();

        Lijnengroep lijnengroep1 = getLijnengroepSample1();
        lijnengroep.setId(lijnengroep1.getId());
        assertThat(lijnengroep).hasSameHashCodeAs(lijnengroep1);
    }

    @Test
    void omvatLijnTest() throws Exception {
        Lijnengroep lijnengroep = getLijnengroepRandomSampleGenerator();
        Lijn lijnBack = getLijnRandomSampleGenerator();

        lijnengroep.addOmvatLijn(lijnBack);
        assertThat(lijnengroep.getOmvatLijns()).containsOnly(lijnBack);
        assertThat(lijnBack.getOmvatLijnengroep()).isEqualTo(lijnengroep);

        lijnengroep.removeOmvatLijn(lijnBack);
        assertThat(lijnengroep.getOmvatLijns()).doesNotContain(lijnBack);
        assertThat(lijnBack.getOmvatLijnengroep()).isNull();

        lijnengroep.omvatLijns(new HashSet<>(Set.of(lijnBack)));
        assertThat(lijnengroep.getOmvatLijns()).containsOnly(lijnBack);
        assertThat(lijnBack.getOmvatLijnengroep()).isEqualTo(lijnengroep);

        lijnengroep.setOmvatLijns(new HashSet<>());
        assertThat(lijnengroep.getOmvatLijns()).doesNotContain(lijnBack);
        assertThat(lijnBack.getOmvatLijnengroep()).isNull();
    }
}
