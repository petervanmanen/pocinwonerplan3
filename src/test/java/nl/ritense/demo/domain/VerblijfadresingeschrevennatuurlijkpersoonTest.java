package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerblijfadresingeschrevennatuurlijkpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerblijfadresingeschrevennatuurlijkpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verblijfadresingeschrevennatuurlijkpersoon.class);
        Verblijfadresingeschrevennatuurlijkpersoon verblijfadresingeschrevennatuurlijkpersoon1 =
            getVerblijfadresingeschrevennatuurlijkpersoonSample1();
        Verblijfadresingeschrevennatuurlijkpersoon verblijfadresingeschrevennatuurlijkpersoon2 =
            new Verblijfadresingeschrevennatuurlijkpersoon();
        assertThat(verblijfadresingeschrevennatuurlijkpersoon1).isNotEqualTo(verblijfadresingeschrevennatuurlijkpersoon2);

        verblijfadresingeschrevennatuurlijkpersoon2.setId(verblijfadresingeschrevennatuurlijkpersoon1.getId());
        assertThat(verblijfadresingeschrevennatuurlijkpersoon1).isEqualTo(verblijfadresingeschrevennatuurlijkpersoon2);

        verblijfadresingeschrevennatuurlijkpersoon2 = getVerblijfadresingeschrevennatuurlijkpersoonSample2();
        assertThat(verblijfadresingeschrevennatuurlijkpersoon1).isNotEqualTo(verblijfadresingeschrevennatuurlijkpersoon2);
    }
}
