package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BedrijfsprocestypeTestSamples.*;
import static nl.ritense.demo.domain.ProducttypeTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static nl.ritense.demo.domain.ZaaktypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProducttypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Producttype.class);
        Producttype producttype1 = getProducttypeSample1();
        Producttype producttype2 = new Producttype();
        assertThat(producttype1).isNotEqualTo(producttype2);

        producttype2.setId(producttype1.getId());
        assertThat(producttype1).isEqualTo(producttype2);

        producttype2 = getProducttypeSample2();
        assertThat(producttype1).isNotEqualTo(producttype2);
    }

    @Test
    void heeftproductZaakTest() throws Exception {
        Producttype producttype = getProducttypeRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        producttype.setHeeftproductZaak(zaakBack);
        assertThat(producttype.getHeeftproductZaak()).isEqualTo(zaakBack);
        assertThat(zaakBack.getHeeftproductProducttype()).isEqualTo(producttype);

        producttype.heeftproductZaak(null);
        assertThat(producttype.getHeeftproductZaak()).isNull();
        assertThat(zaakBack.getHeeftproductProducttype()).isNull();
    }

    @Test
    void heeftZaaktypeTest() throws Exception {
        Producttype producttype = getProducttypeRandomSampleGenerator();
        Zaaktype zaaktypeBack = getZaaktypeRandomSampleGenerator();

        producttype.setHeeftZaaktype(zaaktypeBack);
        assertThat(producttype.getHeeftZaaktype()).isEqualTo(zaaktypeBack);
        assertThat(zaaktypeBack.getHeeftProducttype()).isEqualTo(producttype);

        producttype.heeftZaaktype(null);
        assertThat(producttype.getHeeftZaaktype()).isNull();
        assertThat(zaaktypeBack.getHeeftProducttype()).isNull();
    }

    @Test
    void heeftBedrijfsprocestypeTest() throws Exception {
        Producttype producttype = getProducttypeRandomSampleGenerator();
        Bedrijfsprocestype bedrijfsprocestypeBack = getBedrijfsprocestypeRandomSampleGenerator();

        producttype.setHeeftBedrijfsprocestype(bedrijfsprocestypeBack);
        assertThat(producttype.getHeeftBedrijfsprocestype()).isEqualTo(bedrijfsprocestypeBack);

        producttype.heeftBedrijfsprocestype(null);
        assertThat(producttype.getHeeftBedrijfsprocestype()).isNull();
    }
}
