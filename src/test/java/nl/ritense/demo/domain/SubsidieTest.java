package nl.ritense.demo.domain;

import static nl.ritense.demo.domain.DocumentTestSamples.*;
import static nl.ritense.demo.domain.KostenplaatsTestSamples.*;
import static nl.ritense.demo.domain.MedewerkerTestSamples.*;
import static nl.ritense.demo.domain.RapportagemomentTestSamples.*;
import static nl.ritense.demo.domain.RechtspersoonTestSamples.*;
import static nl.ritense.demo.domain.SectorTestSamples.*;
import static nl.ritense.demo.domain.SubsidieTestSamples.*;
import static nl.ritense.demo.domain.SubsidieaanvraagTestSamples.*;
import static nl.ritense.demo.domain.SubsidiebeschikkingTestSamples.*;
import static nl.ritense.demo.domain.TaakTestSamples.*;
import static nl.ritense.demo.domain.ZaakTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import nl.ritense.demo.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubsidieTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Subsidie.class);
        Subsidie subsidie1 = getSubsidieSample1();
        Subsidie subsidie2 = new Subsidie();
        assertThat(subsidie1).isNotEqualTo(subsidie2);

        subsidie2.setId(subsidie1.getId());
        assertThat(subsidie1).isEqualTo(subsidie2);

        subsidie2 = getSubsidieSample2();
        assertThat(subsidie1).isNotEqualTo(subsidie2);
    }

    @Test
    void heeftZaakTest() throws Exception {
        Subsidie subsidie = getSubsidieRandomSampleGenerator();
        Zaak zaakBack = getZaakRandomSampleGenerator();

        subsidie.setHeeftZaak(zaakBack);
        assertThat(subsidie.getHeeftZaak()).isEqualTo(zaakBack);

        subsidie.heeftZaak(null);
        assertThat(subsidie.getHeeftZaak()).isNull();
    }

    @Test
    void heeftRapportagemomentTest() throws Exception {
        Subsidie subsidie = getSubsidieRandomSampleGenerator();
        Rapportagemoment rapportagemomentBack = getRapportagemomentRandomSampleGenerator();

        subsidie.addHeeftRapportagemoment(rapportagemomentBack);
        assertThat(subsidie.getHeeftRapportagemoments()).containsOnly(rapportagemomentBack);
        assertThat(rapportagemomentBack.getHeeftSubsidie()).isEqualTo(subsidie);

        subsidie.removeHeeftRapportagemoment(rapportagemomentBack);
        assertThat(subsidie.getHeeftRapportagemoments()).doesNotContain(rapportagemomentBack);
        assertThat(rapportagemomentBack.getHeeftSubsidie()).isNull();

        subsidie.heeftRapportagemoments(new HashSet<>(Set.of(rapportagemomentBack)));
        assertThat(subsidie.getHeeftRapportagemoments()).containsOnly(rapportagemomentBack);
        assertThat(rapportagemomentBack.getHeeftSubsidie()).isEqualTo(subsidie);

        subsidie.setHeeftRapportagemoments(new HashSet<>());
        assertThat(subsidie.getHeeftRapportagemoments()).doesNotContain(rapportagemomentBack);
        assertThat(rapportagemomentBack.getHeeftSubsidie()).isNull();
    }

    @Test
    void heeftTaakTest() throws Exception {
        Subsidie subsidie = getSubsidieRandomSampleGenerator();
        Taak taakBack = getTaakRandomSampleGenerator();

        subsidie.addHeeftTaak(taakBack);
        assertThat(subsidie.getHeeftTaaks()).containsOnly(taakBack);
        assertThat(taakBack.getHeeftSubsidie()).isEqualTo(subsidie);

        subsidie.removeHeeftTaak(taakBack);
        assertThat(subsidie.getHeeftTaaks()).doesNotContain(taakBack);
        assertThat(taakBack.getHeeftSubsidie()).isNull();

        subsidie.heeftTaaks(new HashSet<>(Set.of(taakBack)));
        assertThat(subsidie.getHeeftTaaks()).containsOnly(taakBack);
        assertThat(taakBack.getHeeftSubsidie()).isEqualTo(subsidie);

        subsidie.setHeeftTaaks(new HashSet<>());
        assertThat(subsidie.getHeeftTaaks()).doesNotContain(taakBack);
        assertThat(taakBack.getHeeftSubsidie()).isNull();
    }

    @Test
    void valtbinnenSectorTest() throws Exception {
        Subsidie subsidie = getSubsidieRandomSampleGenerator();
        Sector sectorBack = getSectorRandomSampleGenerator();

        subsidie.setValtbinnenSector(sectorBack);
        assertThat(subsidie.getValtbinnenSector()).isEqualTo(sectorBack);

        subsidie.valtbinnenSector(null);
        assertThat(subsidie.getValtbinnenSector()).isNull();
    }

    @Test
    void behandelaarMedewerkerTest() throws Exception {
        Subsidie subsidie = getSubsidieRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        subsidie.setBehandelaarMedewerker(medewerkerBack);
        assertThat(subsidie.getBehandelaarMedewerker()).isEqualTo(medewerkerBack);

        subsidie.behandelaarMedewerker(null);
        assertThat(subsidie.getBehandelaarMedewerker()).isNull();
    }

    @Test
    void verstrekkerRechtspersoonTest() throws Exception {
        Subsidie subsidie = getSubsidieRandomSampleGenerator();
        Rechtspersoon rechtspersoonBack = getRechtspersoonRandomSampleGenerator();

        subsidie.setVerstrekkerRechtspersoon(rechtspersoonBack);
        assertThat(subsidie.getVerstrekkerRechtspersoon()).isEqualTo(rechtspersoonBack);

        subsidie.verstrekkerRechtspersoon(null);
        assertThat(subsidie.getVerstrekkerRechtspersoon()).isNull();
    }

    @Test
    void heeftKostenplaatsTest() throws Exception {
        Subsidie subsidie = getSubsidieRandomSampleGenerator();
        Kostenplaats kostenplaatsBack = getKostenplaatsRandomSampleGenerator();

        subsidie.setHeeftKostenplaats(kostenplaatsBack);
        assertThat(subsidie.getHeeftKostenplaats()).isEqualTo(kostenplaatsBack);

        subsidie.heeftKostenplaats(null);
        assertThat(subsidie.getHeeftKostenplaats()).isNull();
    }

    @Test
    void heeftDocumentTest() throws Exception {
        Subsidie subsidie = getSubsidieRandomSampleGenerator();
        Document documentBack = getDocumentRandomSampleGenerator();

        subsidie.setHeeftDocument(documentBack);
        assertThat(subsidie.getHeeftDocument()).isEqualTo(documentBack);

        subsidie.heeftDocument(null);
        assertThat(subsidie.getHeeftDocument()).isNull();
    }

    @Test
    void betreftSubsidieaanvraagTest() throws Exception {
        Subsidie subsidie = getSubsidieRandomSampleGenerator();
        Subsidieaanvraag subsidieaanvraagBack = getSubsidieaanvraagRandomSampleGenerator();

        subsidie.setBetreftSubsidieaanvraag(subsidieaanvraagBack);
        assertThat(subsidie.getBetreftSubsidieaanvraag()).isEqualTo(subsidieaanvraagBack);
        assertThat(subsidieaanvraagBack.getBetreftSubsidie()).isEqualTo(subsidie);

        subsidie.betreftSubsidieaanvraag(null);
        assertThat(subsidie.getBetreftSubsidieaanvraag()).isNull();
        assertThat(subsidieaanvraagBack.getBetreftSubsidie()).isNull();
    }

    @Test
    void betreftSubsidiebeschikkingTest() throws Exception {
        Subsidie subsidie = getSubsidieRandomSampleGenerator();
        Subsidiebeschikking subsidiebeschikkingBack = getSubsidiebeschikkingRandomSampleGenerator();

        subsidie.setBetreftSubsidiebeschikking(subsidiebeschikkingBack);
        assertThat(subsidie.getBetreftSubsidiebeschikking()).isEqualTo(subsidiebeschikkingBack);
        assertThat(subsidiebeschikkingBack.getBetreftSubsidie()).isEqualTo(subsidie);

        subsidie.betreftSubsidiebeschikking(null);
        assertThat(subsidie.getBetreftSubsidiebeschikking()).isNull();
        assertThat(subsidiebeschikkingBack.getBetreftSubsidie()).isNull();
    }

    @Test
    void aanvragerRechtspersoonTest() throws Exception {
        Subsidie subsidie = getSubsidieRandomSampleGenerator();
        Rechtspersoon rechtspersoonBack = getRechtspersoonRandomSampleGenerator();

        subsidie.setAanvragerRechtspersoon(rechtspersoonBack);
        assertThat(subsidie.getAanvragerRechtspersoon()).isEqualTo(rechtspersoonBack);

        subsidie.aanvragerRechtspersoon(null);
        assertThat(subsidie.getAanvragerRechtspersoon()).isNull();
    }

    @Test
    void aanvragerMedewerkerTest() throws Exception {
        Subsidie subsidie = getSubsidieRandomSampleGenerator();
        Medewerker medewerkerBack = getMedewerkerRandomSampleGenerator();

        subsidie.setAanvragerMedewerker(medewerkerBack);
        assertThat(subsidie.getAanvragerMedewerker()).isEqualTo(medewerkerBack);

        subsidie.aanvragerMedewerker(null);
        assertThat(subsidie.getAanvragerMedewerker()).isNull();
    }
}
