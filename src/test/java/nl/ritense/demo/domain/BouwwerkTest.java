package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BouwwerkTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BouwwerkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bouwwerk.class);
        Bouwwerk bouwwerk1 = getBouwwerkSample1();
        Bouwwerk bouwwerk2 = new Bouwwerk();
        assertThat(bouwwerk1).isNotEqualTo(bouwwerk2);

        bouwwerk2.setId(bouwwerk1.getId());
        assertThat(bouwwerk1).isEqualTo(bouwwerk2);

        bouwwerk2 = getBouwwerkSample2();
        assertThat(bouwwerk1).isNotEqualTo(bouwwerk2);
    }
}
