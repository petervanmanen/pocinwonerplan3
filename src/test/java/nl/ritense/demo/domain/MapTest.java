package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MapTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MapTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Map.class);
        Map map1 = getMapSample1();
        Map map2 = new Map();
        assertThat(map1).isNotEqualTo(map2);

        map2.setId(map1.getId());
        assertThat(map1).isEqualTo(map2);

        map2 = getMapSample2();
        assertThat(map1).isNotEqualTo(map2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Map map = new Map();
        assertThat(map.hashCode()).isZero();

        Map map1 = getMapSample1();
        map.setId(map1.getId());
        assertThat(map).hasSameHashCodeAs(map1);
    }
}
