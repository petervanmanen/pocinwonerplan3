package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerkeersdrempelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerkeersdrempelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verkeersdrempel.class);
        Verkeersdrempel verkeersdrempel1 = getVerkeersdrempelSample1();
        Verkeersdrempel verkeersdrempel2 = new Verkeersdrempel();
        assertThat(verkeersdrempel1).isNotEqualTo(verkeersdrempel2);

        verkeersdrempel2.setId(verkeersdrempel1.getId());
        assertThat(verkeersdrempel1).isEqualTo(verkeersdrempel2);

        verkeersdrempel2 = getVerkeersdrempelSample2();
        assertThat(verkeersdrempel1).isNotEqualTo(verkeersdrempel2);
    }
}
