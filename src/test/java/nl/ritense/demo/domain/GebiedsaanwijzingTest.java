package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GebiedsaanwijzingTestSamples.*;
import static nl.ritense.demo.domain.InstructieregelTestSamples.*;
import static nl.ritense.demo.domain.LocatieTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GebiedsaanwijzingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Gebiedsaanwijzing.class);
        Gebiedsaanwijzing gebiedsaanwijzing1 = getGebiedsaanwijzingSample1();
        Gebiedsaanwijzing gebiedsaanwijzing2 = new Gebiedsaanwijzing();
        assertThat(gebiedsaanwijzing1).isNotEqualTo(gebiedsaanwijzing2);

        gebiedsaanwijzing2.setId(gebiedsaanwijzing1.getId());
        assertThat(gebiedsaanwijzing1).isEqualTo(gebiedsaanwijzing2);

        gebiedsaanwijzing2 = getGebiedsaanwijzingSample2();
        assertThat(gebiedsaanwijzing1).isNotEqualTo(gebiedsaanwijzing2);
    }

    @Test
    void verwijstnaarLocatieTest() throws Exception {
        Gebiedsaanwijzing gebiedsaanwijzing = getGebiedsaanwijzingRandomSampleGenerator();
        Locatie locatieBack = getLocatieRandomSampleGenerator();

        gebiedsaanwijzing.addVerwijstnaarLocatie(locatieBack);
        assertThat(gebiedsaanwijzing.getVerwijstnaarLocaties()).containsOnly(locatieBack);

        gebiedsaanwijzing.removeVerwijstnaarLocatie(locatieBack);
        assertThat(gebiedsaanwijzing.getVerwijstnaarLocaties()).doesNotContain(locatieBack);

        gebiedsaanwijzing.verwijstnaarLocaties(new HashSet<>(Set.of(locatieBack)));
        assertThat(gebiedsaanwijzing.getVerwijstnaarLocaties()).containsOnly(locatieBack);

        gebiedsaanwijzing.setVerwijstnaarLocaties(new HashSet<>());
        assertThat(gebiedsaanwijzing.getVerwijstnaarLocaties()).doesNotContain(locatieBack);
    }

    @Test
    void beschrijftgebiedsaanwijzingInstructieregelTest() throws Exception {
        Gebiedsaanwijzing gebiedsaanwijzing = getGebiedsaanwijzingRandomSampleGenerator();
        Instructieregel instructieregelBack = getInstructieregelRandomSampleGenerator();

        gebiedsaanwijzing.addBeschrijftgebiedsaanwijzingInstructieregel(instructieregelBack);
        assertThat(gebiedsaanwijzing.getBeschrijftgebiedsaanwijzingInstructieregels()).containsOnly(instructieregelBack);
        assertThat(instructieregelBack.getBeschrijftgebiedsaanwijzingGebiedsaanwijzings()).containsOnly(gebiedsaanwijzing);

        gebiedsaanwijzing.removeBeschrijftgebiedsaanwijzingInstructieregel(instructieregelBack);
        assertThat(gebiedsaanwijzing.getBeschrijftgebiedsaanwijzingInstructieregels()).doesNotContain(instructieregelBack);
        assertThat(instructieregelBack.getBeschrijftgebiedsaanwijzingGebiedsaanwijzings()).doesNotContain(gebiedsaanwijzing);

        gebiedsaanwijzing.beschrijftgebiedsaanwijzingInstructieregels(new HashSet<>(Set.of(instructieregelBack)));
        assertThat(gebiedsaanwijzing.getBeschrijftgebiedsaanwijzingInstructieregels()).containsOnly(instructieregelBack);
        assertThat(instructieregelBack.getBeschrijftgebiedsaanwijzingGebiedsaanwijzings()).containsOnly(gebiedsaanwijzing);

        gebiedsaanwijzing.setBeschrijftgebiedsaanwijzingInstructieregels(new HashSet<>());
        assertThat(gebiedsaanwijzing.getBeschrijftgebiedsaanwijzingInstructieregels()).doesNotContain(instructieregelBack);
        assertThat(instructieregelBack.getBeschrijftgebiedsaanwijzingGebiedsaanwijzings()).doesNotContain(gebiedsaanwijzing);
    }
}
