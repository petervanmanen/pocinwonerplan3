package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BelproviderTestSamples.*;
import static nl.ritense.demo.domain.ParkeerrechtTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BelproviderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Belprovider.class);
        Belprovider belprovider1 = getBelproviderSample1();
        Belprovider belprovider2 = new Belprovider();
        assertThat(belprovider1).isNotEqualTo(belprovider2);

        belprovider2.setId(belprovider1.getId());
        assertThat(belprovider1).isEqualTo(belprovider2);

        belprovider2 = getBelproviderSample2();
        assertThat(belprovider1).isNotEqualTo(belprovider2);
    }

    @Test
    void leverancierParkeerrechtTest() throws Exception {
        Belprovider belprovider = getBelproviderRandomSampleGenerator();
        Parkeerrecht parkeerrechtBack = getParkeerrechtRandomSampleGenerator();

        belprovider.addLeverancierParkeerrecht(parkeerrechtBack);
        assertThat(belprovider.getLeverancierParkeerrechts()).containsOnly(parkeerrechtBack);
        assertThat(parkeerrechtBack.getLeverancierBelprovider()).isEqualTo(belprovider);

        belprovider.removeLeverancierParkeerrecht(parkeerrechtBack);
        assertThat(belprovider.getLeverancierParkeerrechts()).doesNotContain(parkeerrechtBack);
        assertThat(parkeerrechtBack.getLeverancierBelprovider()).isNull();

        belprovider.leverancierParkeerrechts(new HashSet<>(Set.of(parkeerrechtBack)));
        assertThat(belprovider.getLeverancierParkeerrechts()).containsOnly(parkeerrechtBack);
        assertThat(parkeerrechtBack.getLeverancierBelprovider()).isEqualTo(belprovider);

        belprovider.setLeverancierParkeerrechts(new HashSet<>());
        assertThat(belprovider.getLeverancierParkeerrechts()).doesNotContain(parkeerrechtBack);
        assertThat(parkeerrechtBack.getLeverancierBelprovider()).isNull();
    }
}
