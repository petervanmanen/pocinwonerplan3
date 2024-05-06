package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DeelplanveldTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DeelplanveldTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Deelplanveld.class);
        Deelplanveld deelplanveld1 = getDeelplanveldSample1();
        Deelplanveld deelplanveld2 = new Deelplanveld();
        assertThat(deelplanveld1).isNotEqualTo(deelplanveld2);

        deelplanveld2.setId(deelplanveld1.getId());
        assertThat(deelplanveld1).isEqualTo(deelplanveld2);

        deelplanveld2 = getDeelplanveldSample2();
        assertThat(deelplanveld1).isNotEqualTo(deelplanveld2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Deelplanveld deelplanveld = new Deelplanveld();
        assertThat(deelplanveld.hashCode()).isZero();

        Deelplanveld deelplanveld1 = getDeelplanveldSample1();
        deelplanveld.setId(deelplanveld1.getId());
        assertThat(deelplanveld).hasSameHashCodeAs(deelplanveld1);
    }
}
