package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.NadaanvullingbrpTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NadaanvullingbrpTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nadaanvullingbrp.class);
        Nadaanvullingbrp nadaanvullingbrp1 = getNadaanvullingbrpSample1();
        Nadaanvullingbrp nadaanvullingbrp2 = new Nadaanvullingbrp();
        assertThat(nadaanvullingbrp1).isNotEqualTo(nadaanvullingbrp2);

        nadaanvullingbrp2.setId(nadaanvullingbrp1.getId());
        assertThat(nadaanvullingbrp1).isEqualTo(nadaanvullingbrp2);

        nadaanvullingbrp2 = getNadaanvullingbrpSample2();
        assertThat(nadaanvullingbrp1).isNotEqualTo(nadaanvullingbrp2);
    }
}
