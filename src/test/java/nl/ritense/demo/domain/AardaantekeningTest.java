package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AardaantekeningTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AardaantekeningTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aardaantekening.class);
        Aardaantekening aardaantekening1 = getAardaantekeningSample1();
        Aardaantekening aardaantekening2 = new Aardaantekening();
        assertThat(aardaantekening1).isNotEqualTo(aardaantekening2);

        aardaantekening2.setId(aardaantekening1.getId());
        assertThat(aardaantekening1).isEqualTo(aardaantekening2);

        aardaantekening2 = getAardaantekeningSample2();
        assertThat(aardaantekening1).isNotEqualTo(aardaantekening2);
    }
}
