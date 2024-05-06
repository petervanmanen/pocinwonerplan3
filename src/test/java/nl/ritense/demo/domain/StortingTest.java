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

class StortingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Storting.class);
        Storting storting1 = getStortingSample1();
        Storting storting2 = new Storting();
        assertThat(storting1).isNotEqualTo(storting2);

        storting2.setId(storting1.getId());
        assertThat(storting1).isEqualTo(storting2);

        storting2 = getStortingSample2();
        assertThat(storting1).isNotEqualTo(storting2);
    }

    @Test
    void bijMilieustraatTest() throws Exception {
        Storting storting = getStortingRandomSampleGenerator();
        Milieustraat milieustraatBack = getMilieustraatRandomSampleGenerator();

        storting.setBijMilieustraat(milieustraatBack);
        assertThat(storting.getBijMilieustraat()).isEqualTo(milieustraatBack);

        storting.bijMilieustraat(null);
        assertThat(storting.getBijMilieustraat()).isNull();
    }

    @Test
    void fractieFractieTest() throws Exception {
        Storting storting = getStortingRandomSampleGenerator();
        Fractie fractieBack = getFractieRandomSampleGenerator();

        storting.addFractieFractie(fractieBack);
        assertThat(storting.getFractieFracties()).containsOnly(fractieBack);

        storting.removeFractieFractie(fractieBack);
        assertThat(storting.getFractieFracties()).doesNotContain(fractieBack);

        storting.fractieFracties(new HashSet<>(Set.of(fractieBack)));
        assertThat(storting.getFractieFracties()).containsOnly(fractieBack);

        storting.setFractieFracties(new HashSet<>());
        assertThat(storting.getFractieFracties()).doesNotContain(fractieBack);
    }

    @Test
    void uitgevoerdestortingPasTest() throws Exception {
        Storting storting = getStortingRandomSampleGenerator();
        Pas pasBack = getPasRandomSampleGenerator();

        storting.setUitgevoerdestortingPas(pasBack);
        assertThat(storting.getUitgevoerdestortingPas()).isEqualTo(pasBack);

        storting.uitgevoerdestortingPas(null);
        assertThat(storting.getUitgevoerdestortingPas()).isNull();
    }
}
