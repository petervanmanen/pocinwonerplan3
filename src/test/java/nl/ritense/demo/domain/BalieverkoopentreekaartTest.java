package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BalieverkoopentreekaartTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BalieverkoopentreekaartTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Balieverkoopentreekaart.class);
        Balieverkoopentreekaart balieverkoopentreekaart1 = getBalieverkoopentreekaartSample1();
        Balieverkoopentreekaart balieverkoopentreekaart2 = new Balieverkoopentreekaart();
        assertThat(balieverkoopentreekaart1).isNotEqualTo(balieverkoopentreekaart2);

        balieverkoopentreekaart2.setId(balieverkoopentreekaart1.getId());
        assertThat(balieverkoopentreekaart1).isEqualTo(balieverkoopentreekaart2);

        balieverkoopentreekaart2 = getBalieverkoopentreekaartSample2();
        assertThat(balieverkoopentreekaart1).isNotEqualTo(balieverkoopentreekaart2);
    }
}
