package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KolkTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KolkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kolk.class);
        Kolk kolk1 = getKolkSample1();
        Kolk kolk2 = new Kolk();
        assertThat(kolk1).isNotEqualTo(kolk2);

        kolk2.setId(kolk1.getId());
        assertThat(kolk1).isEqualTo(kolk2);

        kolk2 = getKolkSample2();
        assertThat(kolk1).isNotEqualTo(kolk2);
    }
}
