package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PvtTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PvtTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Pvt.class);
        Pvt pvt1 = getPvtSample1();
        Pvt pvt2 = new Pvt();
        assertThat(pvt1).isNotEqualTo(pvt2);

        pvt2.setId(pvt1.getId());
        assertThat(pvt1).isEqualTo(pvt2);

        pvt2 = getPvtSample2();
        assertThat(pvt1).isNotEqualTo(pvt2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Pvt pvt = new Pvt();
        assertThat(pvt.hashCode()).isZero();

        Pvt pvt1 = getPvtSample1();
        pvt.setId(pvt1.getId());
        assertThat(pvt).hasSameHashCodeAs(pvt1);
    }
}
