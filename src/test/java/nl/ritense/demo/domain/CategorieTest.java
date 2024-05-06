package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.CategorieTestSamples.*;
import static nl.ritense.demo.domain.LeverancierTestSamples.*;
import static nl.ritense.demo.domain.MeldingTestSamples.*;
import static nl.ritense.demo.domain.RaadsstukTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class CategorieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Categorie.class);
        Categorie categorie1 = getCategorieSample1();
        Categorie categorie2 = new Categorie();
        assertThat(categorie1).isNotEqualTo(categorie2);

        categorie2.setId(categorie1.getId());
        assertThat(categorie1).isEqualTo(categorie2);

        categorie2 = getCategorieSample2();
        assertThat(categorie1).isNotEqualTo(categorie2);
    }

    @Test
    void heeftRaadsstukTest() throws Exception {
        Categorie categorie = getCategorieRandomSampleGenerator();
        Raadsstuk raadsstukBack = getRaadsstukRandomSampleGenerator();

        categorie.addHeeftRaadsstuk(raadsstukBack);
        assertThat(categorie.getHeeftRaadsstuks()).containsOnly(raadsstukBack);
        assertThat(raadsstukBack.getHeeftCategorie()).isEqualTo(categorie);

        categorie.removeHeeftRaadsstuk(raadsstukBack);
        assertThat(categorie.getHeeftRaadsstuks()).doesNotContain(raadsstukBack);
        assertThat(raadsstukBack.getHeeftCategorie()).isNull();

        categorie.heeftRaadsstuks(new HashSet<>(Set.of(raadsstukBack)));
        assertThat(categorie.getHeeftRaadsstuks()).containsOnly(raadsstukBack);
        assertThat(raadsstukBack.getHeeftCategorie()).isEqualTo(categorie);

        categorie.setHeeftRaadsstuks(new HashSet<>());
        assertThat(categorie.getHeeftRaadsstuks()).doesNotContain(raadsstukBack);
        assertThat(raadsstukBack.getHeeftCategorie()).isNull();
    }

    @Test
    void hoofdcategorieMeldingTest() throws Exception {
        Categorie categorie = getCategorieRandomSampleGenerator();
        Melding meldingBack = getMeldingRandomSampleGenerator();

        categorie.addHoofdcategorieMelding(meldingBack);
        assertThat(categorie.getHoofdcategorieMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getHoofdcategorieCategorie()).isEqualTo(categorie);

        categorie.removeHoofdcategorieMelding(meldingBack);
        assertThat(categorie.getHoofdcategorieMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getHoofdcategorieCategorie()).isNull();

        categorie.hoofdcategorieMeldings(new HashSet<>(Set.of(meldingBack)));
        assertThat(categorie.getHoofdcategorieMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getHoofdcategorieCategorie()).isEqualTo(categorie);

        categorie.setHoofdcategorieMeldings(new HashSet<>());
        assertThat(categorie.getHoofdcategorieMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getHoofdcategorieCategorie()).isNull();
    }

    @Test
    void subcategorieMeldingTest() throws Exception {
        Categorie categorie = getCategorieRandomSampleGenerator();
        Melding meldingBack = getMeldingRandomSampleGenerator();

        categorie.addSubcategorieMelding(meldingBack);
        assertThat(categorie.getSubcategorieMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getSubcategorieCategorie()).isEqualTo(categorie);

        categorie.removeSubcategorieMelding(meldingBack);
        assertThat(categorie.getSubcategorieMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getSubcategorieCategorie()).isNull();

        categorie.subcategorieMeldings(new HashSet<>(Set.of(meldingBack)));
        assertThat(categorie.getSubcategorieMeldings()).containsOnly(meldingBack);
        assertThat(meldingBack.getSubcategorieCategorie()).isEqualTo(categorie);

        categorie.setSubcategorieMeldings(new HashSet<>());
        assertThat(categorie.getSubcategorieMeldings()).doesNotContain(meldingBack);
        assertThat(meldingBack.getSubcategorieCategorie()).isNull();
    }

    @Test
    void gekwalificeerdLeverancierTest() throws Exception {
        Categorie categorie = getCategorieRandomSampleGenerator();
        Leverancier leverancierBack = getLeverancierRandomSampleGenerator();

        categorie.addGekwalificeerdLeverancier(leverancierBack);
        assertThat(categorie.getGekwalificeerdLeveranciers()).containsOnly(leverancierBack);
        assertThat(leverancierBack.getGekwalificeerdCategories()).containsOnly(categorie);

        categorie.removeGekwalificeerdLeverancier(leverancierBack);
        assertThat(categorie.getGekwalificeerdLeveranciers()).doesNotContain(leverancierBack);
        assertThat(leverancierBack.getGekwalificeerdCategories()).doesNotContain(categorie);

        categorie.gekwalificeerdLeveranciers(new HashSet<>(Set.of(leverancierBack)));
        assertThat(categorie.getGekwalificeerdLeveranciers()).containsOnly(leverancierBack);
        assertThat(leverancierBack.getGekwalificeerdCategories()).containsOnly(categorie);

        categorie.setGekwalificeerdLeveranciers(new HashSet<>());
        assertThat(categorie.getGekwalificeerdLeveranciers()).doesNotContain(leverancierBack);
        assertThat(leverancierBack.getGekwalificeerdCategories()).doesNotContain(categorie);
    }
}
