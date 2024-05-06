package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GrondbeheerderTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GrondbeheerderTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Grondbeheerder.class);
        Grondbeheerder grondbeheerder1 = getGrondbeheerderSample1();
        Grondbeheerder grondbeheerder2 = new Grondbeheerder();
        assertThat(grondbeheerder1).isNotEqualTo(grondbeheerder2);

        grondbeheerder2.setId(grondbeheerder1.getId());
        assertThat(grondbeheerder1).isEqualTo(grondbeheerder2);

        grondbeheerder2 = getGrondbeheerderSample2();
        assertThat(grondbeheerder1).isNotEqualTo(grondbeheerder2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Grondbeheerder grondbeheerder = new Grondbeheerder();
        assertThat(grondbeheerder.hashCode()).isZero();

        Grondbeheerder grondbeheerder1 = getGrondbeheerderSample1();
        grondbeheerder.setId(grondbeheerder1.getId());
        assertThat(grondbeheerder).hasSameHashCodeAs(grondbeheerder1);
    }
}
