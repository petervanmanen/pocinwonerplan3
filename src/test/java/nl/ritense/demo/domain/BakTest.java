package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BakTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bak.class);
        Bak bak1 = getBakSample1();
        Bak bak2 = new Bak();
        assertThat(bak1).isNotEqualTo(bak2);

        bak2.setId(bak1.getId());
        assertThat(bak1).isEqualTo(bak2);

        bak2 = getBakSample2();
        assertThat(bak1).isNotEqualTo(bak2);
    }
}
