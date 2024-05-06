package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.StuwgebiedTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class StuwgebiedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Stuwgebied.class);
        Stuwgebied stuwgebied1 = getStuwgebiedSample1();
        Stuwgebied stuwgebied2 = new Stuwgebied();
        assertThat(stuwgebied1).isNotEqualTo(stuwgebied2);

        stuwgebied2.setId(stuwgebied1.getId());
        assertThat(stuwgebied1).isEqualTo(stuwgebied2);

        stuwgebied2 = getStuwgebiedSample2();
        assertThat(stuwgebied1).isNotEqualTo(stuwgebied2);
    }
}
