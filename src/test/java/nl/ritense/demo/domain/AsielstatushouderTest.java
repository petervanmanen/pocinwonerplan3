package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AsielstatushouderTestSamples.*;
import static nl.ritense.demo.domain.GemeenteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AsielstatushouderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Asielstatushouder.class);
        Asielstatushouder asielstatushouder1 = getAsielstatushouderSample1();
        Asielstatushouder asielstatushouder2 = new Asielstatushouder();
        assertThat(asielstatushouder1).isNotEqualTo(asielstatushouder2);

        asielstatushouder2.setId(asielstatushouder1.getId());
        assertThat(asielstatushouder1).isEqualTo(asielstatushouder2);

        asielstatushouder2 = getAsielstatushouderSample2();
        assertThat(asielstatushouder1).isNotEqualTo(asielstatushouder2);
    }

    @Test
    void isgekoppeldaanGemeenteTest() throws Exception {
        Asielstatushouder asielstatushouder = getAsielstatushouderRandomSampleGenerator();
        Gemeente gemeenteBack = getGemeenteRandomSampleGenerator();

        asielstatushouder.setIsgekoppeldaanGemeente(gemeenteBack);
        assertThat(asielstatushouder.getIsgekoppeldaanGemeente()).isEqualTo(gemeenteBack);

        asielstatushouder.isgekoppeldaanGemeente(null);
        assertThat(asielstatushouder.getIsgekoppeldaanGemeente()).isNull();
    }
}
