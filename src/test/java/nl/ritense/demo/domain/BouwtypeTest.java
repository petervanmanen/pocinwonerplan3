package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BouwtypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BouwtypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bouwtype.class);
        Bouwtype bouwtype1 = getBouwtypeSample1();
        Bouwtype bouwtype2 = new Bouwtype();
        assertThat(bouwtype1).isNotEqualTo(bouwtype2);

        bouwtype2.setId(bouwtype1.getId());
        assertThat(bouwtype1).isEqualTo(bouwtype2);

        bouwtype2 = getBouwtypeSample2();
        assertThat(bouwtype1).isNotEqualTo(bouwtype2);
    }
}
