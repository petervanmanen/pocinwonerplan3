package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerblijfsrechtingeschrevennatuurlijkpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerblijfsrechtingeschrevennatuurlijkpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verblijfsrechtingeschrevennatuurlijkpersoon.class);
        Verblijfsrechtingeschrevennatuurlijkpersoon verblijfsrechtingeschrevennatuurlijkpersoon1 =
            getVerblijfsrechtingeschrevennatuurlijkpersoonSample1();
        Verblijfsrechtingeschrevennatuurlijkpersoon verblijfsrechtingeschrevennatuurlijkpersoon2 =
            new Verblijfsrechtingeschrevennatuurlijkpersoon();
        assertThat(verblijfsrechtingeschrevennatuurlijkpersoon1).isNotEqualTo(verblijfsrechtingeschrevennatuurlijkpersoon2);

        verblijfsrechtingeschrevennatuurlijkpersoon2.setId(verblijfsrechtingeschrevennatuurlijkpersoon1.getId());
        assertThat(verblijfsrechtingeschrevennatuurlijkpersoon1).isEqualTo(verblijfsrechtingeschrevennatuurlijkpersoon2);

        verblijfsrechtingeschrevennatuurlijkpersoon2 = getVerblijfsrechtingeschrevennatuurlijkpersoonSample2();
        assertThat(verblijfsrechtingeschrevennatuurlijkpersoon1).isNotEqualTo(verblijfsrechtingeschrevennatuurlijkpersoon2);
    }
}
