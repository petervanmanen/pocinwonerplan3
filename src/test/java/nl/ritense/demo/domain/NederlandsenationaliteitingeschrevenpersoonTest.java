package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.NederlandsenationaliteitingeschrevenpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NederlandsenationaliteitingeschrevenpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nederlandsenationaliteitingeschrevenpersoon.class);
        Nederlandsenationaliteitingeschrevenpersoon nederlandsenationaliteitingeschrevenpersoon1 =
            getNederlandsenationaliteitingeschrevenpersoonSample1();
        Nederlandsenationaliteitingeschrevenpersoon nederlandsenationaliteitingeschrevenpersoon2 =
            new Nederlandsenationaliteitingeschrevenpersoon();
        assertThat(nederlandsenationaliteitingeschrevenpersoon1).isNotEqualTo(nederlandsenationaliteitingeschrevenpersoon2);

        nederlandsenationaliteitingeschrevenpersoon2.setId(nederlandsenationaliteitingeschrevenpersoon1.getId());
        assertThat(nederlandsenationaliteitingeschrevenpersoon1).isEqualTo(nederlandsenationaliteitingeschrevenpersoon2);

        nederlandsenationaliteitingeschrevenpersoon2 = getNederlandsenationaliteitingeschrevenpersoonSample2();
        assertThat(nederlandsenationaliteitingeschrevenpersoon1).isNotEqualTo(nederlandsenationaliteitingeschrevenpersoon2);
    }
}
