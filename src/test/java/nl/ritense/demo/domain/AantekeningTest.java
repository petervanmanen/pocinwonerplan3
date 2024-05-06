package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AantekeningTestSamples.*;
import static nl.ritense.demo.domain.TenaamstellingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AantekeningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aantekening.class);
        Aantekening aantekening1 = getAantekeningSample1();
        Aantekening aantekening2 = new Aantekening();
        assertThat(aantekening1).isNotEqualTo(aantekening2);

        aantekening2.setId(aantekening1.getId());
        assertThat(aantekening1).isEqualTo(aantekening2);

        aantekening2 = getAantekeningSample2();
        assertThat(aantekening1).isNotEqualTo(aantekening2);
    }

    @Test
    void emptyTenaamstellingTest() throws Exception {
        Aantekening aantekening = getAantekeningRandomSampleGenerator();
        Tenaamstelling tenaamstellingBack = getTenaamstellingRandomSampleGenerator();

        aantekening.setEmptyTenaamstelling(tenaamstellingBack);
        assertThat(aantekening.getEmptyTenaamstelling()).isEqualTo(tenaamstellingBack);

        aantekening.emptyTenaamstelling(null);
        assertThat(aantekening.getEmptyTenaamstelling()).isNull();
    }
}
