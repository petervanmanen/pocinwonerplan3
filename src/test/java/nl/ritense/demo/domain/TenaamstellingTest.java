package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AantekeningTestSamples.*;
import static nl.ritense.demo.domain.RechtspersoonTestSamples.*;
import static nl.ritense.demo.domain.TenaamstellingTestSamples.*;
import static nl.ritense.demo.domain.ZekerheidsrechtTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class TenaamstellingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Tenaamstelling.class);
        Tenaamstelling tenaamstelling1 = getTenaamstellingSample1();
        Tenaamstelling tenaamstelling2 = new Tenaamstelling();
        assertThat(tenaamstelling1).isNotEqualTo(tenaamstelling2);

        tenaamstelling2.setId(tenaamstelling1.getId());
        assertThat(tenaamstelling1).isEqualTo(tenaamstelling2);

        tenaamstelling2 = getTenaamstellingSample2();
        assertThat(tenaamstelling1).isNotEqualTo(tenaamstelling2);
    }

    @Test
    void emptyAantekeningTest() throws Exception {
        Tenaamstelling tenaamstelling = getTenaamstellingRandomSampleGenerator();
        Aantekening aantekeningBack = getAantekeningRandomSampleGenerator();

        tenaamstelling.addEmptyAantekening(aantekeningBack);
        assertThat(tenaamstelling.getEmptyAantekenings()).containsOnly(aantekeningBack);
        assertThat(aantekeningBack.getEmptyTenaamstelling()).isEqualTo(tenaamstelling);

        tenaamstelling.removeEmptyAantekening(aantekeningBack);
        assertThat(tenaamstelling.getEmptyAantekenings()).doesNotContain(aantekeningBack);
        assertThat(aantekeningBack.getEmptyTenaamstelling()).isNull();

        tenaamstelling.emptyAantekenings(new HashSet<>(Set.of(aantekeningBack)));
        assertThat(tenaamstelling.getEmptyAantekenings()).containsOnly(aantekeningBack);
        assertThat(aantekeningBack.getEmptyTenaamstelling()).isEqualTo(tenaamstelling);

        tenaamstelling.setEmptyAantekenings(new HashSet<>());
        assertThat(tenaamstelling.getEmptyAantekenings()).doesNotContain(aantekeningBack);
        assertThat(aantekeningBack.getEmptyTenaamstelling()).isNull();
    }

    @Test
    void heeftRechtspersoonTest() throws Exception {
        Tenaamstelling tenaamstelling = getTenaamstellingRandomSampleGenerator();
        Rechtspersoon rechtspersoonBack = getRechtspersoonRandomSampleGenerator();

        tenaamstelling.setHeeftRechtspersoon(rechtspersoonBack);
        assertThat(tenaamstelling.getHeeftRechtspersoon()).isEqualTo(rechtspersoonBack);

        tenaamstelling.heeftRechtspersoon(null);
        assertThat(tenaamstelling.getHeeftRechtspersoon()).isNull();
    }

    @Test
    void bezwaartZekerheidsrechtTest() throws Exception {
        Tenaamstelling tenaamstelling = getTenaamstellingRandomSampleGenerator();
        Zekerheidsrecht zekerheidsrechtBack = getZekerheidsrechtRandomSampleGenerator();

        tenaamstelling.addBezwaartZekerheidsrecht(zekerheidsrechtBack);
        assertThat(tenaamstelling.getBezwaartZekerheidsrechts()).containsOnly(zekerheidsrechtBack);
        assertThat(zekerheidsrechtBack.getBezwaartTenaamstelling()).isEqualTo(tenaamstelling);

        tenaamstelling.removeBezwaartZekerheidsrecht(zekerheidsrechtBack);
        assertThat(tenaamstelling.getBezwaartZekerheidsrechts()).doesNotContain(zekerheidsrechtBack);
        assertThat(zekerheidsrechtBack.getBezwaartTenaamstelling()).isNull();

        tenaamstelling.bezwaartZekerheidsrechts(new HashSet<>(Set.of(zekerheidsrechtBack)));
        assertThat(tenaamstelling.getBezwaartZekerheidsrechts()).containsOnly(zekerheidsrechtBack);
        assertThat(zekerheidsrechtBack.getBezwaartTenaamstelling()).isEqualTo(tenaamstelling);

        tenaamstelling.setBezwaartZekerheidsrechts(new HashSet<>());
        assertThat(tenaamstelling.getBezwaartZekerheidsrechts()).doesNotContain(zekerheidsrechtBack);
        assertThat(zekerheidsrechtBack.getBezwaartTenaamstelling()).isNull();
    }
}
