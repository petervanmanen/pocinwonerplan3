package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KoopwoningenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KoopwoningenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Koopwoningen.class);
        Koopwoningen koopwoningen1 = getKoopwoningenSample1();
        Koopwoningen koopwoningen2 = new Koopwoningen();
        assertThat(koopwoningen1).isNotEqualTo(koopwoningen2);

        koopwoningen2.setId(koopwoningen1.getId());
        assertThat(koopwoningen1).isEqualTo(koopwoningen2);

        koopwoningen2 = getKoopwoningenSample2();
        assertThat(koopwoningen1).isNotEqualTo(koopwoningen2);
    }
}
