package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BeperkingsgebiedTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BeperkingsgebiedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Beperkingsgebied.class);
        Beperkingsgebied beperkingsgebied1 = getBeperkingsgebiedSample1();
        Beperkingsgebied beperkingsgebied2 = new Beperkingsgebied();
        assertThat(beperkingsgebied1).isNotEqualTo(beperkingsgebied2);

        beperkingsgebied2.setId(beperkingsgebied1.getId());
        assertThat(beperkingsgebied1).isEqualTo(beperkingsgebied2);

        beperkingsgebied2 = getBeperkingsgebiedSample2();
        assertThat(beperkingsgebied1).isNotEqualTo(beperkingsgebied2);
    }
}
