package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BergingsbassinTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BergingsbassinTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bergingsbassin.class);
        Bergingsbassin bergingsbassin1 = getBergingsbassinSample1();
        Bergingsbassin bergingsbassin2 = new Bergingsbassin();
        assertThat(bergingsbassin1).isNotEqualTo(bergingsbassin2);

        bergingsbassin2.setId(bergingsbassin1.getId());
        assertThat(bergingsbassin1).isEqualTo(bergingsbassin2);

        bergingsbassin2 = getBergingsbassinSample2();
        assertThat(bergingsbassin1).isNotEqualTo(bergingsbassin2);
    }
}
