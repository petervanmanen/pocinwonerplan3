package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.InformatiedakloosheidTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InformatiedakloosheidTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Informatiedakloosheid.class);
        Informatiedakloosheid informatiedakloosheid1 = getInformatiedakloosheidSample1();
        Informatiedakloosheid informatiedakloosheid2 = new Informatiedakloosheid();
        assertThat(informatiedakloosheid1).isNotEqualTo(informatiedakloosheid2);

        informatiedakloosheid2.setId(informatiedakloosheid1.getId());
        assertThat(informatiedakloosheid1).isEqualTo(informatiedakloosheid2);

        informatiedakloosheid2 = getInformatiedakloosheidSample2();
        assertThat(informatiedakloosheid1).isNotEqualTo(informatiedakloosheid2);
    }
}
