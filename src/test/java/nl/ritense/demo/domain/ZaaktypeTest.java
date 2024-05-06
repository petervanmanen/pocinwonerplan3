package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BedrijfsprocestypeTestSamples.*;
import static nl.ritense.demo.domain.DienstTestSamples.*;
import static nl.ritense.demo.domain.FormuliersoortTestSamples.*;
import static nl.ritense.demo.domain.HeffinggrondslagTestSamples.*;
import static nl.ritense.demo.domain.MedewerkerTestSamples.*;
import static nl.ritense.demo.domain.ProductTestSamples.*;
import static nl.ritense.demo.domain.ProducttypeTestSamples.*;
import static nl.ritense.demo.domain.StatustypeTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static nl.ritense.demo.domain.ZaaktypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ZaaktypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Zaaktype.class);
        Zaaktype zaaktype1 = getZaaktypeSample1();
        Zaaktype zaaktype2 = new Zaaktype();
        assertThat(zaaktype1).isNotEqualTo(zaaktype2);

        zaaktype2.setId(zaaktype1.getId());
        assertThat(zaaktype1).isEqualTo(zaaktype2);

        zaaktype2 = getZaaktypeSample2();
        assertThat(zaaktype1).isNotEqualTo(zaaktype2);
    }

    @Test
    void heeftProducttypeTest() throws Exception {
        Zaaktype zaaktype = getZaaktypeRandomSampleGenerator();
        Producttype producttypeBack = getProducttypeRandomSampleGenerator();

        zaaktype.setHeeftProducttype(producttypeBack);
        assertThat(zaaktype.getHeeftProducttype()).isEqualTo(producttypeBack);

        zaaktype.heeftProducttype(null);
        assertThat(zaaktype.getHeeftProducttype()).isNull();
    }

    @Test
    void heeftHeffinggrondslagTest() throws Exception {
        Zaaktype zaaktype = getZaaktypeRandomSampleGenerator();
        Heffinggrondslag heffinggrondslagBack = getHeffinggrondslagRandomSampleGenerator();

        zaaktype.addHeeftHeffinggrondslag(heffinggrondslagBack);
        assertThat(zaaktype.getHeeftHeffinggrondslags()).containsOnly(heffinggrondslagBack);
        assertThat(heffinggrondslagBack.getHeeftZaaktype()).isEqualTo(zaaktype);

        zaaktype.removeHeeftHeffinggrondslag(heffinggrondslagBack);
        assertThat(zaaktype.getHeeftHeffinggrondslags()).doesNotContain(heffinggrondslagBack);
        assertThat(heffinggrondslagBack.getHeeftZaaktype()).isNull();

        zaaktype.heeftHeffinggrondslags(new HashSet<>(Set.of(heffinggrondslagBack)));
        assertThat(zaaktype.getHeeftHeffinggrondslags()).containsOnly(heffinggrondslagBack);
        assertThat(heffinggrondslagBack.getHeeftZaaktype()).isEqualTo(zaaktype);

        zaaktype.setHeeftHeffinggrondslags(new HashSet<>());
        assertThat(zaaktype.getHeeftHeffinggrondslags()).doesNotContain(heffinggrondslagBack);
        assertThat(heffinggrondslagBack.getHeeftZaaktype()).isNull();
    }

    @Test
    void heeftStatustypeTest() throws Exception {
        Zaaktype zaaktype = getZaaktypeRandomSampleGenerator();
        Statustype statustypeBack = getStatustypeRandomSampleGenerator();

        zaaktype.addHeeftStatustype(statustypeBack);
        assertThat(zaaktype.getHeeftStatustypes()).containsOnly(statustypeBack);
        assertThat(statustypeBack.getHeeftZaaktype()).isEqualTo(zaaktype);

        zaaktype.removeHeeftStatustype(statustypeBack);
        assertThat(zaaktype.getHeeftStatustypes()).doesNotContain(statustypeBack);
        assertThat(statustypeBack.getHeeftZaaktype()).isNull();

        zaaktype.heeftStatustypes(new HashSet<>(Set.of(statustypeBack)));
        assertThat(zaaktype.getHeeftStatustypes()).containsOnly(statustypeBack);
        assertThat(statustypeBack.getHeeftZaaktype()).isEqualTo(zaaktype);

        zaaktype.setHeeftStatustypes(new HashSet<>());
        assertThat(zaaktype.getHeeftStatustypes()).doesNotContain(statustypeBack);
        assertThat(statustypeBack.getHeeftZaaktype()).isNull();
    }

    @Test
    void betreftProductTest() throws Exception {
        Zaaktype zaaktype = getZaaktypeRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        zaaktype.setBetreftProduct(productBack);
        assertThat(zaaktype.getBetreftProduct()).isEqualTo(productBack);

        zaaktype.betreftProduct(null);
        assertThat(zaaktype.getBetreftProduct()).isNull();
    }

    @Test
    void heeftBedrijfsprocestypeTest() throws Exception {
        Zaaktype zaaktype = getZaaktypeRandomSampleGenerator();
        Bedrijfsprocestype bedrijfsprocestypeBack = getBedrijfsprocestypeRandomSampleGenerator();

        zaaktype.setHeeftBedrijfsprocestype(bedrijfsprocestypeBack);
        assertThat(zaaktype.getHeeftBedrijfsprocestype()).isEqualTo(bedrijfsprocestypeBack);

        zaaktype.heeftBedrijfsprocestype(null);
        assertThat(zaaktype.getHeeftBedrijfsprocestype()).isNull();
    }

    @Test
    void isverantwoordelijkevoorMedewerkerTest() throws Exception {
        Zaaktype zaaktype = getZaaktypeRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        zaaktype.setIsverantwoordelijkevoorMedewerker(medewerkerBack);
        assertThat(zaaktype.getIsverantwoordelijkevoorMedewerker()).isEqualTo(medewerkerBack);

        zaaktype.isverantwoordelijkevoorMedewerker(null);
        assertThat(zaaktype.getIsverantwoordelijkevoorMedewerker()).isNull();
    }

    @Test
    void startDienstTest() throws Exception {
        Zaaktype zaaktype = getZaaktypeRandomSampleGenerator();
        Dienst dienstBack = getDienstRandomSampleGenerator();

        zaaktype.addStartDienst(dienstBack);
        assertThat(zaaktype.getStartDiensts()).containsOnly(dienstBack);
        assertThat(dienstBack.getStartZaaktype()).isEqualTo(zaaktype);

        zaaktype.removeStartDienst(dienstBack);
        assertThat(zaaktype.getStartDiensts()).doesNotContain(dienstBack);
        assertThat(dienstBack.getStartZaaktype()).isNull();

        zaaktype.startDiensts(new HashSet<>(Set.of(dienstBack)));
        assertThat(zaaktype.getStartDiensts()).containsOnly(dienstBack);
        assertThat(dienstBack.getStartZaaktype()).isEqualTo(zaaktype);

        zaaktype.setStartDiensts(new HashSet<>());
        assertThat(zaaktype.getStartDiensts()).doesNotContain(dienstBack);
        assertThat(dienstBack.getStartZaaktype()).isNull();
    }

    @Test
    void isvanZaakTest() throws Exception {
        Zaaktype zaaktype = getZaaktypeRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        zaaktype.addIsvanZaak(zaakBack);
        assertThat(zaaktype.getIsvanZaaks()).containsOnly(zaakBack);
        assertThat(zaakBack.getIsvanZaaktype()).isEqualTo(zaaktype);

        zaaktype.removeIsvanZaak(zaakBack);
        assertThat(zaaktype.getIsvanZaaks()).doesNotContain(zaakBack);
        assertThat(zaakBack.getIsvanZaaktype()).isNull();

        zaaktype.isvanZaaks(new HashSet<>(Set.of(zaakBack)));
        assertThat(zaaktype.getIsvanZaaks()).containsOnly(zaakBack);
        assertThat(zaakBack.getIsvanZaaktype()).isEqualTo(zaaktype);

        zaaktype.setIsvanZaaks(new HashSet<>());
        assertThat(zaaktype.getIsvanZaaks()).doesNotContain(zaakBack);
        assertThat(zaakBack.getIsvanZaaktype()).isNull();
    }

    @Test
    void isaanleidingvoorFormuliersoortTest() throws Exception {
        Zaaktype zaaktype = getZaaktypeRandomSampleGenerator();
        Formuliersoort formuliersoortBack = getFormuliersoortRandomSampleGenerator();

        zaaktype.addIsaanleidingvoorFormuliersoort(formuliersoortBack);
        assertThat(zaaktype.getIsaanleidingvoorFormuliersoorts()).containsOnly(formuliersoortBack);
        assertThat(formuliersoortBack.getIsaanleidingvoorZaaktypes()).containsOnly(zaaktype);

        zaaktype.removeIsaanleidingvoorFormuliersoort(formuliersoortBack);
        assertThat(zaaktype.getIsaanleidingvoorFormuliersoorts()).doesNotContain(formuliersoortBack);
        assertThat(formuliersoortBack.getIsaanleidingvoorZaaktypes()).doesNotContain(zaaktype);

        zaaktype.isaanleidingvoorFormuliersoorts(new HashSet<>(Set.of(formuliersoortBack)));
        assertThat(zaaktype.getIsaanleidingvoorFormuliersoorts()).containsOnly(formuliersoortBack);
        assertThat(formuliersoortBack.getIsaanleidingvoorZaaktypes()).containsOnly(zaaktype);

        zaaktype.setIsaanleidingvoorFormuliersoorts(new HashSet<>());
        assertThat(zaaktype.getIsaanleidingvoorFormuliersoorts()).doesNotContain(formuliersoortBack);
        assertThat(formuliersoortBack.getIsaanleidingvoorZaaktypes()).doesNotContain(zaaktype);
    }
}
