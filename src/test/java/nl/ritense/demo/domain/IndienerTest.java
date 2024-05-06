package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.CollegelidTestSamples.*;
import static nl.ritense.demo.domain.IndienerTestSamples.*;
import static nl.ritense.demo.domain.RaadslidTestSamples.*;
import static nl.ritense.demo.domain.RaadsstukTestSamples.*;
import static nl.ritense.demo.domain.RechtspersoonTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class IndienerTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Indiener.class);
        Indiener indiener1 = getIndienerSample1();
        Indiener indiener2 = new Indiener();
        assertThat(indiener1).isNotEqualTo(indiener2);

        indiener2.setId(indiener1.getId());
        assertThat(indiener1).isEqualTo(indiener2);

        indiener2 = getIndienerSample2();
        assertThat(indiener1).isNotEqualTo(indiener2);
    }

    @Test
    void isCollegelidTest() throws Exception {
        Indiener indiener = getIndienerRandomSampleGenerator();
        Collegelid collegelidBack = getCollegelidRandomSampleGenerator();

        indiener.setIsCollegelid(collegelidBack);
        assertThat(indiener.getIsCollegelid()).isEqualTo(collegelidBack);

        indiener.isCollegelid(null);
        assertThat(indiener.getIsCollegelid()).isNull();
    }

    @Test
    void isRaadslidTest() throws Exception {
        Indiener indiener = getIndienerRandomSampleGenerator();
        Raadslid raadslidBack = getRaadslidRandomSampleGenerator();

        indiener.setIsRaadslid(raadslidBack);
        assertThat(indiener.getIsRaadslid()).isEqualTo(raadslidBack);

        indiener.isRaadslid(null);
        assertThat(indiener.getIsRaadslid()).isNull();
    }

    @Test
    void isRechtspersoonTest() throws Exception {
        Indiener indiener = getIndienerRandomSampleGenerator();
        Rechtspersoon rechtspersoonBack = getRechtspersoonRandomSampleGenerator();

        indiener.setIsRechtspersoon(rechtspersoonBack);
        assertThat(indiener.getIsRechtspersoon()).isEqualTo(rechtspersoonBack);

        indiener.isRechtspersoon(null);
        assertThat(indiener.getIsRechtspersoon()).isNull();
    }

    @Test
    void heeftRaadsstukTest() throws Exception {
        Indiener indiener = getIndienerRandomSampleGenerator();
        Raadsstuk raadsstukBack = getRaadsstukRandomSampleGenerator();

        indiener.addHeeftRaadsstuk(raadsstukBack);
        assertThat(indiener.getHeeftRaadsstuks()).containsOnly(raadsstukBack);

        indiener.removeHeeftRaadsstuk(raadsstukBack);
        assertThat(indiener.getHeeftRaadsstuks()).doesNotContain(raadsstukBack);

        indiener.heeftRaadsstuks(new HashSet<>(Set.of(raadsstukBack)));
        assertThat(indiener.getHeeftRaadsstuks()).containsOnly(raadsstukBack);

        indiener.setHeeftRaadsstuks(new HashSet<>());
        assertThat(indiener.getHeeftRaadsstuks()).doesNotContain(raadsstukBack);
    }
}
