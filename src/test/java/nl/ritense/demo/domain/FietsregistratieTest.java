package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FietsregistratieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FietsregistratieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Fietsregistratie.class);
        Fietsregistratie fietsregistratie1 = getFietsregistratieSample1();
        Fietsregistratie fietsregistratie2 = new Fietsregistratie();
        assertThat(fietsregistratie1).isNotEqualTo(fietsregistratie2);

        fietsregistratie2.setId(fietsregistratie1.getId());
        assertThat(fietsregistratie1).isEqualTo(fietsregistratie2);

        fietsregistratie2 = getFietsregistratieSample2();
        assertThat(fietsregistratie1).isNotEqualTo(fietsregistratie2);
    }
}
