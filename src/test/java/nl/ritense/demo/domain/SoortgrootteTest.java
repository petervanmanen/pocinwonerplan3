package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SoortgrootteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SoortgrootteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Soortgrootte.class);
        Soortgrootte soortgrootte1 = getSoortgrootteSample1();
        Soortgrootte soortgrootte2 = new Soortgrootte();
        assertThat(soortgrootte1).isNotEqualTo(soortgrootte2);

        soortgrootte2.setId(soortgrootte1.getId());
        assertThat(soortgrootte1).isEqualTo(soortgrootte2);

        soortgrootte2 = getSoortgrootteSample2();
        assertThat(soortgrootte1).isNotEqualTo(soortgrootte2);
    }
}
