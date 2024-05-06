package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.WinkelvloeroppervlakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WinkelvloeroppervlakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Winkelvloeroppervlak.class);
        Winkelvloeroppervlak winkelvloeroppervlak1 = getWinkelvloeroppervlakSample1();
        Winkelvloeroppervlak winkelvloeroppervlak2 = new Winkelvloeroppervlak();
        assertThat(winkelvloeroppervlak1).isNotEqualTo(winkelvloeroppervlak2);

        winkelvloeroppervlak2.setId(winkelvloeroppervlak1.getId());
        assertThat(winkelvloeroppervlak1).isEqualTo(winkelvloeroppervlak2);

        winkelvloeroppervlak2 = getWinkelvloeroppervlakSample2();
        assertThat(winkelvloeroppervlak1).isNotEqualTo(winkelvloeroppervlak2);
    }
}
