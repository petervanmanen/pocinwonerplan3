package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GeoobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GeoobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Geoobject.class);
        Geoobject geoobject1 = getGeoobjectSample1();
        Geoobject geoobject2 = new Geoobject();
        assertThat(geoobject1).isNotEqualTo(geoobject2);

        geoobject2.setId(geoobject1.getId());
        assertThat(geoobject1).isEqualTo(geoobject2);

        geoobject2 = getGeoobjectSample2();
        assertThat(geoobject1).isNotEqualTo(geoobject2);
    }
}
