package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DigitaalbestandTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DigitaalbestandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Digitaalbestand.class);
        Digitaalbestand digitaalbestand1 = getDigitaalbestandSample1();
        Digitaalbestand digitaalbestand2 = new Digitaalbestand();
        assertThat(digitaalbestand1).isNotEqualTo(digitaalbestand2);

        digitaalbestand2.setId(digitaalbestand1.getId());
        assertThat(digitaalbestand1).isEqualTo(digitaalbestand2);

        digitaalbestand2 = getDigitaalbestandSample2();
        assertThat(digitaalbestand1).isNotEqualTo(digitaalbestand2);
    }
}
