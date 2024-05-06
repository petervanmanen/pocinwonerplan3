package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.NaheffingTestSamples.*;
import static nl.ritense.demo.domain.ParkeerscanTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NaheffingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Naheffing.class);
        Naheffing naheffing1 = getNaheffingSample1();
        Naheffing naheffing2 = new Naheffing();
        assertThat(naheffing1).isNotEqualTo(naheffing2);

        naheffing2.setId(naheffing1.getId());
        assertThat(naheffing1).isEqualTo(naheffing2);

        naheffing2 = getNaheffingSample2();
        assertThat(naheffing1).isNotEqualTo(naheffing2);
    }

    @Test
    void komtvoortuitParkeerscanTest() throws Exception {
        Naheffing naheffing = getNaheffingRandomSampleGenerator();
        Parkeerscan parkeerscanBack = getParkeerscanRandomSampleGenerator();

        naheffing.setKomtvoortuitParkeerscan(parkeerscanBack);
        assertThat(naheffing.getKomtvoortuitParkeerscan()).isEqualTo(parkeerscanBack);
        assertThat(parkeerscanBack.getKomtvoortuitNaheffing()).isEqualTo(naheffing);

        naheffing.komtvoortuitParkeerscan(null);
        assertThat(naheffing.getKomtvoortuitParkeerscan()).isNull();
        assertThat(parkeerscanBack.getKomtvoortuitNaheffing()).isNull();
    }
}
