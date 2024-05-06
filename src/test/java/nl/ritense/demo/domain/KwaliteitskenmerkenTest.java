package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.KwaliteitskenmerkenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class KwaliteitskenmerkenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Kwaliteitskenmerken.class);
        Kwaliteitskenmerken kwaliteitskenmerken1 = getKwaliteitskenmerkenSample1();
        Kwaliteitskenmerken kwaliteitskenmerken2 = new Kwaliteitskenmerken();
        assertThat(kwaliteitskenmerken1).isNotEqualTo(kwaliteitskenmerken2);

        kwaliteitskenmerken2.setId(kwaliteitskenmerken1.getId());
        assertThat(kwaliteitskenmerken1).isEqualTo(kwaliteitskenmerken2);

        kwaliteitskenmerken2 = getKwaliteitskenmerkenSample2();
        assertThat(kwaliteitskenmerken1).isNotEqualTo(kwaliteitskenmerken2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Kwaliteitskenmerken kwaliteitskenmerken = new Kwaliteitskenmerken();
        assertThat(kwaliteitskenmerken.hashCode()).isZero();

        Kwaliteitskenmerken kwaliteitskenmerken1 = getKwaliteitskenmerkenSample1();
        kwaliteitskenmerken.setId(kwaliteitskenmerken1.getId());
        assertThat(kwaliteitskenmerken).hasSameHashCodeAs(kwaliteitskenmerken1);
    }
}
