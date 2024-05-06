package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ParkeervergunningTestSamples.*;
import static nl.ritense.demo.domain.ProductgroepTestSamples.*;
import static nl.ritense.demo.domain.ProductsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductsoortTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Productsoort.class);
        Productsoort productsoort1 = getProductsoortSample1();
        Productsoort productsoort2 = new Productsoort();
        assertThat(productsoort1).isNotEqualTo(productsoort2);

        productsoort2.setId(productsoort1.getId());
        assertThat(productsoort1).isEqualTo(productsoort2);

        productsoort2 = getProductsoortSample2();
        assertThat(productsoort1).isNotEqualTo(productsoort2);
    }

    @Test
    void soortParkeervergunningTest() throws Exception {
        Productsoort productsoort = getProductsoortRandomSampleGenerator();
        Parkeervergunning parkeervergunningBack = getParkeervergunningRandomSampleGenerator();

        productsoort.addSoortParkeervergunning(parkeervergunningBack);
        assertThat(productsoort.getSoortParkeervergunnings()).containsOnly(parkeervergunningBack);
        assertThat(parkeervergunningBack.getSoortProductsoort()).isEqualTo(productsoort);

        productsoort.removeSoortParkeervergunning(parkeervergunningBack);
        assertThat(productsoort.getSoortParkeervergunnings()).doesNotContain(parkeervergunningBack);
        assertThat(parkeervergunningBack.getSoortProductsoort()).isNull();

        productsoort.soortParkeervergunnings(new HashSet<>(Set.of(parkeervergunningBack)));
        assertThat(productsoort.getSoortParkeervergunnings()).containsOnly(parkeervergunningBack);
        assertThat(parkeervergunningBack.getSoortProductsoort()).isEqualTo(productsoort);

        productsoort.setSoortParkeervergunnings(new HashSet<>());
        assertThat(productsoort.getSoortParkeervergunnings()).doesNotContain(parkeervergunningBack);
        assertThat(parkeervergunningBack.getSoortProductsoort()).isNull();
    }

    @Test
    void valtbinnenProductgroepTest() throws Exception {
        Productsoort productsoort = getProductsoortRandomSampleGenerator();
        Productgroep productgroepBack = getProductgroepRandomSampleGenerator();

        productsoort.setValtbinnenProductgroep(productgroepBack);
        assertThat(productsoort.getValtbinnenProductgroep()).isEqualTo(productgroepBack);

        productsoort.valtbinnenProductgroep(null);
        assertThat(productsoort.getValtbinnenProductgroep()).isNull();
    }
}
