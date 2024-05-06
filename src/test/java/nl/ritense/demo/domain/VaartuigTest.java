package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VaartuigTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VaartuigTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vaartuig.class);
        Vaartuig vaartuig1 = getVaartuigSample1();
        Vaartuig vaartuig2 = new Vaartuig();
        assertThat(vaartuig1).isNotEqualTo(vaartuig2);

        vaartuig2.setId(vaartuig1.getId());
        assertThat(vaartuig1).isEqualTo(vaartuig2);

        vaartuig2 = getVaartuigSample2();
        assertThat(vaartuig1).isNotEqualTo(vaartuig2);
    }
}
