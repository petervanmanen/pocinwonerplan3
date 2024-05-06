package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.NatuurlijkpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NatuurlijkpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Natuurlijkpersoon.class);
        Natuurlijkpersoon natuurlijkpersoon1 = getNatuurlijkpersoonSample1();
        Natuurlijkpersoon natuurlijkpersoon2 = new Natuurlijkpersoon();
        assertThat(natuurlijkpersoon1).isNotEqualTo(natuurlijkpersoon2);

        natuurlijkpersoon2.setId(natuurlijkpersoon1.getId());
        assertThat(natuurlijkpersoon1).isEqualTo(natuurlijkpersoon2);

        natuurlijkpersoon2 = getNatuurlijkpersoonSample2();
        assertThat(natuurlijkpersoon1).isNotEqualTo(natuurlijkpersoon2);
    }
}
