package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.LijnTestSamples.*;
import static nl.ritense.demo.domain.LijnengroepTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class LijnTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Lijn.class);
        Lijn lijn1 = getLijnSample1();
        Lijn lijn2 = new Lijn();
        assertThat(lijn1).isNotEqualTo(lijn2);

        lijn2.setId(lijn1.getId());
        assertThat(lijn1).isEqualTo(lijn2);

        lijn2 = getLijnSample2();
        assertThat(lijn1).isNotEqualTo(lijn2);
    }

    @Test
    void omvatLijnengroepTest() throws Exception {
        Lijn lijn = getLijnRandomSampleGenerator();
        Lijnengroep lijnengroepBack = getLijnengroepRandomSampleGenerator();

        lijn.setOmvatLijnengroep(lijnengroepBack);
        assertThat(lijn.getOmvatLijnengroep()).isEqualTo(lijnengroepBack);

        lijn.omvatLijnengroep(null);
        assertThat(lijn.getOmvatLijnengroep()).isNull();
    }
}
