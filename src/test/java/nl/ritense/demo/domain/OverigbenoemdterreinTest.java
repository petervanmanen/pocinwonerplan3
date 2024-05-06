package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OverigbenoemdterreinTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OverigbenoemdterreinTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Overigbenoemdterrein.class);
        Overigbenoemdterrein overigbenoemdterrein1 = getOverigbenoemdterreinSample1();
        Overigbenoemdterrein overigbenoemdterrein2 = new Overigbenoemdterrein();
        assertThat(overigbenoemdterrein1).isNotEqualTo(overigbenoemdterrein2);

        overigbenoemdterrein2.setId(overigbenoemdterrein1.getId());
        assertThat(overigbenoemdterrein1).isEqualTo(overigbenoemdterrein2);

        overigbenoemdterrein2 = getOverigbenoemdterreinSample2();
        assertThat(overigbenoemdterrein1).isNotEqualTo(overigbenoemdterrein2);
    }
}
