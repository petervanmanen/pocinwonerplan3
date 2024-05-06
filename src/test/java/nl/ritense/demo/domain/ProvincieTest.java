package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ProvincieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProvincieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Provincie.class);
        Provincie provincie1 = getProvincieSample1();
        Provincie provincie2 = new Provincie();
        assertThat(provincie1).isNotEqualTo(provincie2);

        provincie2.setId(provincie1.getId());
        assertThat(provincie1).isEqualTo(provincie2);

        provincie2 = getProvincieSample2();
        assertThat(provincie1).isNotEqualTo(provincie2);
    }
}
