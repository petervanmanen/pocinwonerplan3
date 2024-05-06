package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BetaalmomentTestSamples.*;
import static nl.ritense.demo.domain.SubsidiecomponentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BetaalmomentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Betaalmoment.class);
        Betaalmoment betaalmoment1 = getBetaalmomentSample1();
        Betaalmoment betaalmoment2 = new Betaalmoment();
        assertThat(betaalmoment1).isNotEqualTo(betaalmoment2);

        betaalmoment2.setId(betaalmoment1.getId());
        assertThat(betaalmoment1).isEqualTo(betaalmoment2);

        betaalmoment2 = getBetaalmomentSample2();
        assertThat(betaalmoment1).isNotEqualTo(betaalmoment2);
    }

    @Test
    void heeftSubsidiecomponentTest() throws Exception {
        Betaalmoment betaalmoment = getBetaalmomentRandomSampleGenerator();
        Subsidiecomponent subsidiecomponentBack = getSubsidiecomponentRandomSampleGenerator();

        betaalmoment.setHeeftSubsidiecomponent(subsidiecomponentBack);
        assertThat(betaalmoment.getHeeftSubsidiecomponent()).isEqualTo(subsidiecomponentBack);

        betaalmoment.heeftSubsidiecomponent(null);
        assertThat(betaalmoment.getHeeftSubsidiecomponent()).isNull();
    }
}
