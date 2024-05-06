package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BelijningTestSamples.*;
import static nl.ritense.demo.domain.SportparkTestSamples.*;
import static nl.ritense.demo.domain.VeldTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VeldTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Veld.class);
        Veld veld1 = getVeldSample1();
        Veld veld2 = new Veld();
        assertThat(veld1).isNotEqualTo(veld2);

        veld2.setId(veld1.getId());
        assertThat(veld1).isEqualTo(veld2);

        veld2 = getVeldSample2();
        assertThat(veld1).isNotEqualTo(veld2);
    }

    @Test
    void heeftBelijningTest() throws Exception {
        Veld veld = getVeldRandomSampleGenerator();
        Belijning belijningBack = getBelijningRandomSampleGenerator();

        veld.addHeeftBelijning(belijningBack);
        assertThat(veld.getHeeftBelijnings()).containsOnly(belijningBack);

        veld.removeHeeftBelijning(belijningBack);
        assertThat(veld.getHeeftBelijnings()).doesNotContain(belijningBack);

        veld.heeftBelijnings(new HashSet<>(Set.of(belijningBack)));
        assertThat(veld.getHeeftBelijnings()).containsOnly(belijningBack);

        veld.setHeeftBelijnings(new HashSet<>());
        assertThat(veld.getHeeftBelijnings()).doesNotContain(belijningBack);
    }

    @Test
    void heeftSportparkTest() throws Exception {
        Veld veld = getVeldRandomSampleGenerator();
        Sportpark sportparkBack = getSportparkRandomSampleGenerator();

        veld.setHeeftSportpark(sportparkBack);
        assertThat(veld.getHeeftSportpark()).isEqualTo(sportparkBack);

        veld.heeftSportpark(null);
        assertThat(veld.getHeeftSportpark()).isNull();
    }
}
