package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.ArtikelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ArtikelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Artikel.class);
        Artikel artikel1 = getArtikelSample1();
        Artikel artikel2 = new Artikel();
        assertThat(artikel1).isNotEqualTo(artikel2);

        artikel2.setId(artikel1.getId());
        assertThat(artikel1).isEqualTo(artikel2);

        artikel2 = getArtikelSample2();
        assertThat(artikel1).isNotEqualTo(artikel2);
    }

    @Test
    void hashCodeVerifier() throws Exception {
        Artikel artikel = new Artikel();
        assertThat(artikel.hashCode()).isZero();

        Artikel artikel1 = getArtikelSample1();
        artikel.setId(artikel1.getId());
        assertThat(artikel).hasSameHashCodeAs(artikel1);
    }
}
