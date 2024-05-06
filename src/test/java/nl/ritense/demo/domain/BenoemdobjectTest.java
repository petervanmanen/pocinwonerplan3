package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BenoemdobjectTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BenoemdobjectTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Benoemdobject.class);
        Benoemdobject benoemdobject1 = getBenoemdobjectSample1();
        Benoemdobject benoemdobject2 = new Benoemdobject();
        assertThat(benoemdobject1).isNotEqualTo(benoemdobject2);

        benoemdobject2.setId(benoemdobject1.getId());
        assertThat(benoemdobject1).isEqualTo(benoemdobject2);

        benoemdobject2 = getBenoemdobjectSample2();
        assertThat(benoemdobject1).isNotEqualTo(benoemdobject2);
    }
}
