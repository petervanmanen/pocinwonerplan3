package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SoftwareTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SoftwareTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Software.class);
        Software software1 = getSoftwareSample1();
        Software software2 = new Software();
        assertThat(software1).isNotEqualTo(software2);

        software2.setId(software1.getId());
        assertThat(software1).isEqualTo(software2);

        software2 = getSoftwareSample2();
        assertThat(software1).isNotEqualTo(software2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Software software = new Software();
        assertThat(software.hashCode()).isZero();

        Software software1 = getSoftwareSample1();
        software.setId(software1.getId());
        assertThat(software).hasSameHashCodeAs(software1);
    }
}
