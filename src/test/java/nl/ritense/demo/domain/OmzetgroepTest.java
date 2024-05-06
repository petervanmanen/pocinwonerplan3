package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OmzetgroepTestSamples.*;
import static nl.ritense.demo.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OmzetgroepTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Omzetgroep.class);
        Omzetgroep omzetgroep1 = getOmzetgroepSample1();
        Omzetgroep omzetgroep2 = new Omzetgroep();
        assertThat(omzetgroep1).isNotEqualTo(omzetgroep2);

        omzetgroep2.setId(omzetgroep1.getId());
        assertThat(omzetgroep1).isEqualTo(omzetgroep2);

        omzetgroep2 = getOmzetgroepSample2();
        assertThat(omzetgroep1).isNotEqualTo(omzetgroep2);
    }

    @Test
    void valtbinnenProductTest() throws Exception {
        Omzetgroep omzetgroep = getOmzetgroepRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        omzetgroep.addValtbinnenProduct(productBack);
        assertThat(omzetgroep.getValtbinnenProducts()).containsOnly(productBack);
        assertThat(productBack.getValtbinnenOmzetgroeps()).containsOnly(omzetgroep);

        omzetgroep.removeValtbinnenProduct(productBack);
        assertThat(omzetgroep.getValtbinnenProducts()).doesNotContain(productBack);
        assertThat(productBack.getValtbinnenOmzetgroeps()).doesNotContain(omzetgroep);

        omzetgroep.valtbinnenProducts(new HashSet<>(Set.of(productBack)));
        assertThat(omzetgroep.getValtbinnenProducts()).containsOnly(productBack);
        assertThat(productBack.getValtbinnenOmzetgroeps()).containsOnly(omzetgroep);

        omzetgroep.setValtbinnenProducts(new HashSet<>());
        assertThat(omzetgroep.getValtbinnenProducts()).doesNotContain(productBack);
        assertThat(productBack.getValtbinnenOmzetgroeps()).doesNotContain(omzetgroep);
    }
}
