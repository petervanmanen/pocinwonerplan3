package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.StrooirouteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StrooirouteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Strooiroute.class);
        Strooiroute strooiroute1 = getStrooirouteSample1();
        Strooiroute strooiroute2 = new Strooiroute();
        assertThat(strooiroute1).isNotEqualTo(strooiroute2);

        strooiroute2.setId(strooiroute1.getId());
        assertThat(strooiroute1).isEqualTo(strooiroute2);

        strooiroute2 = getStrooirouteSample2();
        assertThat(strooiroute1).isNotEqualTo(strooiroute2);
    }
}
