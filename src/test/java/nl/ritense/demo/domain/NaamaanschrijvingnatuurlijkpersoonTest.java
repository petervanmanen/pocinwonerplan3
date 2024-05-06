package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.NaamaanschrijvingnatuurlijkpersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NaamaanschrijvingnatuurlijkpersoonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Naamaanschrijvingnatuurlijkpersoon.class);
        Naamaanschrijvingnatuurlijkpersoon naamaanschrijvingnatuurlijkpersoon1 = getNaamaanschrijvingnatuurlijkpersoonSample1();
        Naamaanschrijvingnatuurlijkpersoon naamaanschrijvingnatuurlijkpersoon2 = new Naamaanschrijvingnatuurlijkpersoon();
        assertThat(naamaanschrijvingnatuurlijkpersoon1).isNotEqualTo(naamaanschrijvingnatuurlijkpersoon2);

        naamaanschrijvingnatuurlijkpersoon2.setId(naamaanschrijvingnatuurlijkpersoon1.getId());
        assertThat(naamaanschrijvingnatuurlijkpersoon1).isEqualTo(naamaanschrijvingnatuurlijkpersoon2);

        naamaanschrijvingnatuurlijkpersoon2 = getNaamaanschrijvingnatuurlijkpersoonSample2();
        assertThat(naamaanschrijvingnatuurlijkpersoon1).isNotEqualTo(naamaanschrijvingnatuurlijkpersoon2);
    }
}
