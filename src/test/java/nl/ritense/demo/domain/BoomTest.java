package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BoomTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BoomTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Boom.class);
        Boom boom1 = getBoomSample1();
        Boom boom2 = new Boom();
        assertThat(boom1).isNotEqualTo(boom2);

        boom2.setId(boom1.getId());
        assertThat(boom1).isEqualTo(boom2);

        boom2 = getBoomSample2();
        assertThat(boom1).isNotEqualTo(boom2);
    }
}
