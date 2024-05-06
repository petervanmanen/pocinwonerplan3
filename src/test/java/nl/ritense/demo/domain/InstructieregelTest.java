package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.GebiedsaanwijzingTestSamples.*;
import static nl.ritense.demo.domain.InstructieregelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class InstructieregelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Instructieregel.class);
        Instructieregel instructieregel1 = getInstructieregelSample1();
        Instructieregel instructieregel2 = new Instructieregel();
        assertThat(instructieregel1).isNotEqualTo(instructieregel2);

        instructieregel2.setId(instructieregel1.getId());
        assertThat(instructieregel1).isEqualTo(instructieregel2);

        instructieregel2 = getInstructieregelSample2();
        assertThat(instructieregel1).isNotEqualTo(instructieregel2);
    }

    @Test
    void beschrijftgebiedsaanwijzingGebiedsaanwijzingTest() throws Exception {
        Instructieregel instructieregel = getInstructieregelRandomSampleGenerator();
        Gebiedsaanwijzing gebiedsaanwijzingBack = getGebiedsaanwijzingRandomSampleGenerator();

        instructieregel.addBeschrijftgebiedsaanwijzingGebiedsaanwijzing(gebiedsaanwijzingBack);
        assertThat(instructieregel.getBeschrijftgebiedsaanwijzingGebiedsaanwijzings()).containsOnly(gebiedsaanwijzingBack);

        instructieregel.removeBeschrijftgebiedsaanwijzingGebiedsaanwijzing(gebiedsaanwijzingBack);
        assertThat(instructieregel.getBeschrijftgebiedsaanwijzingGebiedsaanwijzings()).doesNotContain(gebiedsaanwijzingBack);

        instructieregel.beschrijftgebiedsaanwijzingGebiedsaanwijzings(new HashSet<>(Set.of(gebiedsaanwijzingBack)));
        assertThat(instructieregel.getBeschrijftgebiedsaanwijzingGebiedsaanwijzings()).containsOnly(gebiedsaanwijzingBack);

        instructieregel.setBeschrijftgebiedsaanwijzingGebiedsaanwijzings(new HashSet<>());
        assertThat(instructieregel.getBeschrijftgebiedsaanwijzingGebiedsaanwijzings()).doesNotContain(gebiedsaanwijzingBack);
    }
}
