package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AdresaanduidingTestSamples.*;
import static nl.ritense.demo.domain.NummeraanduidingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AdresaanduidingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Adresaanduiding.class);
        Adresaanduiding adresaanduiding1 = getAdresaanduidingSample1();
        Adresaanduiding adresaanduiding2 = new Adresaanduiding();
        assertThat(adresaanduiding1).isNotEqualTo(adresaanduiding2);

        adresaanduiding2.setId(adresaanduiding1.getId());
        assertThat(adresaanduiding1).isEqualTo(adresaanduiding2);

        adresaanduiding2 = getAdresaanduidingSample2();
        assertThat(adresaanduiding1).isNotEqualTo(adresaanduiding2);
    }

    @Test
    void verwijstnaarNummeraanduidingTest() throws Exception {
        Adresaanduiding adresaanduiding = getAdresaanduidingRandomSampleGenerator();
        Nummeraanduiding nummeraanduidingBack = getNummeraanduidingRandomSampleGenerator();

        adresaanduiding.setVerwijstnaarNummeraanduiding(nummeraanduidingBack);
        assertThat(adresaanduiding.getVerwijstnaarNummeraanduiding()).isEqualTo(nummeraanduidingBack);

        adresaanduiding.verwijstnaarNummeraanduiding(null);
        assertThat(adresaanduiding.getVerwijstnaarNummeraanduiding()).isNull();
    }
}
