package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GeboorteingeschrevennatuurlijkpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GeboorteingeschrevennatuurlijkpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Geboorteingeschrevennatuurlijkpersoon.class);
        Geboorteingeschrevennatuurlijkpersoon geboorteingeschrevennatuurlijkpersoon1 = getGeboorteingeschrevennatuurlijkpersoonSample1();
        Geboorteingeschrevennatuurlijkpersoon geboorteingeschrevennatuurlijkpersoon2 = new Geboorteingeschrevennatuurlijkpersoon();
        assertThat(geboorteingeschrevennatuurlijkpersoon1).isNotEqualTo(geboorteingeschrevennatuurlijkpersoon2);

        geboorteingeschrevennatuurlijkpersoon2.setId(geboorteingeschrevennatuurlijkpersoon1.getId());
        assertThat(geboorteingeschrevennatuurlijkpersoon1).isEqualTo(geboorteingeschrevennatuurlijkpersoon2);

        geboorteingeschrevennatuurlijkpersoon2 = getGeboorteingeschrevennatuurlijkpersoonSample2();
        assertThat(geboorteingeschrevennatuurlijkpersoon1).isNotEqualTo(geboorteingeschrevennatuurlijkpersoon2);
    }
}
