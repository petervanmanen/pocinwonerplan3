package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.HardwareTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HardwareTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Hardware.class);
        Hardware hardware1 = getHardwareSample1();
        Hardware hardware2 = new Hardware();
        assertThat(hardware1).isNotEqualTo(hardware2);

        hardware2.setId(hardware1.getId());
        assertThat(hardware1).isEqualTo(hardware2);

        hardware2 = getHardwareSample2();
        assertThat(hardware1).isNotEqualTo(hardware2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Hardware hardware = new Hardware();
        assertThat(hardware.hashCode()).isZero();

        Hardware hardware1 = getHardwareSample1();
        hardware.setId(hardware1.getId());
        assertThat(hardware).hasSameHashCodeAs(hardware1);
    }
}
