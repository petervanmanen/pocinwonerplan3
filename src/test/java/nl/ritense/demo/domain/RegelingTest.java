package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ClientTestSamples.*;
import static nl.ritense.demo.domain.RegelingTestSamples.*;
import static nl.ritense.demo.domain.RegelingsoortTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RegelingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Regeling.class);
        Regeling regeling1 = getRegelingSample1();
        Regeling regeling2 = new Regeling();
        assertThat(regeling1).isNotEqualTo(regeling2);

        regeling2.setId(regeling1.getId());
        assertThat(regeling1).isEqualTo(regeling2);

        regeling2 = getRegelingSample2();
        assertThat(regeling1).isNotEqualTo(regeling2);
    }

    @Test
    void isregelingsoortRegelingsoortTest() throws Exception {
        Regeling regeling = getRegelingRandomSampleGenerator();
        Regelingsoort regelingsoortBack = getRegelingsoortRandomSampleGenerator();

        regeling.setIsregelingsoortRegelingsoort(regelingsoortBack);
        assertThat(regeling.getIsregelingsoortRegelingsoort()).isEqualTo(regelingsoortBack);

        regeling.isregelingsoortRegelingsoort(null);
        assertThat(regeling.getIsregelingsoortRegelingsoort()).isNull();
    }

    @Test
    void heeftregelingClientTest() throws Exception {
        Regeling regeling = getRegelingRandomSampleGenerator();
        Client clientBack = getClientRandomSampleGenerator();

        regeling.setHeeftregelingClient(clientBack);
        assertThat(regeling.getHeeftregelingClient()).isEqualTo(clientBack);

        regeling.heeftregelingClient(null);
        assertThat(regeling.getHeeftregelingClient()).isNull();
    }
}
