package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OpbrekingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OpbrekingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Opbreking.class);
        Opbreking opbreking1 = getOpbrekingSample1();
        Opbreking opbreking2 = new Opbreking();
        assertThat(opbreking1).isNotEqualTo(opbreking2);

        opbreking2.setId(opbreking1.getId());
        assertThat(opbreking1).isEqualTo(opbreking2);

        opbreking2 = getOpbrekingSample2();
        assertThat(opbreking1).isNotEqualTo(opbreking2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Opbreking opbreking = new Opbreking();
        assertThat(opbreking.hashCode()).isZero();

        Opbreking opbreking1 = getOpbrekingSample1();
        opbreking.setId(opbreking1.getId());
        assertThat(opbreking).hasSameHashCodeAs(opbreking1);
    }
}
