package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.RekeningnummerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RekeningnummerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rekeningnummer.class);
        Rekeningnummer rekeningnummer1 = getRekeningnummerSample1();
        Rekeningnummer rekeningnummer2 = new Rekeningnummer();
        assertThat(rekeningnummer1).isNotEqualTo(rekeningnummer2);

        rekeningnummer2.setId(rekeningnummer1.getId());
        assertThat(rekeningnummer1).isEqualTo(rekeningnummer2);

        rekeningnummer2 = getRekeningnummerSample2();
        assertThat(rekeningnummer1).isNotEqualTo(rekeningnummer2);
    }
}
