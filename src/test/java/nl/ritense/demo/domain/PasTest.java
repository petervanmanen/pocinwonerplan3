package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MilieustraatTestSamples.*;
import static nl.ritense.demo.domain.PasTestSamples.*;
import static nl.ritense.demo.domain.StortingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PasTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pas.class);
        Pas pas1 = getPasSample1();
        Pas pas2 = new Pas();
        assertThat(pas1).isNotEqualTo(pas2);

        pas2.setId(pas1.getId());
        assertThat(pas1).isEqualTo(pas2);

        pas2 = getPasSample2();
        assertThat(pas1).isNotEqualTo(pas2);
    }

    @Test
    void uitgevoerdestortingStortingTest() throws Exception {
        Pas pas = getPasRandomSampleGenerator();
        Storting stortingBack = getStortingRandomSampleGenerator();

        pas.addUitgevoerdestortingStorting(stortingBack);
        assertThat(pas.getUitgevoerdestortingStortings()).containsOnly(stortingBack);
        assertThat(stortingBack.getUitgevoerdestortingPas()).isEqualTo(pas);

        pas.removeUitgevoerdestortingStorting(stortingBack);
        assertThat(pas.getUitgevoerdestortingStortings()).doesNotContain(stortingBack);
        assertThat(stortingBack.getUitgevoerdestortingPas()).isNull();

        pas.uitgevoerdestortingStortings(new HashSet<>(Set.of(stortingBack)));
        assertThat(pas.getUitgevoerdestortingStortings()).containsOnly(stortingBack);
        assertThat(stortingBack.getUitgevoerdestortingPas()).isEqualTo(pas);

        pas.setUitgevoerdestortingStortings(new HashSet<>());
        assertThat(pas.getUitgevoerdestortingStortings()).doesNotContain(stortingBack);
        assertThat(stortingBack.getUitgevoerdestortingPas()).isNull();
    }

    @Test
    void geldigvoorMilieustraatTest() throws Exception {
        Pas pas = getPasRandomSampleGenerator();
        Milieustraat milieustraatBack = getMilieustraatRandomSampleGenerator();

        pas.addGeldigvoorMilieustraat(milieustraatBack);
        assertThat(pas.getGeldigvoorMilieustraats()).containsOnly(milieustraatBack);

        pas.removeGeldigvoorMilieustraat(milieustraatBack);
        assertThat(pas.getGeldigvoorMilieustraats()).doesNotContain(milieustraatBack);

        pas.geldigvoorMilieustraats(new HashSet<>(Set.of(milieustraatBack)));
        assertThat(pas.getGeldigvoorMilieustraats()).containsOnly(milieustraatBack);

        pas.setGeldigvoorMilieustraats(new HashSet<>());
        assertThat(pas.getGeldigvoorMilieustraats()).doesNotContain(milieustraatBack);
    }
}
