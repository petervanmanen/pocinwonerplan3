package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ViaductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ViaductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Viaduct.class);
        Viaduct viaduct1 = getViaductSample1();
        Viaduct viaduct2 = new Viaduct();
        assertThat(viaduct1).isNotEqualTo(viaduct2);

        viaduct2.setId(viaduct1.getId());
        assertThat(viaduct1).isEqualTo(viaduct2);

        viaduct2 = getViaductSample2();
        assertThat(viaduct1).isNotEqualTo(viaduct2);
    }
}
