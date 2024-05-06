package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.RegeltekstTestSamples.*;
import static nl.ritense.demo.domain.ThemaTestSamples.*;
import static nl.ritense.demo.domain.ThemaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ThemaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Thema.class);
        Thema thema1 = getThemaSample1();
        Thema thema2 = new Thema();
        assertThat(thema1).isNotEqualTo(thema2);

        thema2.setId(thema1.getId());
        assertThat(thema1).isEqualTo(thema2);

        thema2 = getThemaSample2();
        assertThat(thema1).isNotEqualTo(thema2);
    }

    @Test
    void subthemaThemaTest() throws Exception {
        Thema thema = getThemaRandomSampleGenerator();
        Thema themaBack = getThemaRandomSampleGenerator();

        thema.addSubthemaThema(themaBack);
        assertThat(thema.getSubthemaThemas()).containsOnly(themaBack);
        assertThat(themaBack.getSubthemaThema2()).isEqualTo(thema);

        thema.removeSubthemaThema(themaBack);
        assertThat(thema.getSubthemaThemas()).doesNotContain(themaBack);
        assertThat(themaBack.getSubthemaThema2()).isNull();

        thema.subthemaThemas(new HashSet<>(Set.of(themaBack)));
        assertThat(thema.getSubthemaThemas()).containsOnly(themaBack);
        assertThat(themaBack.getSubthemaThema2()).isEqualTo(thema);

        thema.setSubthemaThemas(new HashSet<>());
        assertThat(thema.getSubthemaThemas()).doesNotContain(themaBack);
        assertThat(themaBack.getSubthemaThema2()).isNull();
    }

    @Test
    void subthemaThema2Test() throws Exception {
        Thema thema = getThemaRandomSampleGenerator();
        Thema themaBack = getThemaRandomSampleGenerator();

        thema.setSubthemaThema2(themaBack);
        assertThat(thema.getSubthemaThema2()).isEqualTo(themaBack);

        thema.subthemaThema2(null);
        assertThat(thema.getSubthemaThema2()).isNull();
    }

    @Test
    void heeftthemaRegeltekstTest() throws Exception {
        Thema thema = getThemaRandomSampleGenerator();
        Regeltekst regeltekstBack = getRegeltekstRandomSampleGenerator();

        thema.addHeeftthemaRegeltekst(regeltekstBack);
        assertThat(thema.getHeeftthemaRegelteksts()).containsOnly(regeltekstBack);
        assertThat(regeltekstBack.getHeeftthemaThemas()).containsOnly(thema);

        thema.removeHeeftthemaRegeltekst(regeltekstBack);
        assertThat(thema.getHeeftthemaRegelteksts()).doesNotContain(regeltekstBack);
        assertThat(regeltekstBack.getHeeftthemaThemas()).doesNotContain(thema);

        thema.heeftthemaRegelteksts(new HashSet<>(Set.of(regeltekstBack)));
        assertThat(thema.getHeeftthemaRegelteksts()).containsOnly(regeltekstBack);
        assertThat(regeltekstBack.getHeeftthemaThemas()).containsOnly(thema);

        thema.setHeeftthemaRegelteksts(new HashSet<>());
        assertThat(thema.getHeeftthemaRegelteksts()).doesNotContain(regeltekstBack);
        assertThat(regeltekstBack.getHeeftthemaThemas()).doesNotContain(thema);
    }
}
