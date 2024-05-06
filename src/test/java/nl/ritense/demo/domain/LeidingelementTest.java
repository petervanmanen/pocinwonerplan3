package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LeidingelementTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LeidingelementTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Leidingelement.class);
        Leidingelement leidingelement1 = getLeidingelementSample1();
        Leidingelement leidingelement2 = new Leidingelement();
        assertThat(leidingelement1).isNotEqualTo(leidingelement2);

        leidingelement2.setId(leidingelement1.getId());
        assertThat(leidingelement1).isEqualTo(leidingelement2);

        leidingelement2 = getLeidingelementSample2();
        assertThat(leidingelement1).isNotEqualTo(leidingelement2);
    }
}
