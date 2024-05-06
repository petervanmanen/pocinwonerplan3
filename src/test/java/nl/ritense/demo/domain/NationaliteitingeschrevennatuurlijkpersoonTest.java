package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.NationaliteitingeschrevennatuurlijkpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NationaliteitingeschrevennatuurlijkpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nationaliteitingeschrevennatuurlijkpersoon.class);
        Nationaliteitingeschrevennatuurlijkpersoon nationaliteitingeschrevennatuurlijkpersoon1 =
            getNationaliteitingeschrevennatuurlijkpersoonSample1();
        Nationaliteitingeschrevennatuurlijkpersoon nationaliteitingeschrevennatuurlijkpersoon2 =
            new Nationaliteitingeschrevennatuurlijkpersoon();
        assertThat(nationaliteitingeschrevennatuurlijkpersoon1).isNotEqualTo(nationaliteitingeschrevennatuurlijkpersoon2);

        nationaliteitingeschrevennatuurlijkpersoon2.setId(nationaliteitingeschrevennatuurlijkpersoon1.getId());
        assertThat(nationaliteitingeschrevennatuurlijkpersoon1).isEqualTo(nationaliteitingeschrevennatuurlijkpersoon2);

        nationaliteitingeschrevennatuurlijkpersoon2 = getNationaliteitingeschrevennatuurlijkpersoonSample2();
        assertThat(nationaliteitingeschrevennatuurlijkpersoon1).isNotEqualTo(nationaliteitingeschrevennatuurlijkpersoon2);
    }
}
