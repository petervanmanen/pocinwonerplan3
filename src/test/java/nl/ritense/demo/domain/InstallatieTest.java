package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.InstallatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InstallatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Installatie.class);
        Installatie installatie1 = getInstallatieSample1();
        Installatie installatie2 = new Installatie();
        assertThat(installatie1).isNotEqualTo(installatie2);

        installatie2.setId(installatie1.getId());
        assertThat(installatie1).isEqualTo(installatie2);

        installatie2 = getInstallatieSample2();
        assertThat(installatie1).isNotEqualTo(installatie2);
    }
}
