package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.PrijzenboekTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PrijzenboekTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Prijzenboek.class);
        Prijzenboek prijzenboek1 = getPrijzenboekSample1();
        Prijzenboek prijzenboek2 = new Prijzenboek();
        assertThat(prijzenboek1).isNotEqualTo(prijzenboek2);

        prijzenboek2.setId(prijzenboek1.getId());
        assertThat(prijzenboek1).isEqualTo(prijzenboek2);

        prijzenboek2 = getPrijzenboekSample2();
        assertThat(prijzenboek1).isNotEqualTo(prijzenboek2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Prijzenboek prijzenboek = new Prijzenboek();
        assertThat(prijzenboek.hashCode()).isZero();

        Prijzenboek prijzenboek1 = getPrijzenboekSample1();
        prijzenboek.setId(prijzenboek1.getId());
        assertThat(prijzenboek).hasSameHashCodeAs(prijzenboek1);
    }
}
