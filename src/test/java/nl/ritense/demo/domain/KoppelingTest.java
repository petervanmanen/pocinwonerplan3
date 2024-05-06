package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KoppelingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KoppelingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Koppeling.class);
        Koppeling koppeling1 = getKoppelingSample1();
        Koppeling koppeling2 = new Koppeling();
        assertThat(koppeling1).isNotEqualTo(koppeling2);

        koppeling2.setId(koppeling1.getId());
        assertThat(koppeling1).isEqualTo(koppeling2);

        koppeling2 = getKoppelingSample2();
        assertThat(koppeling1).isNotEqualTo(koppeling2);
    }
}
