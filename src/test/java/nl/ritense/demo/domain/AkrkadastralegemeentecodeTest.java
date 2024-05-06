package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AkrkadastralegemeentecodeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AkrkadastralegemeentecodeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Akrkadastralegemeentecode.class);
        Akrkadastralegemeentecode akrkadastralegemeentecode1 = getAkrkadastralegemeentecodeSample1();
        Akrkadastralegemeentecode akrkadastralegemeentecode2 = new Akrkadastralegemeentecode();
        assertThat(akrkadastralegemeentecode1).isNotEqualTo(akrkadastralegemeentecode2);

        akrkadastralegemeentecode2.setId(akrkadastralegemeentecode1.getId());
        assertThat(akrkadastralegemeentecode1).isEqualTo(akrkadastralegemeentecode2);

        akrkadastralegemeentecode2 = getAkrkadastralegemeentecodeSample2();
        assertThat(akrkadastralegemeentecode1).isNotEqualTo(akrkadastralegemeentecode2);
    }
}
