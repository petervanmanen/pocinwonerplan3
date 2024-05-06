package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BemalingsgebiedTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BemalingsgebiedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bemalingsgebied.class);
        Bemalingsgebied bemalingsgebied1 = getBemalingsgebiedSample1();
        Bemalingsgebied bemalingsgebied2 = new Bemalingsgebied();
        assertThat(bemalingsgebied1).isNotEqualTo(bemalingsgebied2);

        bemalingsgebied2.setId(bemalingsgebied1.getId());
        assertThat(bemalingsgebied1).isEqualTo(bemalingsgebied2);

        bemalingsgebied2 = getBemalingsgebiedSample2();
        assertThat(bemalingsgebied1).isNotEqualTo(bemalingsgebied2);
    }
}
