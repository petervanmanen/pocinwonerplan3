package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.WaterobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WaterobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Waterobject.class);
        Waterobject waterobject1 = getWaterobjectSample1();
        Waterobject waterobject2 = new Waterobject();
        assertThat(waterobject1).isNotEqualTo(waterobject2);

        waterobject2.setId(waterobject1.getId());
        assertThat(waterobject1).isEqualTo(waterobject2);

        waterobject2 = getWaterobjectSample2();
        assertThat(waterobject1).isNotEqualTo(waterobject2);
    }
}
