package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MigratieingeschrevennatuurlijkpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MigratieingeschrevennatuurlijkpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Migratieingeschrevennatuurlijkpersoon.class);
        Migratieingeschrevennatuurlijkpersoon migratieingeschrevennatuurlijkpersoon1 = getMigratieingeschrevennatuurlijkpersoonSample1();
        Migratieingeschrevennatuurlijkpersoon migratieingeschrevennatuurlijkpersoon2 = new Migratieingeschrevennatuurlijkpersoon();
        assertThat(migratieingeschrevennatuurlijkpersoon1).isNotEqualTo(migratieingeschrevennatuurlijkpersoon2);

        migratieingeschrevennatuurlijkpersoon2.setId(migratieingeschrevennatuurlijkpersoon1.getId());
        assertThat(migratieingeschrevennatuurlijkpersoon1).isEqualTo(migratieingeschrevennatuurlijkpersoon2);

        migratieingeschrevennatuurlijkpersoon2 = getMigratieingeschrevennatuurlijkpersoonSample2();
        assertThat(migratieingeschrevennatuurlijkpersoon1).isNotEqualTo(migratieingeschrevennatuurlijkpersoon2);
    }
}
