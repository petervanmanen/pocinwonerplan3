package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ChildclassaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ChildclassaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Childclassa.class);
        Childclassa childclassa1 = getChildclassaSample1();
        Childclassa childclassa2 = new Childclassa();
        assertThat(childclassa1).isNotEqualTo(childclassa2);

        childclassa2.setId(childclassa1.getId());
        assertThat(childclassa1).isEqualTo(childclassa2);

        childclassa2 = getChildclassaSample2();
        assertThat(childclassa1).isNotEqualTo(childclassa2);
    }
}
