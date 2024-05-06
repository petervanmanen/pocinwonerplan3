package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OndersteunendwaterdeelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OndersteunendwaterdeelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ondersteunendwaterdeel.class);
        Ondersteunendwaterdeel ondersteunendwaterdeel1 = getOndersteunendwaterdeelSample1();
        Ondersteunendwaterdeel ondersteunendwaterdeel2 = new Ondersteunendwaterdeel();
        assertThat(ondersteunendwaterdeel1).isNotEqualTo(ondersteunendwaterdeel2);

        ondersteunendwaterdeel2.setId(ondersteunendwaterdeel1.getId());
        assertThat(ondersteunendwaterdeel1).isEqualTo(ondersteunendwaterdeel2);

        ondersteunendwaterdeel2 = getOndersteunendwaterdeelSample2();
        assertThat(ondersteunendwaterdeel1).isNotEqualTo(ondersteunendwaterdeel2);
    }
}
