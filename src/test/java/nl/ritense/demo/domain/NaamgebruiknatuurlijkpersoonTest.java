package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.NaamgebruiknatuurlijkpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NaamgebruiknatuurlijkpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Naamgebruiknatuurlijkpersoon.class);
        Naamgebruiknatuurlijkpersoon naamgebruiknatuurlijkpersoon1 = getNaamgebruiknatuurlijkpersoonSample1();
        Naamgebruiknatuurlijkpersoon naamgebruiknatuurlijkpersoon2 = new Naamgebruiknatuurlijkpersoon();
        assertThat(naamgebruiknatuurlijkpersoon1).isNotEqualTo(naamgebruiknatuurlijkpersoon2);

        naamgebruiknatuurlijkpersoon2.setId(naamgebruiknatuurlijkpersoon1.getId());
        assertThat(naamgebruiknatuurlijkpersoon1).isEqualTo(naamgebruiknatuurlijkpersoon2);

        naamgebruiknatuurlijkpersoon2 = getNaamgebruiknatuurlijkpersoonSample2();
        assertThat(naamgebruiknatuurlijkpersoon1).isNotEqualTo(naamgebruiknatuurlijkpersoon2);
    }
}
