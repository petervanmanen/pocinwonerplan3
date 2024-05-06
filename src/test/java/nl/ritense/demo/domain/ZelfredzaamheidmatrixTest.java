package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ZelfredzaamheidmatrixTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ZelfredzaamheidmatrixTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zelfredzaamheidmatrix.class);
        Zelfredzaamheidmatrix zelfredzaamheidmatrix1 = getZelfredzaamheidmatrixSample1();
        Zelfredzaamheidmatrix zelfredzaamheidmatrix2 = new Zelfredzaamheidmatrix();
        assertThat(zelfredzaamheidmatrix1).isNotEqualTo(zelfredzaamheidmatrix2);

        zelfredzaamheidmatrix2.setId(zelfredzaamheidmatrix1.getId());
        assertThat(zelfredzaamheidmatrix1).isEqualTo(zelfredzaamheidmatrix2);

        zelfredzaamheidmatrix2 = getZelfredzaamheidmatrixSample2();
        assertThat(zelfredzaamheidmatrix1).isNotEqualTo(zelfredzaamheidmatrix2);
    }
}
