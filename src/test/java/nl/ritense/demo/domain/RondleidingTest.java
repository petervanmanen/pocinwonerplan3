package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ActiviteitTestSamples.*;
import static nl.ritense.demo.domain.RondleidingTestSamples.*;
import static nl.ritense.demo.domain.TentoonstellingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RondleidingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rondleiding.class);
        Rondleiding rondleiding1 = getRondleidingSample1();
        Rondleiding rondleiding2 = new Rondleiding();
        assertThat(rondleiding1).isNotEqualTo(rondleiding2);

        rondleiding2.setId(rondleiding1.getId());
        assertThat(rondleiding1).isEqualTo(rondleiding2);

        rondleiding2 = getRondleidingSample2();
        assertThat(rondleiding1).isNotEqualTo(rondleiding2);
    }

    @Test
    void voorTentoonstellingTest() throws Exception {
        Rondleiding rondleiding = getRondleidingRandomSampleGenerator();
        Tentoonstelling tentoonstellingBack = getTentoonstellingRandomSampleGenerator();

        rondleiding.setVoorTentoonstelling(tentoonstellingBack);
        assertThat(rondleiding.getVoorTentoonstelling()).isEqualTo(tentoonstellingBack);

        rondleiding.voorTentoonstelling(null);
        assertThat(rondleiding.getVoorTentoonstelling()).isNull();
    }

    @Test
    void heeftActiviteitTest() throws Exception {
        Rondleiding rondleiding = getRondleidingRandomSampleGenerator();
        Activiteit activiteitBack = getActiviteitRandomSampleGenerator();

        rondleiding.addHeeftActiviteit(activiteitBack);
        assertThat(rondleiding.getHeeftActiviteits()).containsOnly(activiteitBack);
        assertThat(activiteitBack.getHeeftRondleiding()).isEqualTo(rondleiding);

        rondleiding.removeHeeftActiviteit(activiteitBack);
        assertThat(rondleiding.getHeeftActiviteits()).doesNotContain(activiteitBack);
        assertThat(activiteitBack.getHeeftRondleiding()).isNull();

        rondleiding.heeftActiviteits(new HashSet<>(Set.of(activiteitBack)));
        assertThat(rondleiding.getHeeftActiviteits()).containsOnly(activiteitBack);
        assertThat(activiteitBack.getHeeftRondleiding()).isEqualTo(rondleiding);

        rondleiding.setHeeftActiviteits(new HashSet<>());
        assertThat(rondleiding.getHeeftActiviteits()).doesNotContain(activiteitBack);
        assertThat(activiteitBack.getHeeftRondleiding()).isNull();
    }
}
