package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.UitvoerdergraafwerkzaamhedenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UitvoerdergraafwerkzaamhedenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Uitvoerdergraafwerkzaamheden.class);
        Uitvoerdergraafwerkzaamheden uitvoerdergraafwerkzaamheden1 = getUitvoerdergraafwerkzaamhedenSample1();
        Uitvoerdergraafwerkzaamheden uitvoerdergraafwerkzaamheden2 = new Uitvoerdergraafwerkzaamheden();
        assertThat(uitvoerdergraafwerkzaamheden1).isNotEqualTo(uitvoerdergraafwerkzaamheden2);

        uitvoerdergraafwerkzaamheden2.setId(uitvoerdergraafwerkzaamheden1.getId());
        assertThat(uitvoerdergraafwerkzaamheden1).isEqualTo(uitvoerdergraafwerkzaamheden2);

        uitvoerdergraafwerkzaamheden2 = getUitvoerdergraafwerkzaamhedenSample2();
        assertThat(uitvoerdergraafwerkzaamheden1).isNotEqualTo(uitvoerdergraafwerkzaamheden2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Uitvoerdergraafwerkzaamheden uitvoerdergraafwerkzaamheden = new Uitvoerdergraafwerkzaamheden();
        assertThat(uitvoerdergraafwerkzaamheden.hashCode()).isZero();

        Uitvoerdergraafwerkzaamheden uitvoerdergraafwerkzaamheden1 = getUitvoerdergraafwerkzaamhedenSample1();
        uitvoerdergraafwerkzaamheden.setId(uitvoerdergraafwerkzaamheden1.getId());
        assertThat(uitvoerdergraafwerkzaamheden).hasSameHashCodeAs(uitvoerdergraafwerkzaamheden1);
    }
}
