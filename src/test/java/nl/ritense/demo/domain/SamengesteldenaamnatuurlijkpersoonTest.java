package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SamengesteldenaamnatuurlijkpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SamengesteldenaamnatuurlijkpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Samengesteldenaamnatuurlijkpersoon.class);
        Samengesteldenaamnatuurlijkpersoon samengesteldenaamnatuurlijkpersoon1 = getSamengesteldenaamnatuurlijkpersoonSample1();
        Samengesteldenaamnatuurlijkpersoon samengesteldenaamnatuurlijkpersoon2 = new Samengesteldenaamnatuurlijkpersoon();
        assertThat(samengesteldenaamnatuurlijkpersoon1).isNotEqualTo(samengesteldenaamnatuurlijkpersoon2);

        samengesteldenaamnatuurlijkpersoon2.setId(samengesteldenaamnatuurlijkpersoon1.getId());
        assertThat(samengesteldenaamnatuurlijkpersoon1).isEqualTo(samengesteldenaamnatuurlijkpersoon2);

        samengesteldenaamnatuurlijkpersoon2 = getSamengesteldenaamnatuurlijkpersoonSample2();
        assertThat(samengesteldenaamnatuurlijkpersoon1).isNotEqualTo(samengesteldenaamnatuurlijkpersoon2);
    }
}
