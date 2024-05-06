package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.HuurwoningenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HuurwoningenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Huurwoningen.class);
        Huurwoningen huurwoningen1 = getHuurwoningenSample1();
        Huurwoningen huurwoningen2 = new Huurwoningen();
        assertThat(huurwoningen1).isNotEqualTo(huurwoningen2);

        huurwoningen2.setId(huurwoningen1.getId());
        assertThat(huurwoningen1).isEqualTo(huurwoningen2);

        huurwoningen2 = getHuurwoningenSample2();
        assertThat(huurwoningen1).isNotEqualTo(huurwoningen2);
    }
}
