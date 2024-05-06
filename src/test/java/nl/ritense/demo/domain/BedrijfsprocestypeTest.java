package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.BedrijfsprocestypeTestSamples.*;
import static nl.ritense.demo.domain.DeelprocestypeTestSamples.*;
import static nl.ritense.demo.domain.ProducttypeTestSamples.*;
import static nl.ritense.demo.domain.ZaaktypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class BedrijfsprocestypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Bedrijfsprocestype.class);
        Bedrijfsprocestype bedrijfsprocestype1 = getBedrijfsprocestypeSample1();
        Bedrijfsprocestype bedrijfsprocestype2 = new Bedrijfsprocestype();
        assertThat(bedrijfsprocestype1).isNotEqualTo(bedrijfsprocestype2);

        bedrijfsprocestype2.setId(bedrijfsprocestype1.getId());
        assertThat(bedrijfsprocestype1).isEqualTo(bedrijfsprocestype2);

        bedrijfsprocestype2 = getBedrijfsprocestypeSample2();
        assertThat(bedrijfsprocestype1).isNotEqualTo(bedrijfsprocestype2);
    }

    @Test
    void heeftProducttypeTest() throws Exception {
        Bedrijfsprocestype bedrijfsprocestype = getBedrijfsprocestypeRandomSampleGenerator();
        Producttype producttypeBack = getProducttypeRandomSampleGenerator();

        bedrijfsprocestype.addHeeftProducttype(producttypeBack);
        assertThat(bedrijfsprocestype.getHeeftProducttypes()).containsOnly(producttypeBack);
        assertThat(producttypeBack.getHeeftBedrijfsprocestype()).isEqualTo(bedrijfsprocestype);

        bedrijfsprocestype.removeHeeftProducttype(producttypeBack);
        assertThat(bedrijfsprocestype.getHeeftProducttypes()).doesNotContain(producttypeBack);
        assertThat(producttypeBack.getHeeftBedrijfsprocestype()).isNull();

        bedrijfsprocestype.heeftProducttypes(new HashSet<>(Set.of(producttypeBack)));
        assertThat(bedrijfsprocestype.getHeeftProducttypes()).containsOnly(producttypeBack);
        assertThat(producttypeBack.getHeeftBedrijfsprocestype()).isEqualTo(bedrijfsprocestype);

        bedrijfsprocestype.setHeeftProducttypes(new HashSet<>());
        assertThat(bedrijfsprocestype.getHeeftProducttypes()).doesNotContain(producttypeBack);
        assertThat(producttypeBack.getHeeftBedrijfsprocestype()).isNull();
    }

    @Test
    void heeftZaaktypeTest() throws Exception {
        Bedrijfsprocestype bedrijfsprocestype = getBedrijfsprocestypeRandomSampleGenerator();
        Zaaktype zaaktypeBack = getZaaktypeRandomSampleGenerator();

        bedrijfsprocestype.addHeeftZaaktype(zaaktypeBack);
        assertThat(bedrijfsprocestype.getHeeftZaaktypes()).containsOnly(zaaktypeBack);
        assertThat(zaaktypeBack.getHeeftBedrijfsprocestype()).isEqualTo(bedrijfsprocestype);

        bedrijfsprocestype.removeHeeftZaaktype(zaaktypeBack);
        assertThat(bedrijfsprocestype.getHeeftZaaktypes()).doesNotContain(zaaktypeBack);
        assertThat(zaaktypeBack.getHeeftBedrijfsprocestype()).isNull();

        bedrijfsprocestype.heeftZaaktypes(new HashSet<>(Set.of(zaaktypeBack)));
        assertThat(bedrijfsprocestype.getHeeftZaaktypes()).containsOnly(zaaktypeBack);
        assertThat(zaaktypeBack.getHeeftBedrijfsprocestype()).isEqualTo(bedrijfsprocestype);

        bedrijfsprocestype.setHeeftZaaktypes(new HashSet<>());
        assertThat(bedrijfsprocestype.getHeeftZaaktypes()).doesNotContain(zaaktypeBack);
        assertThat(zaaktypeBack.getHeeftBedrijfsprocestype()).isNull();
    }

    @Test
    void isdeelvanDeelprocestypeTest() throws Exception {
        Bedrijfsprocestype bedrijfsprocestype = getBedrijfsprocestypeRandomSampleGenerator();
        Deelprocestype deelprocestypeBack = getDeelprocestypeRandomSampleGenerator();

        bedrijfsprocestype.setIsdeelvanDeelprocestype(deelprocestypeBack);
        assertThat(bedrijfsprocestype.getIsdeelvanDeelprocestype()).isEqualTo(deelprocestypeBack);
        assertThat(deelprocestypeBack.getIsdeelvanBedrijfsprocestype()).isEqualTo(bedrijfsprocestype);

        bedrijfsprocestype.isdeelvanDeelprocestype(null);
        assertThat(bedrijfsprocestype.getIsdeelvanDeelprocestype()).isNull();
        assertThat(deelprocestypeBack.getIsdeelvanBedrijfsprocestype()).isNull();
    }
}
