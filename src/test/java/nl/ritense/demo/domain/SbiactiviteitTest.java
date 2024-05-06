package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SbiactiviteitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SbiactiviteitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Sbiactiviteit.class);
        Sbiactiviteit sbiactiviteit1 = getSbiactiviteitSample1();
        Sbiactiviteit sbiactiviteit2 = new Sbiactiviteit();
        assertThat(sbiactiviteit1).isNotEqualTo(sbiactiviteit2);

        sbiactiviteit2.setId(sbiactiviteit1.getId());
        assertThat(sbiactiviteit1).isEqualTo(sbiactiviteit2);

        sbiactiviteit2 = getSbiactiviteitSample2();
        assertThat(sbiactiviteit1).isNotEqualTo(sbiactiviteit2);
    }
}
