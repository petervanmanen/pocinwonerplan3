package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ActivaTestSamples.*;
import static nl.ritense.demo.domain.ActivasoortTestSamples.*;
import static nl.ritense.demo.domain.HoofdrekeningTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ActivaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Activa.class);
        Activa activa1 = getActivaSample1();
        Activa activa2 = new Activa();
        assertThat(activa1).isNotEqualTo(activa2);

        activa2.setId(activa1.getId());
        assertThat(activa1).isEqualTo(activa2);

        activa2 = getActivaSample2();
        assertThat(activa1).isNotEqualTo(activa2);
    }

    @Test
    void issoortActivasoortTest() throws Exception {
        Activa activa = getActivaRandomSampleGenerator();
        Activasoort activasoortBack = getActivasoortRandomSampleGenerator();

        activa.setIssoortActivasoort(activasoortBack);
        assertThat(activa.getIssoortActivasoort()).isEqualTo(activasoortBack);

        activa.issoortActivasoort(null);
        assertThat(activa.getIssoortActivasoort()).isNull();
    }

    @Test
    void heeftHoofdrekeningTest() throws Exception {
        Activa activa = getActivaRandomSampleGenerator();
        Hoofdrekening hoofdrekeningBack = getHoofdrekeningRandomSampleGenerator();

        activa.addHeeftHoofdrekening(hoofdrekeningBack);
        assertThat(activa.getHeeftHoofdrekenings()).containsOnly(hoofdrekeningBack);
        assertThat(hoofdrekeningBack.getHeeftActivas()).containsOnly(activa);

        activa.removeHeeftHoofdrekening(hoofdrekeningBack);
        assertThat(activa.getHeeftHoofdrekenings()).doesNotContain(hoofdrekeningBack);
        assertThat(hoofdrekeningBack.getHeeftActivas()).doesNotContain(activa);

        activa.heeftHoofdrekenings(new HashSet<>(Set.of(hoofdrekeningBack)));
        assertThat(activa.getHeeftHoofdrekenings()).containsOnly(hoofdrekeningBack);
        assertThat(hoofdrekeningBack.getHeeftActivas()).containsOnly(activa);

        activa.setHeeftHoofdrekenings(new HashSet<>());
        assertThat(activa.getHeeftHoofdrekenings()).doesNotContain(hoofdrekeningBack);
        assertThat(hoofdrekeningBack.getHeeftActivas()).doesNotContain(activa);
    }
}
