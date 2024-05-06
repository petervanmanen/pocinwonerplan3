package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.InkooporderTestSamples.*;
import static nl.ritense.demo.domain.InkooppakketTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InkooppakketTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inkooppakket.class);
        Inkooppakket inkooppakket1 = getInkooppakketSample1();
        Inkooppakket inkooppakket2 = new Inkooppakket();
        assertThat(inkooppakket1).isNotEqualTo(inkooppakket2);

        inkooppakket2.setId(inkooppakket1.getId());
        assertThat(inkooppakket1).isEqualTo(inkooppakket2);

        inkooppakket2 = getInkooppakketSample2();
        assertThat(inkooppakket1).isNotEqualTo(inkooppakket2);
    }

    @Test
    void heeftInkooporderTest() throws Exception {
        Inkooppakket inkooppakket = getInkooppakketRandomSampleGenerator();
        Inkooporder inkooporderBack = getInkooporderRandomSampleGenerator();

        inkooppakket.addHeeftInkooporder(inkooporderBack);
        assertThat(inkooppakket.getHeeftInkooporders()).containsOnly(inkooporderBack);
        assertThat(inkooporderBack.getHeeftInkooppakket()).isEqualTo(inkooppakket);

        inkooppakket.removeHeeftInkooporder(inkooporderBack);
        assertThat(inkooppakket.getHeeftInkooporders()).doesNotContain(inkooporderBack);
        assertThat(inkooporderBack.getHeeftInkooppakket()).isNull();

        inkooppakket.heeftInkooporders(new HashSet<>(Set.of(inkooporderBack)));
        assertThat(inkooppakket.getHeeftInkooporders()).containsOnly(inkooporderBack);
        assertThat(inkooporderBack.getHeeftInkooppakket()).isEqualTo(inkooppakket);

        inkooppakket.setHeeftInkooporders(new HashSet<>());
        assertThat(inkooppakket.getHeeftInkooporders()).doesNotContain(inkooporderBack);
        assertThat(inkooporderBack.getHeeftInkooppakket()).isNull();
    }
}
