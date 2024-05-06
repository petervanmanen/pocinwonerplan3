package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GeluidsschermTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GeluidsschermTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Geluidsscherm.class);
        Geluidsscherm geluidsscherm1 = getGeluidsschermSample1();
        Geluidsscherm geluidsscherm2 = new Geluidsscherm();
        assertThat(geluidsscherm1).isNotEqualTo(geluidsscherm2);

        geluidsscherm2.setId(geluidsscherm1.getId());
        assertThat(geluidsscherm1).isEqualTo(geluidsscherm2);

        geluidsscherm2 = getGeluidsschermSample2();
        assertThat(geluidsscherm1).isNotEqualTo(geluidsscherm2);
    }
}
