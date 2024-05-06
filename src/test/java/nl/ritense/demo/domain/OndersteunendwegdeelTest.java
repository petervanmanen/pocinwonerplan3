package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OndersteunendwegdeelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OndersteunendwegdeelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ondersteunendwegdeel.class);
        Ondersteunendwegdeel ondersteunendwegdeel1 = getOndersteunendwegdeelSample1();
        Ondersteunendwegdeel ondersteunendwegdeel2 = new Ondersteunendwegdeel();
        assertThat(ondersteunendwegdeel1).isNotEqualTo(ondersteunendwegdeel2);

        ondersteunendwegdeel2.setId(ondersteunendwegdeel1.getId());
        assertThat(ondersteunendwegdeel1).isEqualTo(ondersteunendwegdeel2);

        ondersteunendwegdeel2 = getOndersteunendwegdeelSample2();
        assertThat(ondersteunendwegdeel1).isNotEqualTo(ondersteunendwegdeel2);
    }
}
