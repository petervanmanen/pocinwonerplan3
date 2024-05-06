package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.SoortkunstwerkTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SoortkunstwerkTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Soortkunstwerk.class);
        Soortkunstwerk soortkunstwerk1 = getSoortkunstwerkSample1();
        Soortkunstwerk soortkunstwerk2 = new Soortkunstwerk();
        assertThat(soortkunstwerk1).isNotEqualTo(soortkunstwerk2);

        soortkunstwerk2.setId(soortkunstwerk1.getId());
        assertThat(soortkunstwerk1).isEqualTo(soortkunstwerk2);

        soortkunstwerk2 = getSoortkunstwerkSample2();
        assertThat(soortkunstwerk1).isNotEqualTo(soortkunstwerk2);
    }
}
