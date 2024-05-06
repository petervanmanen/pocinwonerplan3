package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OphaalmomentTestSamples.*;
import static nl.ritense.demo.domain.RitTestSamples.*;
import static nl.ritense.demo.domain.VuilniswagenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rit.class);
        Rit rit1 = getRitSample1();
        Rit rit2 = new Rit();
        assertThat(rit1).isNotEqualTo(rit2);

        rit2.setId(rit1.getId());
        assertThat(rit1).isEqualTo(rit2);

        rit2 = getRitSample2();
        assertThat(rit1).isNotEqualTo(rit2);
    }

    @Test
    void heeftOphaalmomentTest() throws Exception {
        Rit rit = getRitRandomSampleGenerator();
        Ophaalmoment ophaalmomentBack = getOphaalmomentRandomSampleGenerator();

        rit.addHeeftOphaalmoment(ophaalmomentBack);
        assertThat(rit.getHeeftOphaalmoments()).containsOnly(ophaalmomentBack);
        assertThat(ophaalmomentBack.getHeeftRit()).isEqualTo(rit);

        rit.removeHeeftOphaalmoment(ophaalmomentBack);
        assertThat(rit.getHeeftOphaalmoments()).doesNotContain(ophaalmomentBack);
        assertThat(ophaalmomentBack.getHeeftRit()).isNull();

        rit.heeftOphaalmoments(new HashSet<>(Set.of(ophaalmomentBack)));
        assertThat(rit.getHeeftOphaalmoments()).containsOnly(ophaalmomentBack);
        assertThat(ophaalmomentBack.getHeeftRit()).isEqualTo(rit);

        rit.setHeeftOphaalmoments(new HashSet<>());
        assertThat(rit.getHeeftOphaalmoments()).doesNotContain(ophaalmomentBack);
        assertThat(ophaalmomentBack.getHeeftRit()).isNull();
    }

    @Test
    void uitgevoerdmetVuilniswagenTest() throws Exception {
        Rit rit = getRitRandomSampleGenerator();
        Vuilniswagen vuilniswagenBack = getVuilniswagenRandomSampleGenerator();

        rit.setUitgevoerdmetVuilniswagen(vuilniswagenBack);
        assertThat(rit.getUitgevoerdmetVuilniswagen()).isEqualTo(vuilniswagenBack);

        rit.uitgevoerdmetVuilniswagen(null);
        assertThat(rit.getUitgevoerdmetVuilniswagen()).isNull();
    }
}
