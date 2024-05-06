package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerhuurbaareenheidTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerhuurbaareenheidTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verhuurbaareenheid.class);
        Verhuurbaareenheid verhuurbaareenheid1 = getVerhuurbaareenheidSample1();
        Verhuurbaareenheid verhuurbaareenheid2 = new Verhuurbaareenheid();
        assertThat(verhuurbaareenheid1).isNotEqualTo(verhuurbaareenheid2);

        verhuurbaareenheid2.setId(verhuurbaareenheid1.getId());
        assertThat(verhuurbaareenheid1).isEqualTo(verhuurbaareenheid2);

        verhuurbaareenheid2 = getVerhuurbaareenheidSample2();
        assertThat(verhuurbaareenheid1).isNotEqualTo(verhuurbaareenheid2);
    }
}
