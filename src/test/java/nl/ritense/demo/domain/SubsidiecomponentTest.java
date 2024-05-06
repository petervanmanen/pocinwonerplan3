package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BetaalmomentTestSamples.*;
import static nl.ritense.demo.domain.KostenplaatsTestSamples.*;
import static nl.ritense.demo.domain.SubsidiecomponentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubsidiecomponentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subsidiecomponent.class);
        Subsidiecomponent subsidiecomponent1 = getSubsidiecomponentSample1();
        Subsidiecomponent subsidiecomponent2 = new Subsidiecomponent();
        assertThat(subsidiecomponent1).isNotEqualTo(subsidiecomponent2);

        subsidiecomponent2.setId(subsidiecomponent1.getId());
        assertThat(subsidiecomponent1).isEqualTo(subsidiecomponent2);

        subsidiecomponent2 = getSubsidiecomponentSample2();
        assertThat(subsidiecomponent1).isNotEqualTo(subsidiecomponent2);
    }

    @Test
    void heeftBetaalmomentTest() throws Exception {
        Subsidiecomponent subsidiecomponent = getSubsidiecomponentRandomSampleGenerator();
        Betaalmoment betaalmomentBack = getBetaalmomentRandomSampleGenerator();

        subsidiecomponent.addHeeftBetaalmoment(betaalmomentBack);
        assertThat(subsidiecomponent.getHeeftBetaalmoments()).containsOnly(betaalmomentBack);
        assertThat(betaalmomentBack.getHeeftSubsidiecomponent()).isEqualTo(subsidiecomponent);

        subsidiecomponent.removeHeeftBetaalmoment(betaalmomentBack);
        assertThat(subsidiecomponent.getHeeftBetaalmoments()).doesNotContain(betaalmomentBack);
        assertThat(betaalmomentBack.getHeeftSubsidiecomponent()).isNull();

        subsidiecomponent.heeftBetaalmoments(new HashSet<>(Set.of(betaalmomentBack)));
        assertThat(subsidiecomponent.getHeeftBetaalmoments()).containsOnly(betaalmomentBack);
        assertThat(betaalmomentBack.getHeeftSubsidiecomponent()).isEqualTo(subsidiecomponent);

        subsidiecomponent.setHeeftBetaalmoments(new HashSet<>());
        assertThat(subsidiecomponent.getHeeftBetaalmoments()).doesNotContain(betaalmomentBack);
        assertThat(betaalmomentBack.getHeeftSubsidiecomponent()).isNull();
    }

    @Test
    void heeftKostenplaatsTest() throws Exception {
        Subsidiecomponent subsidiecomponent = getSubsidiecomponentRandomSampleGenerator();
        Kostenplaats kostenplaatsBack = getKostenplaatsRandomSampleGenerator();

        subsidiecomponent.setHeeftKostenplaats(kostenplaatsBack);
        assertThat(subsidiecomponent.getHeeftKostenplaats()).isEqualTo(kostenplaatsBack);

        subsidiecomponent.heeftKostenplaats(null);
        assertThat(subsidiecomponent.getHeeftKostenplaats()).isNull();
    }
}
