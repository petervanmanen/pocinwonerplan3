package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeslissingTestSamples.*;
import static nl.ritense.demo.domain.LeerplichtambtenaarTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeerplichtambtenaarTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leerplichtambtenaar.class);
        Leerplichtambtenaar leerplichtambtenaar1 = getLeerplichtambtenaarSample1();
        Leerplichtambtenaar leerplichtambtenaar2 = new Leerplichtambtenaar();
        assertThat(leerplichtambtenaar1).isNotEqualTo(leerplichtambtenaar2);

        leerplichtambtenaar2.setId(leerplichtambtenaar1.getId());
        assertThat(leerplichtambtenaar1).isEqualTo(leerplichtambtenaar2);

        leerplichtambtenaar2 = getLeerplichtambtenaarSample2();
        assertThat(leerplichtambtenaar1).isNotEqualTo(leerplichtambtenaar2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Leerplichtambtenaar leerplichtambtenaar = new Leerplichtambtenaar();
        assertThat(leerplichtambtenaar.hashCode()).isZero();

        Leerplichtambtenaar leerplichtambtenaar1 = getLeerplichtambtenaarSample1();
        leerplichtambtenaar.setId(leerplichtambtenaar1.getId());
        assertThat(leerplichtambtenaar).hasSameHashCodeAs(leerplichtambtenaar1);
    }

    @Test
    void behandelaarBeslissingTest() throws Exception {
        Leerplichtambtenaar leerplichtambtenaar = getLeerplichtambtenaarRandomSampleGenerator();
        Beslissing beslissingBack = getBeslissingRandomSampleGenerator();

        leerplichtambtenaar.addBehandelaarBeslissing(beslissingBack);
        assertThat(leerplichtambtenaar.getBehandelaarBeslissings()).containsOnly(beslissingBack);
        assertThat(beslissingBack.getBehandelaarLeerplichtambtenaar()).isEqualTo(leerplichtambtenaar);

        leerplichtambtenaar.removeBehandelaarBeslissing(beslissingBack);
        assertThat(leerplichtambtenaar.getBehandelaarBeslissings()).doesNotContain(beslissingBack);
        assertThat(beslissingBack.getBehandelaarLeerplichtambtenaar()).isNull();

        leerplichtambtenaar.behandelaarBeslissings(new HashSet<>(Set.of(beslissingBack)));
        assertThat(leerplichtambtenaar.getBehandelaarBeslissings()).containsOnly(beslissingBack);
        assertThat(beslissingBack.getBehandelaarLeerplichtambtenaar()).isEqualTo(leerplichtambtenaar);

        leerplichtambtenaar.setBehandelaarBeslissings(new HashSet<>());
        assertThat(leerplichtambtenaar.getBehandelaarBeslissings()).doesNotContain(beslissingBack);
        assertThat(beslissingBack.getBehandelaarLeerplichtambtenaar()).isNull();
    }
}
