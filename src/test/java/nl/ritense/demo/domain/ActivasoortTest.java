package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ActivaTestSamples.*;
import static nl.ritense.demo.domain.ActivasoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActivasoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Activasoort.class);
        Activasoort activasoort1 = getActivasoortSample1();
        Activasoort activasoort2 = new Activasoort();
        assertThat(activasoort1).isNotEqualTo(activasoort2);

        activasoort2.setId(activasoort1.getId());
        assertThat(activasoort1).isEqualTo(activasoort2);

        activasoort2 = getActivasoortSample2();
        assertThat(activasoort1).isNotEqualTo(activasoort2);
    }

    @Test
    void issoortActivaTest() throws Exception {
        Activasoort activasoort = getActivasoortRandomSampleGenerator();
        Activa activaBack = getActivaRandomSampleGenerator();

        activasoort.addIssoortActiva(activaBack);
        assertThat(activasoort.getIssoortActivas()).containsOnly(activaBack);
        assertThat(activaBack.getIssoortActivasoort()).isEqualTo(activasoort);

        activasoort.removeIssoortActiva(activaBack);
        assertThat(activasoort.getIssoortActivas()).doesNotContain(activaBack);
        assertThat(activaBack.getIssoortActivasoort()).isNull();

        activasoort.issoortActivas(new HashSet<>(Set.of(activaBack)));
        assertThat(activasoort.getIssoortActivas()).containsOnly(activaBack);
        assertThat(activaBack.getIssoortActivasoort()).isEqualTo(activasoort);

        activasoort.setIssoortActivas(new HashSet<>());
        assertThat(activasoort.getIssoortActivas()).doesNotContain(activaBack);
        assertThat(activaBack.getIssoortActivasoort()).isNull();
    }
}
