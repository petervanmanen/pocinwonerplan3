package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.NationaliteitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NationaliteitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nationaliteit.class);
        Nationaliteit nationaliteit1 = getNationaliteitSample1();
        Nationaliteit nationaliteit2 = new Nationaliteit();
        assertThat(nationaliteit1).isNotEqualTo(nationaliteit2);

        nationaliteit2.setId(nationaliteit1.getId());
        assertThat(nationaliteit1).isEqualTo(nationaliteit2);

        nationaliteit2 = getNationaliteitSample2();
        assertThat(nationaliteit1).isNotEqualTo(nationaliteit2);
    }
}
