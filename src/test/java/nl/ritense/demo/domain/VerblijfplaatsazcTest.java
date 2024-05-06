package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.VerblijfplaatsazcTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class VerblijfplaatsazcTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Verblijfplaatsazc.class);
        Verblijfplaatsazc verblijfplaatsazc1 = getVerblijfplaatsazcSample1();
        Verblijfplaatsazc verblijfplaatsazc2 = new Verblijfplaatsazc();
        assertThat(verblijfplaatsazc1).isNotEqualTo(verblijfplaatsazc2);

        verblijfplaatsazc2.setId(verblijfplaatsazc1.getId());
        assertThat(verblijfplaatsazc1).isEqualTo(verblijfplaatsazc2);

        verblijfplaatsazc2 = getVerblijfplaatsazcSample2();
        assertThat(verblijfplaatsazc1).isNotEqualTo(verblijfplaatsazc2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Verblijfplaatsazc verblijfplaatsazc = new Verblijfplaatsazc();
        assertThat(verblijfplaatsazc.hashCode()).isZero();

        Verblijfplaatsazc verblijfplaatsazc1 = getVerblijfplaatsazcSample1();
        verblijfplaatsazc.setId(verblijfplaatsazc1.getId());
        assertThat(verblijfplaatsazc).hasSameHashCodeAs(verblijfplaatsazc1);
    }
}
