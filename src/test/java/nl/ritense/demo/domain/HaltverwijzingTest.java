package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.HaltverwijzingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class HaltverwijzingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Haltverwijzing.class);
        Haltverwijzing haltverwijzing1 = getHaltverwijzingSample1();
        Haltverwijzing haltverwijzing2 = new Haltverwijzing();
        assertThat(haltverwijzing1).isNotEqualTo(haltverwijzing2);

        haltverwijzing2.setId(haltverwijzing1.getId());
        assertThat(haltverwijzing1).isEqualTo(haltverwijzing2);

        haltverwijzing2 = getHaltverwijzingSample2();
        assertThat(haltverwijzing1).isNotEqualTo(haltverwijzing2);
    }
}
