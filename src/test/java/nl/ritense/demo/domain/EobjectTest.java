package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eobject.class);
        Eobject eobject1 = getEobjectSample1();
        Eobject eobject2 = new Eobject();
        assertThat(eobject1).isNotEqualTo(eobject2);

        eobject2.setId(eobject1.getId());
        assertThat(eobject1).isEqualTo(eobject2);

        eobject2 = getEobjectSample2();
        assertThat(eobject1).isNotEqualTo(eobject2);
    }
}
