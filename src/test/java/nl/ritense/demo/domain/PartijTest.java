package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PartijTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PartijTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Partij.class);
        Partij partij1 = getPartijSample1();
        Partij partij2 = new Partij();
        assertThat(partij1).isNotEqualTo(partij2);

        partij2.setId(partij1.getId());
        assertThat(partij1).isEqualTo(partij2);

        partij2 = getPartijSample2();
        assertThat(partij1).isNotEqualTo(partij2);
    }
}
