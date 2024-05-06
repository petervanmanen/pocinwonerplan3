package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.RedenverliesnationaliteitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RedenverliesnationaliteitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Redenverliesnationaliteit.class);
        Redenverliesnationaliteit redenverliesnationaliteit1 = getRedenverliesnationaliteitSample1();
        Redenverliesnationaliteit redenverliesnationaliteit2 = new Redenverliesnationaliteit();
        assertThat(redenverliesnationaliteit1).isNotEqualTo(redenverliesnationaliteit2);

        redenverliesnationaliteit2.setId(redenverliesnationaliteit1.getId());
        assertThat(redenverliesnationaliteit1).isEqualTo(redenverliesnationaliteit2);

        redenverliesnationaliteit2 = getRedenverliesnationaliteitSample2();
        assertThat(redenverliesnationaliteit1).isNotEqualTo(redenverliesnationaliteit2);
    }
}
