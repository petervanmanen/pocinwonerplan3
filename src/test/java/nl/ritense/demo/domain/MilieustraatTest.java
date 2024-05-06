package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FractieTestSamples.*;
import static nl.ritense.demo.domain.MilieustraatTestSamples.*;
import static nl.ritense.demo.domain.PasTestSamples.*;
import static nl.ritense.demo.domain.StortingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MilieustraatTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Milieustraat.class);
        Milieustraat milieustraat1 = getMilieustraatSample1();
        Milieustraat milieustraat2 = new Milieustraat();
        assertThat(milieustraat1).isNotEqualTo(milieustraat2);

        milieustraat2.setId(milieustraat1.getId());
        assertThat(milieustraat1).isEqualTo(milieustraat2);

        milieustraat2 = getMilieustraatSample2();
        assertThat(milieustraat1).isNotEqualTo(milieustraat2);
    }

    @Test
    void inzamelpuntvanFractieTest() throws Exception {
        Milieustraat milieustraat = getMilieustraatRandomSampleGenerator();
        Fractie fractieBack = getFractieRandomSampleGenerator();

        milieustraat.addInzamelpuntvanFractie(fractieBack);
        assertThat(milieustraat.getInzamelpuntvanFracties()).containsOnly(fractieBack);

        milieustraat.removeInzamelpuntvanFractie(fractieBack);
        assertThat(milieustraat.getInzamelpuntvanFracties()).doesNotContain(fractieBack);

        milieustraat.inzamelpuntvanFracties(new HashSet<>(Set.of(fractieBack)));
        assertThat(milieustraat.getInzamelpuntvanFracties()).containsOnly(fractieBack);

        milieustraat.setInzamelpuntvanFracties(new HashSet<>());
        assertThat(milieustraat.getInzamelpuntvanFracties()).doesNotContain(fractieBack);
    }

    @Test
    void bijStortingTest() throws Exception {
        Milieustraat milieustraat = getMilieustraatRandomSampleGenerator();
        Storting stortingBack = getStortingRandomSampleGenerator();

        milieustraat.addBijStorting(stortingBack);
        assertThat(milieustraat.getBijStortings()).containsOnly(stortingBack);
        assertThat(stortingBack.getBijMilieustraat()).isEqualTo(milieustraat);

        milieustraat.removeBijStorting(stortingBack);
        assertThat(milieustraat.getBijStortings()).doesNotContain(stortingBack);
        assertThat(stortingBack.getBijMilieustraat()).isNull();

        milieustraat.bijStortings(new HashSet<>(Set.of(stortingBack)));
        assertThat(milieustraat.getBijStortings()).containsOnly(stortingBack);
        assertThat(stortingBack.getBijMilieustraat()).isEqualTo(milieustraat);

        milieustraat.setBijStortings(new HashSet<>());
        assertThat(milieustraat.getBijStortings()).doesNotContain(stortingBack);
        assertThat(stortingBack.getBijMilieustraat()).isNull();
    }

    @Test
    void geldigvoorPasTest() throws Exception {
        Milieustraat milieustraat = getMilieustraatRandomSampleGenerator();
        Pas pasBack = getPasRandomSampleGenerator();

        milieustraat.addGeldigvoorPas(pasBack);
        assertThat(milieustraat.getGeldigvoorPas()).containsOnly(pasBack);
        assertThat(pasBack.getGeldigvoorMilieustraats()).containsOnly(milieustraat);

        milieustraat.removeGeldigvoorPas(pasBack);
        assertThat(milieustraat.getGeldigvoorPas()).doesNotContain(pasBack);
        assertThat(pasBack.getGeldigvoorMilieustraats()).doesNotContain(milieustraat);

        milieustraat.geldigvoorPas(new HashSet<>(Set.of(pasBack)));
        assertThat(milieustraat.getGeldigvoorPas()).containsOnly(pasBack);
        assertThat(pasBack.getGeldigvoorMilieustraats()).containsOnly(milieustraat);

        milieustraat.setGeldigvoorPas(new HashSet<>());
        assertThat(milieustraat.getGeldigvoorPas()).doesNotContain(pasBack);
        assertThat(pasBack.getGeldigvoorMilieustraats()).doesNotContain(milieustraat);
    }
}
