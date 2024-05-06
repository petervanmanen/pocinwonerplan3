package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ElogboekTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ElogboekTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Elogboek.class);
        Elogboek elogboek1 = getElogboekSample1();
        Elogboek elogboek2 = new Elogboek();
        assertThat(elogboek1).isNotEqualTo(elogboek2);

        elogboek2.setId(elogboek1.getId());
        assertThat(elogboek1).isEqualTo(elogboek2);

        elogboek2 = getElogboekSample2();
        assertThat(elogboek1).isNotEqualTo(elogboek2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Elogboek elogboek = new Elogboek();
        assertThat(elogboek.hashCode()).isZero();

        Elogboek elogboek1 = getElogboekSample1();
        elogboek.setId(elogboek1.getId());
        assertThat(elogboek).hasSameHashCodeAs(elogboek1);
    }
}
