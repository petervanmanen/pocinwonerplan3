package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OpenbareactiviteitTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpenbareactiviteitTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Openbareactiviteit.class);
        Openbareactiviteit openbareactiviteit1 = getOpenbareactiviteitSample1();
        Openbareactiviteit openbareactiviteit2 = new Openbareactiviteit();
        assertThat(openbareactiviteit1).isNotEqualTo(openbareactiviteit2);

        openbareactiviteit2.setId(openbareactiviteit1.getId());
        assertThat(openbareactiviteit1).isEqualTo(openbareactiviteit2);

        openbareactiviteit2 = getOpenbareactiviteitSample2();
        assertThat(openbareactiviteit1).isNotEqualTo(openbareactiviteit2);
    }
}
