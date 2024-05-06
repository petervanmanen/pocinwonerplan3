package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OverlijdeningeschrevennatuurlijkpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OverlijdeningeschrevennatuurlijkpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Overlijdeningeschrevennatuurlijkpersoon.class);
        Overlijdeningeschrevennatuurlijkpersoon overlijdeningeschrevennatuurlijkpersoon1 =
            getOverlijdeningeschrevennatuurlijkpersoonSample1();
        Overlijdeningeschrevennatuurlijkpersoon overlijdeningeschrevennatuurlijkpersoon2 = new Overlijdeningeschrevennatuurlijkpersoon();
        assertThat(overlijdeningeschrevennatuurlijkpersoon1).isNotEqualTo(overlijdeningeschrevennatuurlijkpersoon2);

        overlijdeningeschrevennatuurlijkpersoon2.setId(overlijdeningeschrevennatuurlijkpersoon1.getId());
        assertThat(overlijdeningeschrevennatuurlijkpersoon1).isEqualTo(overlijdeningeschrevennatuurlijkpersoon2);

        overlijdeningeschrevennatuurlijkpersoon2 = getOverlijdeningeschrevennatuurlijkpersoonSample2();
        assertThat(overlijdeningeschrevennatuurlijkpersoon1).isNotEqualTo(overlijdeningeschrevennatuurlijkpersoon2);
    }
}
