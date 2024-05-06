package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GemeentebegrafenisTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GemeentebegrafenisTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gemeentebegrafenis.class);
        Gemeentebegrafenis gemeentebegrafenis1 = getGemeentebegrafenisSample1();
        Gemeentebegrafenis gemeentebegrafenis2 = new Gemeentebegrafenis();
        assertThat(gemeentebegrafenis1).isNotEqualTo(gemeentebegrafenis2);

        gemeentebegrafenis2.setId(gemeentebegrafenis1.getId());
        assertThat(gemeentebegrafenis1).isEqualTo(gemeentebegrafenis2);

        gemeentebegrafenis2 = getGemeentebegrafenisSample2();
        assertThat(gemeentebegrafenis1).isNotEqualTo(gemeentebegrafenis2);
    }
}
