package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DoosTestSamples.*;
import static nl.ritense.demo.domain.MagazijnlocatieTestSamples.*;
import static nl.ritense.demo.domain.StellingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MagazijnlocatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Magazijnlocatie.class);
        Magazijnlocatie magazijnlocatie1 = getMagazijnlocatieSample1();
        Magazijnlocatie magazijnlocatie2 = new Magazijnlocatie();
        assertThat(magazijnlocatie1).isNotEqualTo(magazijnlocatie2);

        magazijnlocatie2.setId(magazijnlocatie1.getId());
        assertThat(magazijnlocatie1).isEqualTo(magazijnlocatie2);

        magazijnlocatie2 = getMagazijnlocatieSample2();
        assertThat(magazijnlocatie1).isNotEqualTo(magazijnlocatie2);
    }

    @Test
    void heeftStellingTest() throws Exception {
        Magazijnlocatie magazijnlocatie = getMagazijnlocatieRandomSampleGenerator();
        Stelling stellingBack = getStellingRandomSampleGenerator();

        magazijnlocatie.setHeeftStelling(stellingBack);
        assertThat(magazijnlocatie.getHeeftStelling()).isEqualTo(stellingBack);

        magazijnlocatie.heeftStelling(null);
        assertThat(magazijnlocatie.getHeeftStelling()).isNull();
    }

    @Test
    void staatopDoosTest() throws Exception {
        Magazijnlocatie magazijnlocatie = getMagazijnlocatieRandomSampleGenerator();
        Doos doosBack = getDoosRandomSampleGenerator();

        magazijnlocatie.addStaatopDoos(doosBack);
        assertThat(magazijnlocatie.getStaatopDoos()).containsOnly(doosBack);
        assertThat(doosBack.getStaatopMagazijnlocatie()).isEqualTo(magazijnlocatie);

        magazijnlocatie.removeStaatopDoos(doosBack);
        assertThat(magazijnlocatie.getStaatopDoos()).doesNotContain(doosBack);
        assertThat(doosBack.getStaatopMagazijnlocatie()).isNull();

        magazijnlocatie.staatopDoos(new HashSet<>(Set.of(doosBack)));
        assertThat(magazijnlocatie.getStaatopDoos()).containsOnly(doosBack);
        assertThat(doosBack.getStaatopMagazijnlocatie()).isEqualTo(magazijnlocatie);

        magazijnlocatie.setStaatopDoos(new HashSet<>());
        assertThat(magazijnlocatie.getStaatopDoos()).doesNotContain(doosBack);
        assertThat(doosBack.getStaatopMagazijnlocatie()).isNull();
    }
}
