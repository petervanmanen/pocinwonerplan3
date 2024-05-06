package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AmbachtTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AmbachtTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Ambacht.class);
        Ambacht ambacht1 = getAmbachtSample1();
        Ambacht ambacht2 = new Ambacht();
        assertThat(ambacht1).isNotEqualTo(ambacht2);

        ambacht2.setId(ambacht1.getId());
        assertThat(ambacht1).isEqualTo(ambacht2);

        ambacht2 = getAmbachtSample2();
        assertThat(ambacht1).isNotEqualTo(ambacht2);
    }
}
