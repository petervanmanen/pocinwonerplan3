package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.CombibonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CombibonTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Combibon.class);
        Combibon combibon1 = getCombibonSample1();
        Combibon combibon2 = new Combibon();
        assertThat(combibon1).isNotEqualTo(combibon2);

        combibon2.setId(combibon1.getId());
        assertThat(combibon1).isEqualTo(combibon2);

        combibon2 = getCombibonSample2();
        assertThat(combibon1).isNotEqualTo(combibon2);
    }
}
