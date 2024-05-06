package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KpclassaclasscTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KpclassaclasscTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kpclassaclassc.class);
        Kpclassaclassc kpclassaclassc1 = getKpclassaclasscSample1();
        Kpclassaclassc kpclassaclassc2 = new Kpclassaclassc();
        assertThat(kpclassaclassc1).isNotEqualTo(kpclassaclassc2);

        kpclassaclassc2.setId(kpclassaclassc1.getId());
        assertThat(kpclassaclassc1).isEqualTo(kpclassaclassc2);

        kpclassaclassc2 = getKpclassaclasscSample2();
        assertThat(kpclassaclassc1).isNotEqualTo(kpclassaclassc2);
    }
}
