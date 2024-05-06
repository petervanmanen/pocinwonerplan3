package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BouwstijlTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BouwstijlTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bouwstijl.class);
        Bouwstijl bouwstijl1 = getBouwstijlSample1();
        Bouwstijl bouwstijl2 = new Bouwstijl();
        assertThat(bouwstijl1).isNotEqualTo(bouwstijl2);

        bouwstijl2.setId(bouwstijl1.getId());
        assertThat(bouwstijl1).isEqualTo(bouwstijl2);

        bouwstijl2 = getBouwstijlSample2();
        assertThat(bouwstijl1).isNotEqualTo(bouwstijl2);
    }
}
