package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DienstverbandTestSamples.*;
import static nl.ritense.demo.domain.FunctieTestSamples.*;
import static nl.ritense.demo.domain.InzetTestSamples.*;
import static nl.ritense.demo.domain.UrenTestSamples.*;
import static nl.ritense.demo.domain.WerknemerTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class DienstverbandTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dienstverband.class);
        Dienstverband dienstverband1 = getDienstverbandSample1();
        Dienstverband dienstverband2 = new Dienstverband();
        assertThat(dienstverband1).isNotEqualTo(dienstverband2);

        dienstverband2.setId(dienstverband1.getId());
        assertThat(dienstverband1).isEqualTo(dienstverband2);

        dienstverband2 = getDienstverbandSample2();
        assertThat(dienstverband1).isNotEqualTo(dienstverband2);
    }

    @Test
    void dienstverbandconformfunctieFunctieTest() throws Exception {
        Dienstverband dienstverband = getDienstverbandRandomSampleGenerator();
        Functie functieBack = getFunctieRandomSampleGenerator();

        dienstverband.setDienstverbandconformfunctieFunctie(functieBack);
        assertThat(dienstverband.getDienstverbandconformfunctieFunctie()).isEqualTo(functieBack);

        dienstverband.dienstverbandconformfunctieFunctie(null);
        assertThat(dienstverband.getDienstverbandconformfunctieFunctie()).isNull();
    }

    @Test
    void aantalvolgensinzetUrenTest() throws Exception {
        Dienstverband dienstverband = getDienstverbandRandomSampleGenerator();
        Uren urenBack = getUrenRandomSampleGenerator();

        dienstverband.setAantalvolgensinzetUren(urenBack);
        assertThat(dienstverband.getAantalvolgensinzetUren()).isEqualTo(urenBack);

        dienstverband.aantalvolgensinzetUren(null);
        assertThat(dienstverband.getAantalvolgensinzetUren()).isNull();
    }

    @Test
    void medewerkerheeftdienstverbandWerknemerTest() throws Exception {
        Dienstverband dienstverband = getDienstverbandRandomSampleGenerator();
        Werknemer werknemerBack = getWerknemerRandomSampleGenerator();

        dienstverband.setMedewerkerheeftdienstverbandWerknemer(werknemerBack);
        assertThat(dienstverband.getMedewerkerheeftdienstverbandWerknemer()).isEqualTo(werknemerBack);

        dienstverband.medewerkerheeftdienstverbandWerknemer(null);
        assertThat(dienstverband.getMedewerkerheeftdienstverbandWerknemer()).isNull();
    }

    @Test
    void aantalvolgensinzetInzetTest() throws Exception {
        Dienstverband dienstverband = getDienstverbandRandomSampleGenerator();
        Inzet inzetBack = getInzetRandomSampleGenerator();

        dienstverband.setAantalvolgensinzetInzet(inzetBack);
        assertThat(dienstverband.getAantalvolgensinzetInzet()).isEqualTo(inzetBack);

        dienstverband.aantalvolgensinzetInzet(null);
        assertThat(dienstverband.getAantalvolgensinzetInzet()).isNull();
    }
}
