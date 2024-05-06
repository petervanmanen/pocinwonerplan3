package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GroenobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GroenobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Groenobject.class);
        Groenobject groenobject1 = getGroenobjectSample1();
        Groenobject groenobject2 = new Groenobject();
        assertThat(groenobject1).isNotEqualTo(groenobject2);

        groenobject2.setId(groenobject1.getId());
        assertThat(groenobject1).isEqualTo(groenobject2);

        groenobject2 = getGroenobjectSample2();
        assertThat(groenobject1).isNotEqualTo(groenobject2);
    }
}
