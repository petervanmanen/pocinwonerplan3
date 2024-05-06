package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SoortwozobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SoortwozobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Soortwozobject.class);
        Soortwozobject soortwozobject1 = getSoortwozobjectSample1();
        Soortwozobject soortwozobject2 = new Soortwozobject();
        assertThat(soortwozobject1).isNotEqualTo(soortwozobject2);

        soortwozobject2.setId(soortwozobject1.getId());
        assertThat(soortwozobject1).isEqualTo(soortwozobject2);

        soortwozobject2 = getSoortwozobjectSample2();
        assertThat(soortwozobject1).isNotEqualTo(soortwozobject2);
    }
}
