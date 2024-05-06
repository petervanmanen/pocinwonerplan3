package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KeermuurTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KeermuurTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Keermuur.class);
        Keermuur keermuur1 = getKeermuurSample1();
        Keermuur keermuur2 = new Keermuur();
        assertThat(keermuur1).isNotEqualTo(keermuur2);

        keermuur2.setId(keermuur1.getId());
        assertThat(keermuur1).isEqualTo(keermuur2);

        keermuur2 = getKeermuurSample2();
        assertThat(keermuur1).isNotEqualTo(keermuur2);
    }
}
