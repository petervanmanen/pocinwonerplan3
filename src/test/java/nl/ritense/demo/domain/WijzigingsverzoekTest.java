package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.WijzigingsverzoekTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class WijzigingsverzoekTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Wijzigingsverzoek.class);
        Wijzigingsverzoek wijzigingsverzoek1 = getWijzigingsverzoekSample1();
        Wijzigingsverzoek wijzigingsverzoek2 = new Wijzigingsverzoek();
        assertThat(wijzigingsverzoek1).isNotEqualTo(wijzigingsverzoek2);

        wijzigingsverzoek2.setId(wijzigingsverzoek1.getId());
        assertThat(wijzigingsverzoek1).isEqualTo(wijzigingsverzoek2);

        wijzigingsverzoek2 = getWijzigingsverzoekSample2();
        assertThat(wijzigingsverzoek1).isNotEqualTo(wijzigingsverzoek2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Wijzigingsverzoek wijzigingsverzoek = new Wijzigingsverzoek();
        assertThat(wijzigingsverzoek.hashCode()).isZero();

        Wijzigingsverzoek wijzigingsverzoek1 = getWijzigingsverzoekSample1();
        wijzigingsverzoek.setId(wijzigingsverzoek1.getId());
        assertThat(wijzigingsverzoek).hasSameHashCodeAs(wijzigingsverzoek1);
    }
}
