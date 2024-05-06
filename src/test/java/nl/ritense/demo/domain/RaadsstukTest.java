package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.AgendapuntTestSamples.*;
import static nl.ritense.demo.domain.CategorieTestSamples.*;
import static nl.ritense.demo.domain.DossierTestSamples.*;
import static nl.ritense.demo.domain.IndienerTestSamples.*;
import static nl.ritense.demo.domain.ProgrammaTestSamples.*;
import static nl.ritense.demo.domain.RaadsstukTestSamples.*;
import static nl.ritense.demo.domain.StemmingTestSamples.*;
import static nl.ritense.demo.domain.TaakveldTestSamples.*;
import static nl.ritense.demo.domain.VergaderingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class RaadsstukTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Raadsstuk.class);
        Raadsstuk raadsstuk1 = getRaadsstukSample1();
        Raadsstuk raadsstuk2 = new Raadsstuk();
        assertThat(raadsstuk1).isNotEqualTo(raadsstuk2);

        raadsstuk2.setId(raadsstuk1.getId());
        assertThat(raadsstuk1).isEqualTo(raadsstuk2);

        raadsstuk2 = getRaadsstukSample2();
        assertThat(raadsstuk1).isNotEqualTo(raadsstuk2);
    }

    @Test
    void heeftTaakveldTest() throws Exception {
        Raadsstuk raadsstuk = getRaadsstukRandomSampleGenerator();
        Taakveld taakveldBack = getTaakveldRandomSampleGenerator();

        raadsstuk.setHeeftTaakveld(taakveldBack);
        assertThat(raadsstuk.getHeeftTaakveld()).isEqualTo(taakveldBack);

        raadsstuk.heeftTaakveld(null);
        assertThat(raadsstuk.getHeeftTaakveld()).isNull();
    }

    @Test
    void behandeltAgendapuntTest() throws Exception {
        Raadsstuk raadsstuk = getRaadsstukRandomSampleGenerator();
        Agendapunt agendapuntBack = getAgendapuntRandomSampleGenerator();

        raadsstuk.addBehandeltAgendapunt(agendapuntBack);
        assertThat(raadsstuk.getBehandeltAgendapunts()).containsOnly(agendapuntBack);

        raadsstuk.removeBehandeltAgendapunt(agendapuntBack);
        assertThat(raadsstuk.getBehandeltAgendapunts()).doesNotContain(agendapuntBack);

        raadsstuk.behandeltAgendapunts(new HashSet<>(Set.of(agendapuntBack)));
        assertThat(raadsstuk.getBehandeltAgendapunts()).containsOnly(agendapuntBack);

        raadsstuk.setBehandeltAgendapunts(new HashSet<>());
        assertThat(raadsstuk.getBehandeltAgendapunts()).doesNotContain(agendapuntBack);
    }

    @Test
    void hoortbijProgrammaTest() throws Exception {
        Raadsstuk raadsstuk = getRaadsstukRandomSampleGenerator();
        Programma programmaBack = getProgrammaRandomSampleGenerator();

        raadsstuk.addHoortbijProgramma(programmaBack);
        assertThat(raadsstuk.getHoortbijProgrammas()).containsOnly(programmaBack);

        raadsstuk.removeHoortbijProgramma(programmaBack);
        assertThat(raadsstuk.getHoortbijProgrammas()).doesNotContain(programmaBack);

        raadsstuk.hoortbijProgrammas(new HashSet<>(Set.of(programmaBack)));
        assertThat(raadsstuk.getHoortbijProgrammas()).containsOnly(programmaBack);

        raadsstuk.setHoortbijProgrammas(new HashSet<>());
        assertThat(raadsstuk.getHoortbijProgrammas()).doesNotContain(programmaBack);
    }

    @Test
    void wordtbehandeldinVergaderingTest() throws Exception {
        Raadsstuk raadsstuk = getRaadsstukRandomSampleGenerator();
        Vergadering vergaderingBack = getVergaderingRandomSampleGenerator();

        raadsstuk.addWordtbehandeldinVergadering(vergaderingBack);
        assertThat(raadsstuk.getWordtbehandeldinVergaderings()).containsOnly(vergaderingBack);

        raadsstuk.removeWordtbehandeldinVergadering(vergaderingBack);
        assertThat(raadsstuk.getWordtbehandeldinVergaderings()).doesNotContain(vergaderingBack);

        raadsstuk.wordtbehandeldinVergaderings(new HashSet<>(Set.of(vergaderingBack)));
        assertThat(raadsstuk.getWordtbehandeldinVergaderings()).containsOnly(vergaderingBack);

        raadsstuk.setWordtbehandeldinVergaderings(new HashSet<>());
        assertThat(raadsstuk.getWordtbehandeldinVergaderings()).doesNotContain(vergaderingBack);
    }

    @Test
    void heeftverslagVergaderingTest() throws Exception {
        Raadsstuk raadsstuk = getRaadsstukRandomSampleGenerator();
        Vergadering vergaderingBack = getVergaderingRandomSampleGenerator();

        raadsstuk.setHeeftverslagVergadering(vergaderingBack);
        assertThat(raadsstuk.getHeeftverslagVergadering()).isEqualTo(vergaderingBack);
        assertThat(vergaderingBack.getHeeftverslagRaadsstuk()).isEqualTo(raadsstuk);

        raadsstuk.heeftverslagVergadering(null);
        assertThat(raadsstuk.getHeeftverslagVergadering()).isNull();
        assertThat(vergaderingBack.getHeeftverslagRaadsstuk()).isNull();
    }

    @Test
    void betreftStemmingTest() throws Exception {
        Raadsstuk raadsstuk = getRaadsstukRandomSampleGenerator();
        Stemming stemmingBack = getStemmingRandomSampleGenerator();

        raadsstuk.setBetreftStemming(stemmingBack);
        assertThat(raadsstuk.getBetreftStemming()).isEqualTo(stemmingBack);
        assertThat(stemmingBack.getBetreftRaadsstuk()).isEqualTo(raadsstuk);

        raadsstuk.betreftStemming(null);
        assertThat(raadsstuk.getBetreftStemming()).isNull();
        assertThat(stemmingBack.getBetreftRaadsstuk()).isNull();
    }

    @Test
    void heeftCategorieTest() throws Exception {
        Raadsstuk raadsstuk = getRaadsstukRandomSampleGenerator();
        Categorie categorieBack = getCategorieRandomSampleGenerator();

        raadsstuk.setHeeftCategorie(categorieBack);
        assertThat(raadsstuk.getHeeftCategorie()).isEqualTo(categorieBack);

        raadsstuk.heeftCategorie(null);
        assertThat(raadsstuk.getHeeftCategorie()).isNull();
    }

    @Test
    void hoortbijDossierTest() throws Exception {
        Raadsstuk raadsstuk = getRaadsstukRandomSampleGenerator();
        Dossier dossierBack = getDossierRandomSampleGenerator();

        raadsstuk.addHoortbijDossier(dossierBack);
        assertThat(raadsstuk.getHoortbijDossiers()).containsOnly(dossierBack);
        assertThat(dossierBack.getHoortbijRaadsstuks()).containsOnly(raadsstuk);

        raadsstuk.removeHoortbijDossier(dossierBack);
        assertThat(raadsstuk.getHoortbijDossiers()).doesNotContain(dossierBack);
        assertThat(dossierBack.getHoortbijRaadsstuks()).doesNotContain(raadsstuk);

        raadsstuk.hoortbijDossiers(new HashSet<>(Set.of(dossierBack)));
        assertThat(raadsstuk.getHoortbijDossiers()).containsOnly(dossierBack);
        assertThat(dossierBack.getHoortbijRaadsstuks()).containsOnly(raadsstuk);

        raadsstuk.setHoortbijDossiers(new HashSet<>());
        assertThat(raadsstuk.getHoortbijDossiers()).doesNotContain(dossierBack);
        assertThat(dossierBack.getHoortbijRaadsstuks()).doesNotContain(raadsstuk);
    }

    @Test
    void heeftIndienerTest() throws Exception {
        Raadsstuk raadsstuk = getRaadsstukRandomSampleGenerator();
        Indiener indienerBack = getIndienerRandomSampleGenerator();

        raadsstuk.addHeeftIndiener(indienerBack);
        assertThat(raadsstuk.getHeeftIndieners()).containsOnly(indienerBack);
        assertThat(indienerBack.getHeeftRaadsstuks()).containsOnly(raadsstuk);

        raadsstuk.removeHeeftIndiener(indienerBack);
        assertThat(raadsstuk.getHeeftIndieners()).doesNotContain(indienerBack);
        assertThat(indienerBack.getHeeftRaadsstuks()).doesNotContain(raadsstuk);

        raadsstuk.heeftIndieners(new HashSet<>(Set.of(indienerBack)));
        assertThat(raadsstuk.getHeeftIndieners()).containsOnly(indienerBack);
        assertThat(indienerBack.getHeeftRaadsstuks()).containsOnly(raadsstuk);

        raadsstuk.setHeeftIndieners(new HashSet<>());
        assertThat(raadsstuk.getHeeftIndieners()).doesNotContain(indienerBack);
        assertThat(indienerBack.getHeeftRaadsstuks()).doesNotContain(raadsstuk);
    }
}
