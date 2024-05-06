package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SplitsingstekeningreferentieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SplitsingstekeningreferentieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Splitsingstekeningreferentie.class);
        Splitsingstekeningreferentie splitsingstekeningreferentie1 = getSplitsingstekeningreferentieSample1();
        Splitsingstekeningreferentie splitsingstekeningreferentie2 = new Splitsingstekeningreferentie();
        assertThat(splitsingstekeningreferentie1).isNotEqualTo(splitsingstekeningreferentie2);

        splitsingstekeningreferentie2.setId(splitsingstekeningreferentie1.getId());
        assertThat(splitsingstekeningreferentie1).isEqualTo(splitsingstekeningreferentie2);

        splitsingstekeningreferentie2 = getSplitsingstekeningreferentieSample2();
        assertThat(splitsingstekeningreferentie1).isNotEqualTo(splitsingstekeningreferentie2);
    }
}
