package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.NaamnatuurlijkpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NaamnatuurlijkpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Naamnatuurlijkpersoon.class);
        Naamnatuurlijkpersoon naamnatuurlijkpersoon1 = getNaamnatuurlijkpersoonSample1();
        Naamnatuurlijkpersoon naamnatuurlijkpersoon2 = new Naamnatuurlijkpersoon();
        assertThat(naamnatuurlijkpersoon1).isNotEqualTo(naamnatuurlijkpersoon2);

        naamnatuurlijkpersoon2.setId(naamnatuurlijkpersoon1.getId());
        assertThat(naamnatuurlijkpersoon1).isEqualTo(naamnatuurlijkpersoon2);

        naamnatuurlijkpersoon2 = getNaamnatuurlijkpersoonSample2();
        assertThat(naamnatuurlijkpersoon1).isNotEqualTo(naamnatuurlijkpersoon2);
    }
}
