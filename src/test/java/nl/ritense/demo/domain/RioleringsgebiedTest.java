package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.RioleringsgebiedTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RioleringsgebiedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rioleringsgebied.class);
        Rioleringsgebied rioleringsgebied1 = getRioleringsgebiedSample1();
        Rioleringsgebied rioleringsgebied2 = new Rioleringsgebied();
        assertThat(rioleringsgebied1).isNotEqualTo(rioleringsgebied2);

        rioleringsgebied2.setId(rioleringsgebied1.getId());
        assertThat(rioleringsgebied1).isEqualTo(rioleringsgebied2);

        rioleringsgebied2 = getRioleringsgebiedSample2();
        assertThat(rioleringsgebied1).isNotEqualTo(rioleringsgebied2);
    }
}
