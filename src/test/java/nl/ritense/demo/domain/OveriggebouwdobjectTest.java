package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OveriggebouwdobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OveriggebouwdobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Overiggebouwdobject.class);
        Overiggebouwdobject overiggebouwdobject1 = getOveriggebouwdobjectSample1();
        Overiggebouwdobject overiggebouwdobject2 = new Overiggebouwdobject();
        assertThat(overiggebouwdobject1).isNotEqualTo(overiggebouwdobject2);

        overiggebouwdobject2.setId(overiggebouwdobject1.getId());
        assertThat(overiggebouwdobject1).isEqualTo(overiggebouwdobject2);

        overiggebouwdobject2 = getOveriggebouwdobjectSample2();
        assertThat(overiggebouwdobject1).isNotEqualTo(overiggebouwdobject2);
    }
}
