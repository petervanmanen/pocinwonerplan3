package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PompTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PompTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pomp.class);
        Pomp pomp1 = getPompSample1();
        Pomp pomp2 = new Pomp();
        assertThat(pomp1).isNotEqualTo(pomp2);

        pomp2.setId(pomp1.getId());
        assertThat(pomp1).isEqualTo(pomp2);

        pomp2 = getPompSample2();
        assertThat(pomp1).isNotEqualTo(pomp2);
    }
}
