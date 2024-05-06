package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.WozobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WozobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wozobject.class);
        Wozobject wozobject1 = getWozobjectSample1();
        Wozobject wozobject2 = new Wozobject();
        assertThat(wozobject1).isNotEqualTo(wozobject2);

        wozobject2.setId(wozobject1.getId());
        assertThat(wozobject1).isEqualTo(wozobject2);

        wozobject2 = getWozobjectSample2();
        assertThat(wozobject1).isNotEqualTo(wozobject2);
    }
}
