package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EntreekaartTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntreekaartTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entreekaart.class);
        Entreekaart entreekaart1 = getEntreekaartSample1();
        Entreekaart entreekaart2 = new Entreekaart();
        assertThat(entreekaart1).isNotEqualTo(entreekaart2);

        entreekaart2.setId(entreekaart1.getId());
        assertThat(entreekaart1).isEqualTo(entreekaart2);

        entreekaart2 = getEntreekaartSample2();
        assertThat(entreekaart1).isNotEqualTo(entreekaart2);
    }
}
