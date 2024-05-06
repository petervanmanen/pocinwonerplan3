package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.MastTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class MastTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Mast.class);
        Mast mast1 = getMastSample1();
        Mast mast2 = new Mast();
        assertThat(mast1).isNotEqualTo(mast2);

        mast2.setId(mast1.getId());
        assertThat(mast1).isEqualTo(mast2);

        mast2 = getMastSample2();
        assertThat(mast1).isNotEqualTo(mast2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Mast mast = new Mast();
        assertThat(mast.hashCode()).isZero();

        Mast mast1 = getMastSample1();
        mast.setId(mast1.getId());
        assertThat(mast).hasSameHashCodeAs(mast1);
    }
}
