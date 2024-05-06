package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.RioolputTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RioolputTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Rioolput.class);
        Rioolput rioolput1 = getRioolputSample1();
        Rioolput rioolput2 = new Rioolput();
        assertThat(rioolput1).isNotEqualTo(rioolput2);

        rioolput2.setId(rioolput1.getId());
        assertThat(rioolput1).isEqualTo(rioolput2);

        rioolput2 = getRioolputSample2();
        assertThat(rioolput1).isNotEqualTo(rioolput2);
    }
}
