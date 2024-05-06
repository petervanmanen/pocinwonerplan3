package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ValutaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ValutaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Valuta.class);
        Valuta valuta1 = getValutaSample1();
        Valuta valuta2 = new Valuta();
        assertThat(valuta1).isNotEqualTo(valuta2);

        valuta2.setId(valuta1.getId());
        assertThat(valuta1).isEqualTo(valuta2);

        valuta2 = getValutaSample2();
        assertThat(valuta1).isNotEqualTo(valuta2);
    }
}
