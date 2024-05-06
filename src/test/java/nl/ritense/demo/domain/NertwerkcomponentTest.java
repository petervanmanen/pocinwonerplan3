package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.NertwerkcomponentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class NertwerkcomponentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Nertwerkcomponent.class);
        Nertwerkcomponent nertwerkcomponent1 = getNertwerkcomponentSample1();
        Nertwerkcomponent nertwerkcomponent2 = new Nertwerkcomponent();
        assertThat(nertwerkcomponent1).isNotEqualTo(nertwerkcomponent2);

        nertwerkcomponent2.setId(nertwerkcomponent1.getId());
        assertThat(nertwerkcomponent1).isEqualTo(nertwerkcomponent2);

        nertwerkcomponent2 = getNertwerkcomponentSample2();
        assertThat(nertwerkcomponent1).isNotEqualTo(nertwerkcomponent2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Nertwerkcomponent nertwerkcomponent = new Nertwerkcomponent();
        assertThat(nertwerkcomponent.hashCode()).isZero();

        Nertwerkcomponent nertwerkcomponent1 = getNertwerkcomponentSample1();
        nertwerkcomponent.setId(nertwerkcomponent1.getId());
        assertThat(nertwerkcomponent).hasSameHashCodeAs(nertwerkcomponent1);
    }
}
