package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KponstaanuitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KponstaanuitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kponstaanuit.class);
        Kponstaanuit kponstaanuit1 = getKponstaanuitSample1();
        Kponstaanuit kponstaanuit2 = new Kponstaanuit();
        assertThat(kponstaanuit1).isNotEqualTo(kponstaanuit2);

        kponstaanuit2.setId(kponstaanuit1.getId());
        assertThat(kponstaanuit1).isEqualTo(kponstaanuit2);

        kponstaanuit2 = getKponstaanuitSample2();
        assertThat(kponstaanuit1).isNotEqualTo(kponstaanuit2);
    }
}
