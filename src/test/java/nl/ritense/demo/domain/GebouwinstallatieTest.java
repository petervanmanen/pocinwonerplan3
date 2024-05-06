package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GebouwinstallatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GebouwinstallatieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gebouwinstallatie.class);
        Gebouwinstallatie gebouwinstallatie1 = getGebouwinstallatieSample1();
        Gebouwinstallatie gebouwinstallatie2 = new Gebouwinstallatie();
        assertThat(gebouwinstallatie1).isNotEqualTo(gebouwinstallatie2);

        gebouwinstallatie2.setId(gebouwinstallatie1.getId());
        assertThat(gebouwinstallatie1).isEqualTo(gebouwinstallatie2);

        gebouwinstallatie2 = getGebouwinstallatieSample2();
        assertThat(gebouwinstallatie1).isNotEqualTo(gebouwinstallatie2);
    }
}
