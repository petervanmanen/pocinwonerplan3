package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.TelefoniegegevensTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TelefoniegegevensTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Telefoniegegevens.class);
        Telefoniegegevens telefoniegegevens1 = getTelefoniegegevensSample1();
        Telefoniegegevens telefoniegegevens2 = new Telefoniegegevens();
        assertThat(telefoniegegevens1).isNotEqualTo(telefoniegegevens2);

        telefoniegegevens2.setId(telefoniegegevens1.getId());
        assertThat(telefoniegegevens1).isEqualTo(telefoniegegevens2);

        telefoniegegevens2 = getTelefoniegegevensSample2();
        assertThat(telefoniegegevens1).isNotEqualTo(telefoniegegevens2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Telefoniegegevens telefoniegegevens = new Telefoniegegevens();
        assertThat(telefoniegegevens.hashCode()).isZero();

        Telefoniegegevens telefoniegegevens1 = getTelefoniegegevensSample1();
        telefoniegegevens.setId(telefoniegegevens1.getId());
        assertThat(telefoniegegevens).hasSameHashCodeAs(telefoniegegevens1);
    }
}
