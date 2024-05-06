package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AomstatusTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AomstatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Aomstatus.class);
        Aomstatus aomstatus1 = getAomstatusSample1();
        Aomstatus aomstatus2 = new Aomstatus();
        assertThat(aomstatus1).isNotEqualTo(aomstatus2);

        aomstatus2.setId(aomstatus1.getId());
        assertThat(aomstatus1).isEqualTo(aomstatus2);

        aomstatus2 = getAomstatusSample2();
        assertThat(aomstatus1).isNotEqualTo(aomstatus2);
    }
}
