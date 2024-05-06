package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GebouwdobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GebouwdobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gebouwdobject.class);
        Gebouwdobject gebouwdobject1 = getGebouwdobjectSample1();
        Gebouwdobject gebouwdobject2 = new Gebouwdobject();
        assertThat(gebouwdobject1).isNotEqualTo(gebouwdobject2);

        gebouwdobject2.setId(gebouwdobject1.getId());
        assertThat(gebouwdobject1).isEqualTo(gebouwdobject2);

        gebouwdobject2 = getGebouwdobjectSample2();
        assertThat(gebouwdobject1).isNotEqualTo(gebouwdobject2);
    }
}
