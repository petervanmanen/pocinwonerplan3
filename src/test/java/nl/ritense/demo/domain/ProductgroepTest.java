package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ParkeervergunningTestSamples.*;
import static nl.ritense.demo.domain.ProductTestSamples.*;
import static nl.ritense.demo.domain.ProductgroepTestSamples.*;
import static nl.ritense.demo.domain.ProductsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductgroepTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Productgroep.class);
        Productgroep productgroep1 = getProductgroepSample1();
        Productgroep productgroep2 = new Productgroep();
        assertThat(productgroep1).isNotEqualTo(productgroep2);

        productgroep2.setId(productgroep1.getId());
        assertThat(productgroep1).isEqualTo(productgroep2);

        productgroep2 = getProductgroepSample2();
        assertThat(productgroep1).isNotEqualTo(productgroep2);
    }

    @Test
    void soortParkeervergunningTest() throws Exception {
        Productgroep productgroep = getProductgroepRandomSampleGenerator();
        Parkeervergunning parkeervergunningBack = getParkeervergunningRandomSampleGenerator();

        productgroep.addSoortParkeervergunning(parkeervergunningBack);
        assertThat(productgroep.getSoortParkeervergunnings()).containsOnly(parkeervergunningBack);
        assertThat(parkeervergunningBack.getSoortProductgroep()).isEqualTo(productgroep);

        productgroep.removeSoortParkeervergunning(parkeervergunningBack);
        assertThat(productgroep.getSoortParkeervergunnings()).doesNotContain(parkeervergunningBack);
        assertThat(parkeervergunningBack.getSoortProductgroep()).isNull();

        productgroep.soortParkeervergunnings(new HashSet<>(Set.of(parkeervergunningBack)));
        assertThat(productgroep.getSoortParkeervergunnings()).containsOnly(parkeervergunningBack);
        assertThat(parkeervergunningBack.getSoortProductgroep()).isEqualTo(productgroep);

        productgroep.setSoortParkeervergunnings(new HashSet<>());
        assertThat(productgroep.getSoortParkeervergunnings()).doesNotContain(parkeervergunningBack);
        assertThat(parkeervergunningBack.getSoortProductgroep()).isNull();
    }

    @Test
    void valtbinnenProductsoortTest() throws Exception {
        Productgroep productgroep = getProductgroepRandomSampleGenerator();
        Productsoort productsoortBack = getProductsoortRandomSampleGenerator();

        productgroep.addValtbinnenProductsoort(productsoortBack);
        assertThat(productgroep.getValtbinnenProductsoorts()).containsOnly(productsoortBack);
        assertThat(productsoortBack.getValtbinnenProductgroep()).isEqualTo(productgroep);

        productgroep.removeValtbinnenProductsoort(productsoortBack);
        assertThat(productgroep.getValtbinnenProductsoorts()).doesNotContain(productsoortBack);
        assertThat(productsoortBack.getValtbinnenProductgroep()).isNull();

        productgroep.valtbinnenProductsoorts(new HashSet<>(Set.of(productsoortBack)));
        assertThat(productgroep.getValtbinnenProductsoorts()).containsOnly(productsoortBack);
        assertThat(productsoortBack.getValtbinnenProductgroep()).isEqualTo(productgroep);

        productgroep.setValtbinnenProductsoorts(new HashSet<>());
        assertThat(productgroep.getValtbinnenProductsoorts()).doesNotContain(productsoortBack);
        assertThat(productsoortBack.getValtbinnenProductgroep()).isNull();
    }

    @Test
    void valtbinnenProductTest() throws Exception {
        Productgroep productgroep = getProductgroepRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        productgroep.addValtbinnenProduct(productBack);
        assertThat(productgroep.getValtbinnenProducts()).containsOnly(productBack);
        assertThat(productBack.getValtbinnenProductgroeps()).containsOnly(productgroep);

        productgroep.removeValtbinnenProduct(productBack);
        assertThat(productgroep.getValtbinnenProducts()).doesNotContain(productBack);
        assertThat(productBack.getValtbinnenProductgroeps()).doesNotContain(productgroep);

        productgroep.valtbinnenProducts(new HashSet<>(Set.of(productBack)));
        assertThat(productgroep.getValtbinnenProducts()).containsOnly(productBack);
        assertThat(productBack.getValtbinnenProductgroeps()).containsOnly(productgroep);

        productgroep.setValtbinnenProducts(new HashSet<>());
        assertThat(productgroep.getValtbinnenProducts()).doesNotContain(productBack);
        assertThat(productBack.getValtbinnenProductgroeps()).doesNotContain(productgroep);
    }
}
