package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.InonderzoekTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InonderzoekTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inonderzoek.class);
        Inonderzoek inonderzoek1 = getInonderzoekSample1();
        Inonderzoek inonderzoek2 = new Inonderzoek();
        assertThat(inonderzoek1).isNotEqualTo(inonderzoek2);

        inonderzoek2.setId(inonderzoek1.getId());
        assertThat(inonderzoek1).isEqualTo(inonderzoek2);

        inonderzoek2 = getInonderzoekSample2();
        assertThat(inonderzoek1).isNotEqualTo(inonderzoek2);
    }
}
