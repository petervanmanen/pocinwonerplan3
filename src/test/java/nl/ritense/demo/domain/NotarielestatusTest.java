package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.NotarielestatusTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NotarielestatusTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Notarielestatus.class);
        Notarielestatus notarielestatus1 = getNotarielestatusSample1();
        Notarielestatus notarielestatus2 = new Notarielestatus();
        assertThat(notarielestatus1).isNotEqualTo(notarielestatus2);

        notarielestatus2.setId(notarielestatus1.getId());
        assertThat(notarielestatus1).isEqualTo(notarielestatus2);

        notarielestatus2 = getNotarielestatusSample2();
        assertThat(notarielestatus1).isNotEqualTo(notarielestatus2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Notarielestatus notarielestatus = new Notarielestatus();
        assertThat(notarielestatus.hashCode()).isZero();

        Notarielestatus notarielestatus1 = getNotarielestatusSample1();
        notarielestatus.setId(notarielestatus1.getId());
        assertThat(notarielestatus).hasSameHashCodeAs(notarielestatus1);
    }
}
