package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.NietnatuurlijkpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NietnatuurlijkpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nietnatuurlijkpersoon.class);
        Nietnatuurlijkpersoon nietnatuurlijkpersoon1 = getNietnatuurlijkpersoonSample1();
        Nietnatuurlijkpersoon nietnatuurlijkpersoon2 = new Nietnatuurlijkpersoon();
        assertThat(nietnatuurlijkpersoon1).isNotEqualTo(nietnatuurlijkpersoon2);

        nietnatuurlijkpersoon2.setId(nietnatuurlijkpersoon1.getId());
        assertThat(nietnatuurlijkpersoon1).isEqualTo(nietnatuurlijkpersoon2);

        nietnatuurlijkpersoon2 = getNietnatuurlijkpersoonSample2();
        assertThat(nietnatuurlijkpersoon1).isNotEqualTo(nietnatuurlijkpersoon2);
    }
}
