package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KlimplantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KlimplantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Klimplant.class);
        Klimplant klimplant1 = getKlimplantSample1();
        Klimplant klimplant2 = new Klimplant();
        assertThat(klimplant1).isNotEqualTo(klimplant2);

        klimplant2.setId(klimplant1.getId());
        assertThat(klimplant1).isEqualTo(klimplant2);

        klimplant2 = getKlimplantSample2();
        assertThat(klimplant1).isNotEqualTo(klimplant2);
    }
}
