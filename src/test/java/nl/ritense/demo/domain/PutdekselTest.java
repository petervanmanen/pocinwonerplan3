package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PutdekselTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PutdekselTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Putdeksel.class);
        Putdeksel putdeksel1 = getPutdekselSample1();
        Putdeksel putdeksel2 = new Putdeksel();
        assertThat(putdeksel1).isNotEqualTo(putdeksel2);

        putdeksel2.setId(putdeksel1.getId());
        assertThat(putdeksel1).isEqualTo(putdeksel2);

        putdeksel2 = getPutdekselSample2();
        assertThat(putdeksel1).isNotEqualTo(putdeksel2);
    }
}
