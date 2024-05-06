package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.FunctiehuisTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FunctiehuisTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Functiehuis.class);
        Functiehuis functiehuis1 = getFunctiehuisSample1();
        Functiehuis functiehuis2 = new Functiehuis();
        assertThat(functiehuis1).isNotEqualTo(functiehuis2);

        functiehuis2.setId(functiehuis1.getId());
        assertThat(functiehuis1).isEqualTo(functiehuis2);

        functiehuis2 = getFunctiehuisSample2();
        assertThat(functiehuis1).isNotEqualTo(functiehuis2);
    }
}
