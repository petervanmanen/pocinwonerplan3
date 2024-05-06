package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KostenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KostenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kosten.class);
        Kosten kosten1 = getKostenSample1();
        Kosten kosten2 = new Kosten();
        assertThat(kosten1).isNotEqualTo(kosten2);

        kosten2.setId(kosten1.getId());
        assertThat(kosten1).isEqualTo(kosten2);

        kosten2 = getKostenSample2();
        assertThat(kosten1).isNotEqualTo(kosten2);
    }
}
