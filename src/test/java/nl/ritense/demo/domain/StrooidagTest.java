package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.StrooidagTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StrooidagTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Strooidag.class);
        Strooidag strooidag1 = getStrooidagSample1();
        Strooidag strooidag2 = new Strooidag();
        assertThat(strooidag1).isNotEqualTo(strooidag2);

        strooidag2.setId(strooidag1.getId());
        assertThat(strooidag1).isEqualTo(strooidag2);

        strooidag2 = getStrooidagSample2();
        assertThat(strooidag1).isNotEqualTo(strooidag2);
    }
}
