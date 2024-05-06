package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.CpvcodeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CpvcodeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Cpvcode.class);
        Cpvcode cpvcode1 = getCpvcodeSample1();
        Cpvcode cpvcode2 = new Cpvcode();
        assertThat(cpvcode1).isNotEqualTo(cpvcode2);

        cpvcode2.setId(cpvcode1.getId());
        assertThat(cpvcode1).isEqualTo(cpvcode2);

        cpvcode2 = getCpvcodeSample2();
        assertThat(cpvcode1).isNotEqualTo(cpvcode2);
    }
}
