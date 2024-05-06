package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FotoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FotoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Foto.class);
        Foto foto1 = getFotoSample1();
        Foto foto2 = new Foto();
        assertThat(foto1).isNotEqualTo(foto2);

        foto2.setId(foto1.getId());
        assertThat(foto1).isEqualTo(foto2);

        foto2 = getFotoSample2();
        assertThat(foto1).isNotEqualTo(foto2);
    }
}
