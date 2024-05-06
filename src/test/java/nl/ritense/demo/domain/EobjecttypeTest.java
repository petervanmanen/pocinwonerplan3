package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.EobjecttypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EobjecttypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Eobjecttype.class);
        Eobjecttype eobjecttype1 = getEobjecttypeSample1();
        Eobjecttype eobjecttype2 = new Eobjecttype();
        assertThat(eobjecttype1).isNotEqualTo(eobjecttype2);

        eobjecttype2.setId(eobjecttype1.getId());
        assertThat(eobjecttype1).isEqualTo(eobjecttype2);

        eobjecttype2 = getEobjecttypeSample2();
        assertThat(eobjecttype1).isNotEqualTo(eobjecttype2);
    }
}
