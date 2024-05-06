package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SportparkTestSamples.*;
import static nl.ritense.demo.domain.VeldTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SportparkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sportpark.class);
        Sportpark sportpark1 = getSportparkSample1();
        Sportpark sportpark2 = new Sportpark();
        assertThat(sportpark1).isNotEqualTo(sportpark2);

        sportpark2.setId(sportpark1.getId());
        assertThat(sportpark1).isEqualTo(sportpark2);

        sportpark2 = getSportparkSample2();
        assertThat(sportpark1).isNotEqualTo(sportpark2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Sportpark sportpark = new Sportpark();
        assertThat(sportpark.hashCode()).isZero();

        Sportpark sportpark1 = getSportparkSample1();
        sportpark.setId(sportpark1.getId());
        assertThat(sportpark).hasSameHashCodeAs(sportpark1);
    }

    @Test
    void heeftVeldTest() throws Exception {
        Sportpark sportpark = getSportparkRandomSampleGenerator();
        Veld veldBack = getVeldRandomSampleGenerator();

        sportpark.addHeeftVeld(veldBack);
        assertThat(sportpark.getHeeftVelds()).containsOnly(veldBack);
        assertThat(veldBack.getHeeftSportpark()).isEqualTo(sportpark);

        sportpark.removeHeeftVeld(veldBack);
        assertThat(sportpark.getHeeftVelds()).doesNotContain(veldBack);
        assertThat(veldBack.getHeeftSportpark()).isNull();

        sportpark.heeftVelds(new HashSet<>(Set.of(veldBack)));
        assertThat(sportpark.getHeeftVelds()).containsOnly(veldBack);
        assertThat(veldBack.getHeeftSportpark()).isEqualTo(sportpark);

        sportpark.setHeeftVelds(new HashSet<>());
        assertThat(sportpark.getHeeftVelds()).doesNotContain(veldBack);
        assertThat(veldBack.getHeeftSportpark()).isNull();
    }
}
