package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BenoemdterreinTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BenoemdterreinTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Benoemdterrein.class);
        Benoemdterrein benoemdterrein1 = getBenoemdterreinSample1();
        Benoemdterrein benoemdterrein2 = new Benoemdterrein();
        assertThat(benoemdterrein1).isNotEqualTo(benoemdterrein2);

        benoemdterrein2.setId(benoemdterrein1.getId());
        assertThat(benoemdterrein1).isEqualTo(benoemdterrein2);

        benoemdterrein2 = getBenoemdterreinSample2();
        assertThat(benoemdterrein1).isNotEqualTo(benoemdterrein2);
    }
}
