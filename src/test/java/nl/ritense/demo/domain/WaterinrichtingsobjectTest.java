package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.WaterinrichtingsobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WaterinrichtingsobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Waterinrichtingsobject.class);
        Waterinrichtingsobject waterinrichtingsobject1 = getWaterinrichtingsobjectSample1();
        Waterinrichtingsobject waterinrichtingsobject2 = new Waterinrichtingsobject();
        assertThat(waterinrichtingsobject1).isNotEqualTo(waterinrichtingsobject2);

        waterinrichtingsobject2.setId(waterinrichtingsobject1.getId());
        assertThat(waterinrichtingsobject1).isEqualTo(waterinrichtingsobject2);

        waterinrichtingsobject2 = getWaterinrichtingsobjectSample2();
        assertThat(waterinrichtingsobject1).isNotEqualTo(waterinrichtingsobject2);
    }
}
