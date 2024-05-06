package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.OnderhoudskostenTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OnderhoudskostenTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Onderhoudskosten.class);
        Onderhoudskosten onderhoudskosten1 = getOnderhoudskostenSample1();
        Onderhoudskosten onderhoudskosten2 = new Onderhoudskosten();
        assertThat(onderhoudskosten1).isNotEqualTo(onderhoudskosten2);

        onderhoudskosten2.setId(onderhoudskosten1.getId());
        assertThat(onderhoudskosten1).isEqualTo(onderhoudskosten2);

        onderhoudskosten2 = getOnderhoudskostenSample2();
        assertThat(onderhoudskosten1).isNotEqualTo(onderhoudskosten2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Onderhoudskosten onderhoudskosten = new Onderhoudskosten();
        assertThat(onderhoudskosten.hashCode()).isZero();

        Onderhoudskosten onderhoudskosten1 = getOnderhoudskostenSample1();
        onderhoudskosten.setId(onderhoudskosten1.getId());
        assertThat(onderhoudskosten).hasSameHashCodeAs(onderhoudskosten1);
    }
}
